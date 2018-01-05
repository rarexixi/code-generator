package org.xi.quick.codegenerator.config;

import freemarker.template.Template;
import freemarker.template.TemplateExceptionHandler;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.xi.quick.codegenerator.model.FreemarkerModel;
import org.xi.quick.codegenerator.utils.DirectoryUtil;
import org.xi.quick.codegenerator.utils.PropertiesUtil;

import java.io.*;
import java.util.*;
import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Configuration
@PropertySource("classpath:config.properties")
public class AppConfig {

    @Value("${path.template}")
    String templatePath;

    @Value("${codeEncoding}")
    String codeEncoding;

    @Value("${folder.ignored}")
    String ignoredFolder;

    @Value("${file.aggregate}")
    String aggregateFile;

    @Value("${path.common.properties}")
    String commonPropertiesPath;

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
    public List<FreemarkerModel> getAllTemplates(freemarker.template.Configuration freeMarkerConfiguration) throws IOException {

        List<FreemarkerModel> templates = getMatchingTemplates(freeMarkerConfiguration,
                templateRelativePath -> isClassFile(templateRelativePath)
                        && !isMatchingFolder(templateRelativePath, ignoredFolder)
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
    public List<FreemarkerModel> getAllOnceTemplates(freemarker.template.Configuration freeMarkerConfiguration) throws IOException {

        List<FreemarkerModel> templates = getMatchingTemplates(freeMarkerConfiguration,
                templateRelativePath -> !isClassFile(templateRelativePath)
                        && !isMatchingFolder(templateRelativePath, ignoredFolder)
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
    public List<FreemarkerModel> getAllAggregateTemplates(freemarker.template.Configuration freeMarkerConfiguration) throws IOException {

        List<FreemarkerModel> templates = getMatchingTemplates(freeMarkerConfiguration,
                templateRelativePath -> isMatchingFile(templateRelativePath, aggregateFile));
        return templates;
    }


    /**
     * 获取匹配的模版
     *
     * @param freeMarkerConfiguration
     * @param predicate
     * @return
     * @throws IOException
     */
    public List<FreemarkerModel> getMatchingTemplates(freemarker.template.Configuration freeMarkerConfiguration,
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

            FreemarkerModel outModel = new FreemarkerModel(templateRelativePath, template);

            result.add(outModel);
        }

        return result;
    }

    /**
     * 获取公共配置
     *
     * @return
     */
    @Bean("commonProperties")
    public Map<Object, Object> getCommonProperties() {

        Map<Object, Object> commonPropertiesMap = PropertiesUtil.getProperties(commonPropertiesPath, codeEncoding);
        commonPropertiesMap.put("now", new Date());

        return commonPropertiesMap;
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
     * 是否是类相关文件
     *
     * @param path
     * @return
     */
    private static boolean isClassFile(String path) {

        Pattern pattern = Pattern.compile("\\$\\{className(_[^\\}]*)?\\}");
        Matcher matcher = pattern.matcher(path);
        return matcher.find();
    }

}
