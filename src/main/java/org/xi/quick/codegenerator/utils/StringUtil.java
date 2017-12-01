package org.xi.quick.codegenerator.utils;

import java.util.Arrays;
import java.util.Collection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * @author 郗世豪（xish@cloud-young.com）
 * @date 2017/11/27 17:46
 */
public class StringUtil {

    public static String getFirstLower(String s) {

        return s.substring(0, 1).toLowerCase() + s.substring(1);
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
     * @param split
     * @return
     */
    public static String getCamelCaseName(String s, String split) {

        if (s == null || s.isEmpty()) return s;

        String[] tableNameSplit = s.split(split);

        return String.join("", Arrays.stream(tableNameSplit).map(o -> o.substring(0, 1).toUpperCase() + o.substring(1)).collect(Collectors.toList()));
    }

    public static String getDatabaseNameFromJdbcUrl(String url) {

        Pattern pattern = Pattern.compile("/[^(/|\\?)]*\\?");
        Matcher matcher = pattern.matcher(url);
        if (matcher.find()) {
            String group = matcher.group();
            return group.substring(1, group.length() - 1);
        }

        return "";
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

        return Arrays.stream(collection)
                .map(o -> {
                    if (o == null) return "null";
                    if (o instanceof String) {
                        return (String) o;
                    }
                    return o.toString();
                })
                .collect(Collectors.joining(delimiter));
    }
}
