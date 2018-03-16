package org.xi.quick.codegenerator.config;

import freemarker.template.Template;
import freemarker.template.TemplateExceptionHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.xi.quick.codegenerator.model.FreemarkerModel;
import org.xi.quick.codegenerator.properties.FieldProperties;
import org.xi.quick.codegenerator.properties.FileProperties;
import org.xi.quick.codegenerator.properties.PathProperties;
import org.xi.quick.codegenerator.properties.GeneratorProperties;
import org.xi.quick.codegenerator.utils.DirectoryUtil;
import org.xi.quick.codegenerator.utils.SystemUtil;

import java.io.*;
import java.util.*;
import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Configuration
public class AppConfig {

    @Autowired
    GeneratorProperties generator;

    @Autowired
    PathProperties generatorPath;

    @Autowired
    FileProperties generatorFile;

    @Bean(name = "freeMarkerConfiguration")
    public freemarker.template.Configuration getConfiguration() throws IOException {

        File directory = new File(generatorPath.getTemplate());

        freemarker.template.Configuration cfg = new freemarker.template.Configuration(freemarker.template.Configuration.VERSION_2_3_24);
        cfg.setDirectoryForTemplateLoading(directory);
        cfg.setDefaultEncoding(generator.getEncoding());
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
                templateRelativePath ->
                        isClassFile(templateRelativePath)
                                && !isMatchingFile(templateRelativePath, generatorFile.getIgnore())
                                && !isMatchingFile(templateRelativePath, generatorFile.getAggregate())
                                && !isMatchingFile(templateRelativePath, generatorFile.getJustCopy()));
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
                templateRelativePath ->
                        !isClassFile(templateRelativePath)
                                && !isMatchingFile(templateRelativePath, generatorFile.getIgnore())
                                && !isMatchingFile(templateRelativePath, generatorFile.getAggregate())
                                && !isMatchingFile(templateRelativePath, generatorFile.getJustCopy()));
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
                templateRelativePath ->
                        isMatchingFile(templateRelativePath, generatorFile.getAggregate()));
        return templates;
    }

    /**
     * 所有仅复制的文件
     *
     * @return
     * @throws IOException
     */
    @Bean(name = "allJustCopyTemplates")
    public List<FreemarkerModel> getAllJustCopyTemplates(freemarker.template.Configuration freeMarkerConfiguration) throws IOException {

        List<FreemarkerModel> templates = getMatchingTemplates(freeMarkerConfiguration,
                templateRelativePath ->
                        isMatchingFile(templateRelativePath, generatorFile.getJustCopy()));
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

        File directory = new File(generatorPath.getTemplate());
        String dirAbsolutePath = directory.getAbsolutePath();

        List<File> files = DirectoryUtil.getAllFiles(generatorPath.getTemplate());
        List<FreemarkerModel> result = new ArrayList<>();

        for (File file : files) {

            if (file.isHidden()) continue;

            String templateRelativePath = file.getAbsolutePath().substring(dirAbsolutePath.length() + 1);
            if (!predicate.test(templateRelativePath)) continue;

            Template template = freeMarkerConfiguration.getTemplate(templateRelativePath, generator.getEncoding());

            FreemarkerModel outModel = new FreemarkerModel(templateRelativePath, template);

            result.add(outModel);
        }

        return result;
    }

    /**
     * 是否是匹配模版
     *
     * @param templateRelativePath
     * @return
     */
    private boolean isMatchingFile(String templateRelativePath, Set<String> files) {

        for (String file : files) {
            if (templateRelativePath.startsWith(file) || templateRelativePath.contains(SystemUtil.SYSTEM_SLASH + file))
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
