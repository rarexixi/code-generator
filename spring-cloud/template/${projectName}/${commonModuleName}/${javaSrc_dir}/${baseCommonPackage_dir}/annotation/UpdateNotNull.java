package ${baseCommonPackage}.annotation;

<#include "/include/java_copyright.ftl">
@java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.RUNTIME)
@java.lang.annotation.Target({java.lang.annotation.ElementType.FIELD, java.lang.annotation.ElementType.METHOD})
@java.lang.annotation.Documented
public @interface UpdateNotNull {
    String name() default "";
    boolean required() default true;
}
