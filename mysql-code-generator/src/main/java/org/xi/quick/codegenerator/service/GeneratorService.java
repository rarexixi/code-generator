package org.xi.quick.codegenerator.service;

import java.util.Set;

/**
 * @author 郗世豪（rarexixi@outlook.com）
 * @date 2017/11/29 16:31
 */
public interface GeneratorService {

    /**
     * 生成所有数据类
     */
    void generateAll();

    /**
     * 生成数据类
     *
     * @param tableNames
     */
    void generate(String... tableNames);

    /**
     * 生成数据类
     *
     * @param tableNames
     */
    void generateSet(Set<String> tableNames);

    /**
     * 生成所有生成一次的类
     */
    void generateAllOnce();

    /**
     * 生成所有聚合类
     */
    void generateAllAggregate();

    /**
     * 删除所有数据类
     */
    void deleteAll();

    /**
     * 删除数据类
     *
     * @param tableNames
     */
    void delete(String... tableNames);

    /**
     * 删除数据类
     *
     * @param tableNames
     */
    void deleteSet(Set<String> tableNames);

    /**
     * 删除所有生成一次的类
     */
    void deleteAllOnce();

    /**
     * 删除所有聚合类
     */
    void deleteAllAggregate();
}
