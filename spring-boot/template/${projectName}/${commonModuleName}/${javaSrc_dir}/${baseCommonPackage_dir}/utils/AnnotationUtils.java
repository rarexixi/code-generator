package ${baseCommonPackage}.utils;

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
     * @param method              方法
     * @param args                参数值数组
     * @param paramName           注解值
     * @param annotationClass     注解类
     * @param annotationValueName 注解类要获取的方法名
     * @return
     * @throws NoSuchMethodException
     */
    public static Object getParam(Method method, Object[] args, String paramName, Class<? extends Annotation> annotationClass, String annotationValueName) throws NoSuchMethodException {

        Parameter[] parameters = method.getParameters();
        for (int i = 0; i < parameters.length; i++) {
            Parameter parameter = parameters[i];
            Annotation annotation = parameter.getAnnotation(annotationClass);
            if(annotation == null) continue;
            Object annotationValue = getValue(annotation, annotationValueName);
            if (annotationValue == null) continue;
            if (annotation.annotationType() == annotationClass && annotationValue.equals(paramName)) {
                return args[i];
            }
        }
        return null;
    }

    /**
     * 获取方法中有相应注解的参数值
     *
     * @param method          方法
     * @param args            参数值数组
     * @param paramName       注解值
     * @param annotationClass 注解类
     * @return
     * @throws NoSuchMethodException
     */
    public static Object getParam(Method method, Object[] args, String paramName, Class<? extends Annotation> annotationClass) throws NoSuchMethodException {

        return getParam(method, args, paramName, annotationClass, DEFAULT_ANNOTATION_VALUE_NAME);
    }

    /**
     * 获取方法中有相应注解的参数值
     *
     * @param clazz               类名
     * @param methodName          方法名
     * @param parameterTypes      方法参数类型数组
     * @param args                参数值数组
     * @param paramName           注解值
     * @param annotationClass     注解类
     * @param annotationValueName 注解类要获取的方法名
     * @return
     * @throws NoSuchMethodException
     */
    public static Object getParam(Class<?> clazz, String methodName, Class<?>[] parameterTypes, Object[] args, String paramName, Class<? extends Annotation> annotationClass, String annotationValueName) throws NoSuchMethodException {

        Method method = clazz.getMethod(methodName, parameterTypes);
        return getParam(method, args, paramName, annotationClass, annotationValueName);
    }

    /**
     * 获取方法中有相应注解的参数值
     *
     * @param clazz           类名
     * @param methodName      方法名
     * @param parameterTypes  方法参数类型数组
     * @param args            参数值数组
     * @param paramName       注解值
     * @param annotationClass 注解类
     * @return
     * @throws NoSuchMethodException
     */
    public static Object getParam(Class<?> clazz, String methodName, Class<?>[] parameterTypes, Object[] args, String paramName, Class<? extends Annotation> annotationClass) throws NoSuchMethodException {

        return getParam(clazz, methodName, parameterTypes, args, paramName, annotationClass, DEFAULT_ANNOTATION_VALUE_NAME);
    }

    /**
     * 获取方法中有相应注解的参数值Map
     *
     * @param method              方法
     * @param args                参数值数组
     * @param paramNames          注解值列表
     * @param annotationClass     注解类
     * @param annotationValueName 注解类要获取的方法名
     * @return
     * @throws NoSuchMethodException
     */
    public static Map<String, Object> getParams(Method method, Object[] args, String[] paramNames, Class<? extends Annotation> annotationClass, String annotationValueName) throws NoSuchMethodException {

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
     * 获取方法中有相应注解的参数值Map
     *
     * @param method          方法
     * @param args            参数值数组
     * @param paramNames      注解值列表
     * @param annotationClass 注解类
     * @return
     * @throws NoSuchMethodException
     */
    public static Map<String, Object> getParams(Method method, Object[] args, String[] paramNames, Class<? extends Annotation> annotationClass) throws NoSuchMethodException {

        return getParams(method, args, paramNames, annotationClass, DEFAULT_ANNOTATION_VALUE_NAME);
    }

    /**
     * 获取方法中有相应注解的参数值Map
     *
     * @param clazz               类名
     * @param methodName          方法名
     * @param parameterTypes      方法参数类型数组
     * @param args                参数值数组
     * @param paramNames          注解值列表
     * @param annotationClass     注解类
     * @param annotationValueName 注解类要获取的方法名
     * @return
     * @throws NoSuchMethodException
     */
    public static Map<String, Object> getParams(Class<?> clazz, String methodName, Class<?>[] parameterTypes, Object[] args, String[] paramNames, Class<? extends Annotation> annotationClass, String annotationValueName) throws NoSuchMethodException {

        Method method = clazz.getMethod(methodName, parameterTypes);
        return getParams(method, args, paramNames, annotationClass, annotationValueName);
    }

    /**
     * 获取方法中有相应注解的参数值Map
     *
     * @param clazz           类名
     * @param methodName      方法名
     * @param parameterTypes  方法参数类型数组
     * @param args            参数值数组
     * @param paramNames      注解值列表
     * @param annotationClass 注解类
     * @return
     * @throws NoSuchMethodException
     */
    public static Map<String, Object> getParams(Class<?> clazz, String methodName, Class<?>[] parameterTypes, Object[] args, String[] paramNames, Class<? extends Annotation> annotationClass) throws NoSuchMethodException {

        return getParams(clazz, methodName, parameterTypes, args, paramNames, annotationClass, DEFAULT_ANNOTATION_VALUE_NAME);
    }

    /**
     * 获取注解值
     *
     * @param annotation 注解实例
     * @param annotationValueName 注解方法名
     * @return
     */
    private static Object getValue(Annotation annotation, String annotationValueName) {
        try {
            Method method = annotation.annotationType().getMethod(annotationValueName);
            return method.invoke(annotation);
        } catch (Exception e) {
            return null;
        }
    }
}
