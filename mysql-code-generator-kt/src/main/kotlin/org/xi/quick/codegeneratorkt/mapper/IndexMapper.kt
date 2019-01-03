package org.xi.quick.codegeneratorkt.mapper

import org.apache.ibatis.annotations.Mapper
import org.apache.ibatis.annotations.Param
import org.xi.quick.codegeneratorkt.entity.Index

@Mapper
interface IndexMapper {

    /**
     * 获取表的索引
     *
     * @param databaseName
     * @param tableName
     * @return
     */
    fun getStatistics(@Param("databaseName") databaseName: String, @Param("tableName") tableName: String): List<Index>
}
