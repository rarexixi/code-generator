package org.xi.quick.codegeneratorkt.mapper

import org.apache.ibatis.annotations.Mapper
import org.apache.ibatis.annotations.Param
import org.xi.quick.codegeneratorkt.entity.Table

@Mapper
interface TablesMapper {

    /**
     * 获取所有表名
     *
     * @param databaseName
     * @return
     */
    fun getAllTableNameList(@Param("databaseName") databaseName: String): Set<String>

    /**
     * 获取单表
     *
     * @param databaseName
     * @param tableNames
     * @return
     */
    fun getTables(@Param("databaseName") databaseName: String, @Param("tableNames") vararg tableNames: String): List<Table>
}
