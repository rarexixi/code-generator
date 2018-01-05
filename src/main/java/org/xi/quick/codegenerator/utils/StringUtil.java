package org.xi.quick.codegenerator.utils;

import java.util.Arrays;
import java.util.Collection;
import java.util.Map;
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
     * @param split
     * @return
     */
    public static String getCamelCaseName(String s, String split) {

        if (s == null || s.isEmpty()) return s;

        String[] tableNameSplit = s.split(split);

        return String.join("", Arrays.stream(tableNameSplit).map(o -> o.substring(0, 1).toUpperCase() + o.substring(1)).collect(Collectors.toList()));
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

    /**
     * 获取文件输出实际路径
     *
     * @param path
     * @param properties
     * @return
     */
    public static String getActualPath(String path, Map<Object, Object> properties) {

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
                    path = path.replace(group, s.replaceAll("\\.", "/"));
                } else if (isLower) {
                    path = path.replace(group, s.toLowerCase());
                } else if (isUpper) {
                    path = path.replace(group, s.toUpperCase());
                } else if (isFirstLower) {
                    path = path.replace(group, getFirstLower(s));
                } else if (isFirstUpper) {
                    path = path.replace(group, getFirstUpper(s));
                } else {
                    path = path.replace(group, s);
                }
            }
        }

        return path;
    }

    /**
     * 是否是类相关文件
     *
     * @param path
     * @return
     */
    public static boolean isClassFile(String path) {

        Pattern pattern = Pattern.compile("\\$\\{className(_[^\\}]*)?\\}");
        Matcher matcher = pattern.matcher(path);
        return matcher.find();
    }
}
