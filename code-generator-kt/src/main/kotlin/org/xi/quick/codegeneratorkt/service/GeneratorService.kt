package org.xi.quick.codegeneratorkt.service


interface GeneratorService {

    /**
     * 生成所有数据类
     */
    fun generateAll()

    /**
     * 生成数据类
     *
     * @param tableNames
     */
    fun generate(vararg tableNames: String)

    /**
     * 生成所有生成一次的类
     */
    fun generateAllOnce()

    /**
     * 生成所有聚合类
     */
    fun generateAllAggregate()

    /**
     * 删除所有数据类
     */
    fun deleteAll()

    /**
     * 删除数据类
     *
     * @param tableNames
     */
    fun delete(vararg tableNames: String)

    /**
     * 删除所有生成一次的类
     */
    fun deleteAllOnce()

    /**
     * 删除所有聚合类
     */
    fun deleteAllAggregate()
}
