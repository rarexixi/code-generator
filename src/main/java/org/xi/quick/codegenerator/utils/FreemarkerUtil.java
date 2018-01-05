package org.xi.quick.codegenerator.utils;

import freemarker.template.TemplateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xi.quick.codegenerator.model.FreemarkerModel;
import org.xi.quick.codegenerator.staticdata.StaticConfigData;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * @author 郗世豪（xish@cloud-young.com）
 * @date 2017/11/27 14:51
 */
public class FreemarkerUtil {

    static Logger logger = LoggerFactory.getLogger(FreemarkerUtil.class);

    /**
     * 生成输出
     *
     * @param outModel
     * @throws IOException
     * @throws TemplateException
     */
    public static void generate(FreemarkerModel outModel) throws IOException, TemplateException {

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
    public static void generate(FreemarkerModel outModel, Map<Object, Object> dataModel) throws IOException, TemplateException {

        dataModel.putAll(StaticConfigData.COMMON_PROPERTIES);

        String absolutePath = getFilePath(outModel, dataModel);

        logger.info("正在生成" + absolutePath);

        //创建文件路径
        DirectoryUtil.createIfNotExists(getAbsoluteDirectory(absolutePath));

        try (OutputStream stream = new FileOutputStream(absolutePath);
             Writer out = new OutputStreamWriter(stream, StaticConfigData.CODE_ENCODING)) {

            outModel.getTemplate().process(dataModel, out);
        }
    }

    public static void delete(FreemarkerModel outModel) {

        delete(outModel, new HashMap<>());
    }

    public static void delete(FreemarkerModel outModel, Map<Object, Object> dataModel) {

        dataModel.putAll(StaticConfigData.COMMON_PROPERTIES);

        String absolutePath = getFilePath(outModel, dataModel);

        logger.info("正在删除" + absolutePath);

        FileUtil.delete(absolutePath);
    }


    static String getFilePath(FreemarkerModel model, Map<Object, Object> dataModel) {

        File directory = new File(StaticConfigData.OUT_DIRECTORY);
        return directory.getAbsolutePath() + "/" + StringUtil.getActualPath(model.getRelativePath(), dataModel);
    }

    static String getAbsoluteDirectory(String absolutePath) {
        return absolutePath.substring(0, absolutePath.lastIndexOf("/"));
    }
}
