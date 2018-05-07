package ${baseCommonPackage}.utils;

import ${baseCommonPackage}.annotation.ParamName;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.HashMap;
import java.util.Map;

<#include "/include/java_copyright.ftl">
public class AnnotationUtils {

    public static Object getParam(Class<?> clazz, String methodName, Class<?>[] parameterTypes, Object[] args, String paramName) throws NoSuchMethodException {

        Method method = clazz.getMethod(methodName, parameterTypes);
        Parameter[] parameters = method.getParameters();
        for (int i = 0; i < parameters.length; i++) {
            Parameter parameter = parameters[i];
            for (Annotation annotation : parameter.getAnnotations()) {
                if (annotation instanceof ParamName && ((ParamName) annotation).value().equalsIgnoreCase(paramName)) {
                    return args[i];
                }
            }
        }
        return null;
    }

    public static Map<String, Object> getParams(Class<?> clazz, String methodName, Class<?>[] parameterTypes, Object[] args, String[] paramNames) throws NoSuchMethodException {

        Map<String, Object> result = new HashMap<>();
        Method method = clazz.getMethod(methodName, parameterTypes);
        Parameter[] parameters = method.getParameters();
        tab: for (String paramName : paramNames) {
            for (int i = 0; i < parameters.length; i++) {
                Parameter parameter = parameters[i];
                for (Annotation annotation : parameter.getAnnotations()) {
                    if (annotation instanceof ParamName && ((ParamName) annotation).value().equalsIgnoreCase(paramName)) {
                        result.put(paramName, args[i]);
                        continue tab;
                    }
                }
            }
        }
        return null;
    }
}
