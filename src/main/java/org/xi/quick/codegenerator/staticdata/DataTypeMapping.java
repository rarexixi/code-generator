package org.xi.quick.codegenerator.staticdata;

import java.util.HashMap;
import java.util.Map;

/**
 * @author 郗世豪（xish@cloud-young.com）
 * @date 2018/01/05 22:38
 */
public class DataTypeMapping {

    static {
        TYPE_MAP = new HashMap<>();
    }

    public static Map<Object, Object> TYPE_MAP;

    public static String getFieldType(String dataType) {
        String type = (String) TYPE_MAP.get(dataType);
        return type == null ? dataType : type;
    }
}
