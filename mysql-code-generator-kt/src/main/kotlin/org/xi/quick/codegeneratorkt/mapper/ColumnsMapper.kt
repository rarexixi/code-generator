package org.xi.quick.codegeneratorkt.mapper

import org.apache.ibatis.annotations.Mapper
import org.apache.ibatis.annotations.Param
import org.xi.quick.codegeneratorkt.entity.Column

@Mapper
interface ColumnsMapper {

    /**
     * 获取表的列
     *
     * @param databaseName
     * @param tableName
     * @return
     */
    fun getColumns(@Param("databaseName") databaseName: String, @Param("tableName") tableName: String): List<Column>

    /**
     * 获取表的列
     *
     * @param databaseName
     * @param tableName
     * @return
     */
    fun getColumnsWithIndex(@Param("databaseName") databaseName: String, @Param("tableName") tableName: String): List<Column>
}
