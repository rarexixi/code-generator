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

    public static Set<String> NOT_REQUIRED_FIELD_SET;

    public static ValidStatusField VALID_STATUS_FIELD;

    public static String CODE_ENCODING;

    public static String OUT_DIRECTORY;

    public static Map<Object, Object> COMMON_PROPERTIES;
}
