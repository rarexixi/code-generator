package org.xi.quick.codegenerator.service.impl;

import freemarker.template.TemplateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.xi.quick.codegenerator.config.GeneratorConfigProperties;
import org.xi.quick.codegenerator.functionalinterface.BinaryConsumer;
import org.xi.quick.codegenerator.model.FreemarkerModel;
import org.xi.quick.codegenerator.model.TableModel;
import org.xi.quick.codegenerator.service.GeneratorService;
import org.xi.quick.codegenerator.service.TableService;
import org.xi.quick.codegenerator.utils.DirectoryUtil;
import org.xi.quick.codegenerator.utils.FileUtil;
import org.xi.quick.codegenerator.utils.StringUtil;
import org.xi.quick.codegenerator.utils.SystemUtil;

import java.io.*;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author 郗世豪（xish@cloud-young.com）
 * @date 2017/11/29 16:31
 */
@Service("generatorService")
public class GeneratorServiceImpl implements GeneratorService {

    Logger logger = LoggerFactory.getLogger(GeneratorServiceImpl.class);

    @Autowired
    GeneratorConfigProperties generatorConfigProperties;

    @Autowired
    List<FreemarkerModel> allTemplates;

    @Autowired
    List<FreemarkerModel> allOnceTemplates;

    @Autowired
    List<FreemarkerModel> allJustCopyTemplates;

    @Autowired
    List<FreemarkerModel> allAggregateTemplates;

    @Autowired
    TableService tableService;

    //region 生成

    /**
     * 生成所有数据类
     */
    @Override
    public void generateAll() {

        loopAll((template, table) -> generate(template, table));
    }

    /**
     * 生成数据类
     *
     * @param tableNames
     */
    @Override
    public void generate(String... tableNames) {

        loop((template, table) -> generate(template, table), tableNames);
    }

    /**
     * 生成所有生成一次的类
     */
    @Override
    public void generateAllOnce() {

        Map<Object, Object> dataModel = new HashMap<>();
        generateOnce(allOnceTemplates, dataModel);
        generateCopy(allJustCopyTemplates, dataModel);
    }

    /**
     * 生成所有聚合类
     */
    @Override
    public void generateAllAggregate() {

        List<TableModel> tables = tableService.getAllTables();
        Map<Object, Object> dataModel = new HashMap<>();
        dataModel.put("tableModels", tables);
        generateOnce(allAggregateTemplates, dataModel);
    }

    //endregion

    //region 删除

    /**
     * 删除所有数据类
     */
    @Override
    public void deleteAll() {

        loopAll((template, table) -> delete(template, table));
    }

    /**
     * 删除数据类
     *
     * @param tableNames
     */
    @Override
    public void delete(String... tableNames) {

        loop((template, table) -> delete(template, table), tableNames);
    }

    /**
     * 删除所有生成一次的类
     */
    @Override
    public void deleteAllOnce() {

        deleteOnce(allOnceTemplates);
        deleteOnce(allJustCopyTemplates);
    }

    /**
     * 删除所有聚合类
     */
    @Override
    public void deleteAllAggregate() {

        deleteOnce(allAggregateTemplates);
    }

    //endregion

    //region 私有方法

    private void generate(FreemarkerModel template, TableModel table) {

        Map<Object, Object> dataModel = new HashMap<>();
        dataModel.put("table", table);
        dataModel.put("className", table.getTableClassName());

        try {
            generate(template, dataModel);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TemplateException e) {
            e.printStackTrace();
        }
    }

    private void generateOnce(List<FreemarkerModel> templates, Map<Object, Object> dataModel) {

        for (FreemarkerModel template : templates) {
            try {
                generate(template, dataModel);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (TemplateException e) {
                e.printStackTrace();
            }
        }
    }

    private void generateCopy(List<FreemarkerModel> templates, Map<Object, Object> dataModel) {

        for (FreemarkerModel outModel : templates) {

            dataModel.putAll(generatorConfigProperties.getCommonProperties());

            String targetPath = getFilePath(outModel, dataModel);
            String sourcePath = generatorConfigProperties.getTemplatePath() + outModel.getRelativePath();

            logger.info("正在生成" + targetPath);
            try {
                //创建文件路径
                DirectoryUtil.createIfNotExists(getAbsoluteDirectory(targetPath));
                FileUtil.deleteIfExists(new File(targetPath));
                Files.copy(new File(sourcePath).toPath(), new File(targetPath).toPath());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void delete(FreemarkerModel template, TableModel table) {

        Map<Object, Object> dataModel = new HashMap<>();
        dataModel.put("className", table.getTableClassName());
        delete(template, dataModel);
    }

    private void deleteOnce(List<FreemarkerModel> templates) {

        for (FreemarkerModel template : templates) {
            delete(template);
        }
    }

    /**
     * 执行操作
     *
     * @param consumer   消费方法
     * @param tableNames 要操作的表名
     */
    private void loop(BinaryConsumer<FreemarkerModel, TableModel> consumer, String... tableNames) {

        for (String tableName : tableNames) {
            TableModel table = tableService.getTable(tableName);
            if (table != null) {
                allTemplates.forEach(template -> consumer.accept(template, table));
            }
        }
    }

    /**
     * 执行操作
     *
     * @param consumer 消费方法
     */
    private void loopAll(BinaryConsumer<FreemarkerModel, TableModel> consumer) {

        List<TableModel> tables = tableService.getAllTables();

        for (TableModel table : tables) {
            allTemplates.forEach(template -> consumer.accept(template, table));
        }
    }

    /**
     * 生成输出
     *
     * @param outModel
     * @throws IOException
     * @throws TemplateException
     */
    private void generate(FreemarkerModel outModel) throws IOException, TemplateException {

        generate(outModel, new HashMap<>());
    }

    /**
     * 生成输出
     *
     * @param outModel
     * @param dataModel
     * @throws IOException
     * @throws TemplateException
     */
    private void generate(FreemarkerModel outModel, Map<Object, Object> dataModel) throws IOException, TemplateException {

        dataModel.putAll(generatorConfigProperties.getCommonProperties());

        String absolutePath = getFilePath(outModel, dataModel);

        System.out.println("正在生成" + absolutePath);

        //创建文件路径
        DirectoryUtil.createIfNotExists(getAbsoluteDirectory(absolutePath));

        try (OutputStream stream = new FileOutputStream(absolutePath);
             Writer out = new OutputStreamWriter(stream, generatorConfigProperties.getEncoding())) {

            outModel.getTemplate().process(dataModel, out);
        }
    }

    private void delete(FreemarkerModel outModel) {

        delete(outModel, new HashMap<>());
    }

    private void delete(FreemarkerModel outModel, Map<Object, Object> dataModel) {

        dataModel.putAll(generatorConfigProperties.getCommonProperties());

        String absolutePath = getFilePath(outModel, dataModel);

        logger.info("正在删除" + absolutePath);

        FileUtil.delete(absolutePath);
    }


    private String getFilePath(FreemarkerModel model, Map<Object, Object> dataModel) {

        File directory = new File(generatorConfigProperties.getOutPath());
        return directory.getAbsolutePath() + SystemUtil.SYSTEM_SLASH + getActualPath(model.getRelativePath(), dataModel);
    }

    private String getAbsoluteDirectory(String absolutePath) {
        return absolutePath.substring(0, absolutePath.lastIndexOf(SystemUtil.SYSTEM_SLASH));
    }


    /**
     * 获取文件输出实际路径
     *
     * @param path
     * @param properties
     * @return
     */
    private String getActualPath(String path, Map<Object, Object> properties) {

        Pattern pattern = Pattern.compile("\\$\\{[^\\}]*\\}");
        Matcher matcher = pattern.matcher(path);
        while (matcher.find()) {
            String group = matcher.group();
            String key = group.substring(2, group.length() - 1);

            boolean isDir = key.endsWith("_dir");
            boolean isLower = key.endsWith("_lower");
            boolean isUpper = key.endsWith("_upper");
            boolean isFirstLower = key.endsWith("_firstLower");
            boolean isFirstUpper = key.endsWith("_firstUpper");
            if (isDir) {
                key = key.substring(0, key.length() - 4);
            } else if (isLower || isUpper) {
                key = key.substring(0, key.length() - 6);
            } else if (isFirstLower || isFirstUpper) {
                key = key.substring(0, key.length() - 11);
            }

            Object value = properties.get(key);

            if (value != null) {
                String s = (String) value;
                if (isDir) {
                    path = path.replace(group, s.replaceAll("\\.", SystemUtil.REGEX_SYSTEM_SLASH));
                } else if (isLower) {
                    path = path.replace(group, s.toLowerCase());
                } else if (isUpper) {
                    path = path.replace(group, s.toUpperCase());
                } else if (isFirstLower) {
                    path = path.replace(group, StringUtil.getFirstLower(s));
                } else if (isFirstUpper) {
                    path = path.replace(group, StringUtil.getFirstUpper(s));
                } else {
                    path = path.replace(group, s);
                }
            }
        }

        return path;
    }

    //endregion
}
