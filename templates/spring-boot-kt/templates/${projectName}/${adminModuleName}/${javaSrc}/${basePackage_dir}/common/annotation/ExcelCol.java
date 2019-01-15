package ${basePackage}.common.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface ExcelCol {

    String value() default "";

    int width() default 0;

    String condition() default "";

    int order() default 0;

    String formatter() default "";
}
