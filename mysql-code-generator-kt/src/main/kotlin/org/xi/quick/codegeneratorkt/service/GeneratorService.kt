package org.xi.quick.codegeneratorkt.service


interface GeneratorService {

    /**
     * 生成数据类
     *
     * @param tableNames
     */
    fun gen(vararg tableNames: String)

    /**
     * 生成所有生成一次的类
     */
    fun genBase()

    /**
     * 生成所有聚合类
     */
    fun genAggr(vararg tableNames: String)


    /**
     * 删除数据类
     *
     * @param tableNames
     */
    fun del(vararg tableNames: String)

    /**
     * 删除所有生成一次的类
     */
    fun delBase()

    /**
     * 删除所有聚合类
     */
    fun delAggr()
}
