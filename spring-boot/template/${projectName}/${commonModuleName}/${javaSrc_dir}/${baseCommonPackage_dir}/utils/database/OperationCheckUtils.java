package ${baseCommonPackage}.utils.database;

import ${baseCommonPackage}.annotation.InsertNotNull;
import ${baseCommonPackage}.annotation.UpdateNotNull;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

<#include "/include/java_copyright.ftl">
public class OperationCheckUtils {

    /**
     * 获取新增验证消息
     *
     * @param t
     * @param <T>
     * @return
     */
    public static <T> String checkInsert(T t) {

        Class c = t.getClass();

        Field[] fields = c.getDeclaredFields();
        for (Field field : fields) {
            InsertNotNull annotation = field.getAnnotation(InsertNotNull.class);
            if (annotation != null && annotation.required()) {
                field.setAccessible(true);
                Object obj = null;
                try {
                    obj = field.get(t);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
                if (isNullOrEmpty(obj)) {
                    return annotation.name();
                }
            }
        }

        return "";
    }

    /**
     * 获取更新验证消息
     *
     * @param t
     * @param <T>
     * @return
     */
    public static <T> String checkUpdate(T t) {

        Class c = t.getClass();

        Field[] fields = c.getDeclaredFields();
        for (Field field : fields) {
            UpdateNotNull annotation = field.getAnnotation(UpdateNotNull.class);
            if (annotation != null && annotation.required()) {
                field.setAccessible(true);
                Object obj = null;
                try {
                    obj = field.get(t);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
                if (isNullOrEmpty(obj)) {
                    return annotation.name();
                }
            }
        }

        return "";
    }

    /**
     * 获取新增验证消息列表
     *
     * @param t
     * @param <T>
     * @return
     */
    public static <T> List<String> checkInsertList(T t) {

        List<String> messageList = new ArrayList<>();

        Class c = t.getClass();

        Field[] fields = c.getDeclaredFields();
        for (Field field : fields) {
            InsertNotNull annotation = field.getAnnotation(InsertNotNull.class);
            if (annotation != null && annotation.required()) {
                field.setAccessible(true);
                Object obj = null;
                try {
                    obj = field.get(t);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
                if (isNullOrEmpty(obj)) {
                    messageList.add(annotation.name());
                }
            }
        }

        return messageList;
    }

    /**
     * 获取更新验证消息列表
     *
     * @param t
     * @param <T>
     * @return
     */
    public static <T> List<String> checkUpdateList(T t) {

        List<String> messageList = new ArrayList<>();

        Class c = t.getClass();

        Field[] fields = c.getDeclaredFields();
        for (Field field : fields) {
            UpdateNotNull annotation = field.getAnnotation(UpdateNotNull.class);
            if (annotation != null && annotation.required()) {
                field.setAccessible(true);
                Object obj = null;
                try {
                    obj = field.get(t);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
                if (isNullOrEmpty(obj)) {
                    messageList.add(annotation.name());
                }
            }
        }

        return messageList;
    }

    public static boolean isNullOrEmpty(Object... objects) {

        for (Object object : objects) {
            if (isNullOrEmpty(object)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isNullOrEmpty(Object object) {
        if (object == null) {
            return true;
        }
        if (object instanceof CharSequence) {
            return ((CharSequence) object).length() == 0;
        }
        if (object instanceof Collection) {
            return ((Collection) object).isEmpty();
        }
        if (object instanceof Map) {
            return ((Map) object).isEmpty();
        }
        if (object instanceof Object[]) {
            Object[] objects = (Object[]) object;
            if (objects.length == 0) {
                return true;
            }
            boolean empty = true;
            for (int i = 0; i < objects.length; i++) {
                if (!isNullOrEmpty(objects[i])) {
                    empty = false;
                    break;
                }
            }
            return empty;
        }
        return false;
    }
}
