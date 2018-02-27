package org.xi.quick.codegenerator.config;

import freemarker.template.Template;
import freemarker.template.TemplateExceptionHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.xi.quick.codegenerator.model.FreemarkerModel;
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
    GeneratorConfigProperties generatorConfigProperties;

    @Bean(name = "freeMarkerConfiguration")
    public freemarker.template.Configuration getConfiguration() throws IOException {

        File directory = new File(generatorConfigProperties.getTemplatePath());

        freemarker.template.Configuration cfg = new freemarker.template.Configuration(freemarker.template.Configuration.VERSION_2_3_24);
        cfg.setDirectoryForTemplateLoading(directory);
        cfg.setDefaultEncoding(generatorConfigProperties.getEncoding());
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
                                && !isMatchingFolder(templateRelativePath, generatorConfigProperties.getIgnoreFolderSet())
                                && !isMatchingFile(templateRelativePath, generatorConfigProperties.getIgnoreFileSet())

                                && !isMatchingFolder(templateRelativePath, generatorConfigProperties.getAggregateFolderSet())
                                && !isMatchingFile(templateRelativePath, generatorConfigProperties.getAggregateFileSet())

                                && !isMatchingFolder(templateRelativePath, generatorConfigProperties.getJustCopyFolderSet())
                                && !isMatchingFile(templateRelativePath, generatorConfigProperties.getJustCopyFileSet()));
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
                                && !isMatchingFolder(templateRelativePath, generatorConfigProperties.getIgnoreFolderSet())
                                && !isMatchingFile(templateRelativePath, generatorConfigProperties.getIgnoreFileSet())

                                && !isMatchingFolder(templateRelativePath, generatorConfigProperties.getAggregateFolderSet())
                                && !isMatchingFile(templateRelativePath, generatorConfigProperties.getAggregateFileSet())

                                && !isMatchingFolder(templateRelativePath, generatorConfigProperties.getJustCopyFolderSet())
                                && !isMatchingFile(templateRelativePath, generatorConfigProperties.getJustCopyFileSet()));
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
                        isMatchingFolder(templateRelativePath, generatorConfigProperties.getAggregateFolderSet())
                                || isMatchingFile(templateRelativePath, generatorConfigProperties.getAggregateFileSet()));
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
                        isMatchingFolder(templateRelativePath, generatorConfigProperties.getJustCopyFolderSet())
                                || isMatchingFile(templateRelativePath, generatorConfigProperties.getJustCopyFileSet()));
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

        File directory = new File(generatorConfigProperties.getTemplatePath());
        String dirAbsolutePath = directory.getAbsolutePath();

        List<File> files = DirectoryUtil.getAllFiles(generatorConfigProperties.getTemplatePath());
        List<FreemarkerModel> result = new ArrayList<>();

        for (File file : files) {

            if (file.isHidden()) continue;

            String templateRelativePath = file.getAbsolutePath().substring(dirAbsolutePath.length() + 1);
            if (!predicate.test(templateRelativePath)) continue;

            Template template = freeMarkerConfiguration.getTemplate(templateRelativePath, generatorConfigProperties.getEncoding());

            FreemarkerModel outModel = new FreemarkerModel(templateRelativePath, template);

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
    private boolean isMatchingFolder(String templateRelativePath, Set<String> folders) {

        for (String folder : folders) {
            if (!folder.endsWith(SystemUtil.SYSTEM_SLASH))
                folder += SystemUtil.SYSTEM_SLASH;
            if (templateRelativePath.startsWith(folder) || templateRelativePath.contains(SystemUtil.SYSTEM_SLASH + folder))
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
    private boolean isMatchingFile(String templateRelativePath, Set<String> files) {

        int lastIndex = templateRelativePath.lastIndexOf(SystemUtil.SYSTEM_SLASH);
        if (lastIndex >= 0) {
            templateRelativePath = templateRelativePath.substring(lastIndex + 1);
        }

        return files.contains(templateRelativePath);
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
