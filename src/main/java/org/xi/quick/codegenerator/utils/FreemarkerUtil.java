package org.xi.quick.codegenerator.utils;

import freemarker.template.TemplateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xi.quick.codegenerator.model.FreemarkerModel;

import java.io.*;

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
     * @param dataModel
     * @throws IOException
     * @throws TemplateException
     */
    public static void generate(FreemarkerModel outModel, Object dataModel, String encoding) throws IOException, TemplateException {

        generate(outModel, null, dataModel, encoding);
    }

    /**
     * 生成输出
     *
     * @param outModel
     * @param dataModel
     * @throws IOException
     * @throws TemplateException
     */
    public static void generate(FreemarkerModel outModel, String tableClassName, Object dataModel, String encoding) throws IOException, TemplateException {

        String absolutePath = getFilePath(outModel, tableClassName);

        logger.info("正在生成" + absolutePath);

        //创建文件路径
        DirectoryUtil.createIfNotExists(outModel.getAbsoluteDirectory());

        try (OutputStream stream = new FileOutputStream(absolutePath);
             Writer out = new OutputStreamWriter(stream, encoding)) {

            outModel.getTemplate().process(dataModel, out);
        }
    }

    public static void delete(FreemarkerModel outModel) {

        delete(outModel, null);
    }

    public static void delete(FreemarkerModel outModel, String tableClassName) {

        String absolutePath = getFilePath(outModel, tableClassName);

        logger.info("正在删除" + absolutePath);

        FileUtil.delete(absolutePath);
    }


    public static String getFilePath(FreemarkerModel model, String tableClassName) {

        String absolutePath = model.getAbsolutePath();
        if (absolutePath.contains("${className}") && tableClassName != null && !tableClassName.isEmpty()) {
            absolutePath = absolutePath.replace("${className}", tableClassName);
        }
        return absolutePath;
    }
}
