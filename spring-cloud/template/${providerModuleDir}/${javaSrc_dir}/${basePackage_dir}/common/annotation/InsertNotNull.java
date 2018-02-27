package ${basePackage}.common.annotation;

/**
 * @author 郗世豪（xish@cloud-young.com）
 * @date 2017/11/28 17:25
 */
@java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.RUNTIME)
@java.lang.annotation.Target({java.lang.annotation.ElementType.FIELD, java.lang.annotation.ElementType.METHOD})
@java.lang.annotation.Documented
public @interface InsertNotNull {
    String name() default "";
    boolean required() default true;
}
