package org.xi.quick.codegenerator.config;

import freemarker.template.Template;
import freemarker.template.TemplateExceptionHandler;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.xi.quick.codegenerator.entity.ValidStatusField;
import org.xi.quick.codegenerator.model.FreemarkerModel;
import org.xi.quick.codegenerator.utils.DirectoryUtil;
import org.xi.quick.codegenerator.utils.StringUtil;

import java.io.*;
import java.util.*;
import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Configuration
@PropertySource("classpath:config.properties")
public class AppConfig {

    @Value("${spring.datasource.url}")
    String datasourceUrl;

    @Value("${path.template}")
    String templatePath;

    @Value("${path.out}")
    String outPath;

    @Value("${path.common.properties}")
    String commonPropertiesPath;

    @Value("${codeEncoding}")
    String codeEncoding;

    @Value("${folder.ingored}")
    String iningoredFolder;

    @Value("${file.aggregate}")
    String aggregateFile;

    @Bean(name = "databaseName")
    public String getDatabaseName() {
        return StringUtil.getDatabaseNameFromJdbcUrl(datasourceUrl);
    }

    @Bean(name = "commonPropertiesMap")
    public Map<Object, Object> getCommonPropertiesMap() {

        Map<Object, Object> commonPropertiesMap = new HashMap<>();
        Properties properties = new Properties();
        try (InputStream inputStream = new FileInputStream(commonPropertiesPath);
             Reader reader = new InputStreamReader(inputStream, codeEncoding)) {
            properties.load(reader);
            properties.forEach((key, value) -> commonPropertiesMap.put(key, value));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        commonPropertiesMap.put("now", new Date());

        return commonPropertiesMap;
    }

    @Bean(name = "freeMarkerConfiguration")
    public freemarker.template.Configuration getConfiguration() throws IOException {

        File directory = new File(templatePath);

        freemarker.template.Configuration cfg = new freemarker.template.Configuration(freemarker.template.Configuration.VERSION_2_3_24);
        cfg.setDirectoryForTemplateLoading(directory);
        cfg.setDefaultEncoding(codeEncoding);
        cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);

        return cfg;
    }

    /**
     * 所有模版
     *
     * @return
     * @throws IOException
     */
    @Bean(name = "allTemplates")
    public List<FreemarkerModel> getAllTemplates(freemarker.template.Configuration freeMarkerConfiguration,
                                                 Map<Object, Object> commonPropertiesMap) throws IOException {

        List<FreemarkerModel> templates = getMatchingTemplates(freeMarkerConfiguration, commonPropertiesMap,
                templateRelativePath -> StringUtil.isClassFile(templateRelativePath)
                        && !isMatchingFolder(templateRelativePath, iningoredFolder)
                        && !isMatchingFile(templateRelativePath, aggregateFile));
        return templates;
    }

    /**
     * 所有运行一次的模版
     *
     * @return
     * @throws IOException
     */
    @Bean(name = "allOnceTemplates")
    public List<FreemarkerModel> getAllOnceTemplates(freemarker.template.Configuration freeMarkerConfiguration,
                                                     Map<Object, Object> commonPropertiesMap) throws IOException {

        List<FreemarkerModel> templates = getMatchingTemplates(freeMarkerConfiguration, commonPropertiesMap,
                templateRelativePath -> !StringUtil.isClassFile(templateRelativePath)
                        && !isMatchingFolder(templateRelativePath, iningoredFolder)
                        && !isMatchingFile(templateRelativePath, aggregateFile));
        return templates;
    }

    /**
     * 所有聚合的模版
     *
     * @return
     * @throws IOException
     */
    @Bean(name = "allAggregateTemplates")
    public List<FreemarkerModel> getAllAggregateTemplates(freemarker.template.Configuration freeMarkerConfiguration,
                                                          Map<Object, Object> commonPropertiesMap) throws IOException {

        List<FreemarkerModel> templates = getMatchingTemplates(freeMarkerConfiguration, commonPropertiesMap,
                templateRelativePath -> isMatchingFile(templateRelativePath, aggregateFile));
        return templates;
    }



    /**
     * 获取匹配的模版
     *
     * @param freeMarkerConfiguration
     * @param commonPropertiesMap
     * @param predicate
     * @return
     * @throws IOException
     */
    public List<FreemarkerModel> getMatchingTemplates(freemarker.template.Configuration freeMarkerConfiguration,
                                                      Map<Object, Object> commonPropertiesMap,
                                                      Predicate<String> predicate) throws IOException {

        File directory = new File(templatePath);
        String dirAbsolutePath = directory.getAbsolutePath();

        List<File> files = DirectoryUtil.getAllFiles(templatePath);
        List<FreemarkerModel> result = new ArrayList<>();

        for (File file : files) {

            if (file.isHidden()) continue;

            String templateRelativePath = file.getAbsolutePath().substring(dirAbsolutePath.length() + 1);
            if (!predicate.test(templateRelativePath)) continue;

            Template template = freeMarkerConfiguration.getTemplate(templateRelativePath, codeEncoding);

            String fileAbsolutePath = getActualPath(templateRelativePath, commonPropertiesMap);
            FreemarkerModel outModel = new FreemarkerModel(fileAbsolutePath, template);

            result.add(outModel);
        }

        return result;
    }

    /**
     * 是否是匹配的文件夹
     *
     * @param templateRelativePath
     * @return
     */
    private boolean isMatchingFolder(String templateRelativePath, String folders) {

        String[] folderArr = folders.split(",");

        for (String folder : folderArr) {
            if (!folder.endsWith("/"))
                folder += "/";
            if (templateRelativePath.startsWith(folder) || templateRelativePath.contains("/" + folder))
                return true;
        }

        return false;
    }

    /**
     * 是否是匹配模版
     *
     * @param templateRelativePath
     * @return
     */
    private boolean isMatchingFile(String templateRelativePath, String files) {

        String[] fileArr = files.split(",");

        for (String filePath : fileArr) {
            if (templateRelativePath.endsWith("/" + filePath))
                return true;
        }

        return false;
    }


    /**
     * 获取文件输出实际绝对路径
     *
     * @param relativePath
     * @return
     */
    private String getActualPath(String relativePath, Map<Object, Object> commonPropertiesMap) {

        File directory = new File(outPath);
        return directory.getAbsolutePath() + "/" + StringUtil.getActualPath(relativePath, commonPropertiesMap);
    }

}
