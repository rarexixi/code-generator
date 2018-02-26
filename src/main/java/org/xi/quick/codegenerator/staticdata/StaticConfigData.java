package org.xi.quick.codegenerator.staticdata;

import org.xi.quick.codegenerator.entity.ValidStatusField;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @author 郗世豪（xish@cloud-young.com）
 * @date 2018/01/01 10:31
 */
public class StaticConfigData {

    static {
        NOT_REQUIRED_FIELD_SET = new HashSet<>();
        COMMON_PROPERTIES = new HashMap<>();
    }

    public static String DATABASE_NAME;

    //region 字段相关

    public static Set<String> NOT_REQUIRED_FIELD_SET;       //获取不需要的字段集合
    public static Set<String> SELECT_FIELD_SET;             //获取是选择项的字段集合
    public static Set<String> IMG_URL_FIELD_SET;            //获取图片路径的字段集合
    public static Set<String> VIDEO_URL_FIELD_SET;          //获取视频路径的字段集合
    public static Set<String> DOC_URL_FIELD_SET;            //获取文档路径的字段集合
    public static Set<String> PAGE_URL_FIELD_SET;           //获取页面路径的字段集合
    public static Set<String> OTHER_URL_FIELD_SET;          //获取其他路径的字段集合
    public static Set<String> ALL_URL_FIELD_SET;            //获取所有路径的字段集合
    public static Set<String> CONTENT_FIELD_SET;            //获取内容的字段集合
    public static Set<String> IGNORE_SEARCH_FIELD_SET;      //获取忽略查询的字段集合

    //endregion

    public static ValidStatusField VALID_STATUS_FIELD;

    public static String CODE_ENCODING;

    public static Map<Object, Object> COMMON_PROPERTIES;
}
