package org.xi.quick.codegenerator.utils;

import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

/**
 * @author 郗世豪（xish@cloud-young.com）
 * @date 2017/11/27 17:46
 */
public class StringUtil {

    /**
     * 获取首字母小写
     *
     * @param s
     * @return
     */
    public static String getFirstLower(String s) {

        return s.substring(0, 1).toLowerCase() + s.substring(1);
    }

    /**
     * 获取首字母大写
     *
     * @param s
     * @return
     */
    public static String getFirstUpper(String s) {

        return s.substring(0, 1).toUpperCase() + s.substring(1);
    }

    /**
     * 获取驼峰命名
     *
     * @param s
     * @return
     */
    public static String getCamelCaseName(String s) {

        return getCamelCaseName(s, "_");
    }

    /**
     * 获取驼峰命名
     *
     * @param s
     * @param delimiter
     * @return
     */
    public static String getCamelCaseName(String s, String delimiter) {

        if (s == null || s.isEmpty()) return s;

        String[] tableNameSplit = s.split(delimiter);

        String camelCaseName = Arrays.stream(tableNameSplit)
                .map(o -> o.substring(0, 1).toUpperCase() + o.substring(1).toLowerCase())
                .collect(Collectors.joining(""));

        return camelCaseName;
    }


    public static <T> String join(String delimiter, Collection<T> collection) {

        return collection.stream()
                .map(o -> {
                    if (o == null) return "null";
                    if (o instanceof String) {
                        return (String) o;
                    }
                    return o.toString();
                })
                .collect(Collectors.joining(delimiter));
    }

    public static <T> String join(String delimiter, T[] collection) {

        return join(delimiter, Arrays.asList(collection));
    }
}
