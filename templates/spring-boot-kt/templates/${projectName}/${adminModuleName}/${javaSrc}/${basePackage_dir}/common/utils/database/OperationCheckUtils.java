package ${basePackage}.common.utils.database;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

<#include "/include/java_copyright.ftl">
public class OperationCheckUtils {

    private static <T> boolean checkFieldNull(T t, Field field) {

        field.setAccessible(true);

        Object obj = null;
        try {
            obj = field.get(t);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        if (isNullOrEmpty(obj)) {
            return true;
        }
        return false;
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
