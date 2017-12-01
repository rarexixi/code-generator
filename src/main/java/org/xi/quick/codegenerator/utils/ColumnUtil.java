package org.xi.quick.codegenerator.utils;

import java.util.HashMap;
import java.util.Map;

/**
 * @author 郗世豪（xish@cloud-young.com）
 * @date 2017/11/27 19:42
 */
public class ColumnUtil {

    private static Map<String, String> typeMap;

    static {
        typeMap = new HashMap<>();
        typeMap.put("tinyint", "Integer");
        typeMap.put("smallint", "Integer");
        typeMap.put("int", "Integer");
        typeMap.put("bigint", "Long");
        typeMap.put("float", "Float");
        typeMap.put("double", "Double");
        typeMap.put("decimal", "Decimal");
        typeMap.put("numeric", "BigDecimal");
        typeMap.put("bit", "Boolean");
        typeMap.put("character", "Character");
        typeMap.put("varchar", "String");
        typeMap.put("char", "String");
        typeMap.put("date", "Date");
        typeMap.put("datetime", "Date");
        typeMap.put("timestamp", "Date");
    }

    public static String getFieldType(String dataType) {
        String type = typeMap.get(dataType);
        return type == null ? dataType : type;
    }
}
