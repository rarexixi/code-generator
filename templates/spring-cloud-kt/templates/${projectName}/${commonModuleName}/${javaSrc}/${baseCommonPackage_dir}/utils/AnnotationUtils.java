package ${baseCommonPackage}.utils;

import org.apache.commons.lang3.StringUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.HashMap;
import java.util.Map;

<#include "/include/java_copyright.ftl">
public class AnnotationUtils {

    private static final String DEFAULT_ANNOTATION_VALUE_NAME = "value";

    /**
     * 获取方法中有相应注解的参数值
     *
     * @param annotationClass     注解类
     * @param annotationValueName 注解类要获取的方法名
     * @param method              方法
     * @param args                参数值数组
     * @param paramName           注解值
     * @return
     */
    public static Object getParam(Class<? extends Annotation> annotationClass, String annotationValueName,
                                  Method method, Object[] args, String paramName) {

        Parameter[] parameters = method.getParameters();
        for (int i = 0; i < parameters.length; i++) {
            Parameter parameter = parameters[i];
            Annotation annotation = parameter.getAnnotation(annotationClass);
            if (annotation == null) continue;
            Object annotationValue = getValue(annotation, annotationValueName);
            if (annotationValue == null) continue;
            if (annotation.annotationType() == annotationClass && annotationValue.equals(paramName)) {
                return args[i];
            }
        }
        return null;
    }

    /**
     * 获取方法中有相应注解的参数值，注解类要获取的方法名为value
     *
     * @param annotationClass 注解类
     * @param method          方法
     * @param args            参数值数组
     * @param paramName       注解值
     * @return
     */
    public static Object getParam(Class<? extends Annotation> annotationClass,
                                  Method method, Object[] args, String paramName) {

        return getParam(annotationClass, DEFAULT_ANNOTATION_VALUE_NAME, method, args, paramName);
    }

    /**
     * 获取方法中有相应注解的参数值
     *
     * @param annotationClass     注解类
     * @param annotationValueName 注解类要获取的方法名
     * @param clazz               类名
     * @param methodName          方法名
     * @param parameterTypes      方法参数类型数组
     * @param args                参数值数组
     * @param paramName           注解值
     * @return
     * @throws NoSuchMethodException
     */
    public static Object getParam(Class<? extends Annotation> annotationClass, String annotationValueName,
                                  Class<?> clazz, String methodName, Class<?>[] parameterTypes, Object[] args, String paramName) throws NoSuchMethodException {

        Method method = clazz.getMethod(methodName, parameterTypes);
        return getParam(annotationClass, annotationValueName, method, args, paramName);
    }

    /**
     * 获取方法中有相应注解的参数值，注解类要获取的方法名为value
     *
     * @param annotationClass 注解类
     * @param clazz           类名
     * @param methodName      方法名
     * @param parameterTypes  方法参数类型数组
     * @param args            参数值数组
     * @param paramName       注解值
     * @return
     * @throws NoSuchMethodException
     */
    public static Object getParam(Class<? extends Annotation> annotationClass,
                                  Class<?> clazz, String methodName, Class<?>[] parameterTypes, Object[] args, String paramName) throws NoSuchMethodException {

        return getParam(annotationClass, DEFAULT_ANNOTATION_VALUE_NAME, clazz, methodName, parameterTypes, args, paramName);
    }

    /**
     * 获取方法中有相应注解的参数值Map
     *
     * @param annotationClass     注解类
     * @param annotationValueName 注解类要获取的方法名
     * @param method              方法
     * @param args                参数值数组
     * @param paramNames          注解值列表
     * @return
     */
    public static Map<String, Object> getParams(Class<? extends Annotation> annotationClass, String annotationValueName,
                                                Method method, Object[] args, String[] paramNames) {

        Map<String, Object> result = new HashMap<>();
        Parameter[] parameters = method.getParameters();

        outerFor:
        for (String paramName : paramNames) {
            for (int i = 0; i < parameters.length; i++) {
                Parameter parameter = parameters[i];
                Annotation annotation = parameter.getAnnotation(annotationClass);
                if (annotation == null) continue;
                Object annotationValue = getValue(annotation, annotationValueName);
                if (annotationValue == null) continue;
                if (annotation.annotationType() == annotationClass && annotationValue.equals(paramName)) {
                    result.put(paramName, args[i]);
                    continue outerFor;
                }
            }
        }
        return null;
    }

    /**
     * 获取方法中有相应注解的参数值Map，注解类要获取的方法名为value
     *
     * @param annotationClass 注解类
     * @param method          方法
     * @param args            参数值数组
     * @param paramNames      注解值列表
     * @return
     */
    public static Map<String, Object> getParams(Class<? extends Annotation> annotationClass,
                                                Method method, Object[] args, String[] paramNames) {

        return getParams(annotationClass, DEFAULT_ANNOTATION_VALUE_NAME, method, args, paramNames);
    }

    /**
     * 获取方法中有相应注解的参数值Map
     *
     * @param annotationClass     注解类
     * @param annotationValueName 注解类要获取的方法名
     * @param clazz               类名
     * @param methodName          方法名
     * @param parameterTypes      方法参数类型数组
     * @param args                参数值数组
     * @param paramNames          注解值列表
     * @return
     * @throws NoSuchMethodException
     */
    public static Map<String, Object> getParams(Class<? extends Annotation> annotationClass, String annotationValueName,
                                                Class<?> clazz, String methodName, Class<?>[] parameterTypes, Object[] args, String[] paramNames) throws NoSuchMethodException {

        Method method = clazz.getMethod(methodName, parameterTypes);
        return getParams(annotationClass, annotationValueName, method, args, paramNames);
    }

    /**
     * 获取方法中有相应注解的参数值Map，注解类要获取的方法名为value
     *
     * @param annotationClass 注解类
     * @param clazz           类名
     * @param methodName      方法名
     * @param parameterTypes  方法参数类型数组
     * @param args            参数值数组
     * @param paramNames      注解值列表
     * @return
     * @throws NoSuchMethodException
     */
    public static Map<String, Object> getParams(Class<? extends Annotation> annotationClass,
                                                Class<?> clazz, String methodName, Class<?>[] parameterTypes, Object[] args, String[] paramNames) throws NoSuchMethodException {

        return getParams(annotationClass, DEFAULT_ANNOTATION_VALUE_NAME, clazz, methodName, parameterTypes, args, paramNames);
    }

    /**
     * 获取注解值
     *
     * @param annotation          注解实例
     * @param annotationValueName 注解方法名
     * @return
     */
    public static Object getValue(Annotation annotation, String annotationValueName) {

        if (annotation == null || StringUtils.isEmpty(annotationValueName)) return null;
        try {
            Method method = annotation.annotationType().getMethod(annotationValueName);
            return method.invoke(annotation);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 获取注解值
     *
     * @param annotation           注解实例
     * @param annotationValueNames 注解方法名数组
     * @return
     */
    public static Map<String, Object> getValue(Annotation annotation, String[] annotationValueNames) {

        Map<String, Object> result = new HashMap<>();

        if (annotation == null || annotationValueNames == null || annotationValueNames.length == 0) return null;

        for (String annotationValueName : annotationValueNames) {
            try {
                if (StringUtils.isEmpty(annotationValueName)) continue;
                Method method = annotation.annotationType().getMethod(annotationValueName);
                if (method != null) result.put(annotationValueName, method.invoke(annotation));
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }
        return result;
    }
}
