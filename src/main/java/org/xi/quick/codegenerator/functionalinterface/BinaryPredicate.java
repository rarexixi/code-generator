package org.xi.quick.codegenerator.functionalinterface;

/**
 * @author 郗世豪（xish@cloud-young.com）
 * @date 2017/11/30 10:15
 */
@FunctionalInterface
public interface BinaryPredicate<T1, T2> {

    boolean test(T1 t1, T2 t2);
}
