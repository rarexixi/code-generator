package org.xi.quick.codegenerator.functionalinterface;

/**
 * @author 郗世豪（rarexixi@outlook.com）
 * @date 2017/11/29 15:41
 */
@FunctionalInterface
public interface BinaryConsumer<T1, T2> {

    void accept(T1 t1, T2 t2);
}

