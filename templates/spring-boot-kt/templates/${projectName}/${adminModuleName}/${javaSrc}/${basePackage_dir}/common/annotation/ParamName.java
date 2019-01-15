package ${basePackage}.common.annotation;

import java.lang.annotation.*;

<#include "/include/java_copyright.ftl">
@Target({ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ParamName {

    String value() default "";
}
