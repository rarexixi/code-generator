package org.xi.quick.codegeneratorkt.mapper

import org.apache.ibatis.annotations.Mapper
import org.apache.ibatis.annotations.Param
import org.xi.quick.codegeneratorkt.entity.Statistics

@Mapper
interface StatisticsMapper {

    /**
     * 获取表的索引
     *
     * @param databaseName
     * @param tableName
     * @return
     */
    fun getStatistics(@Param("databaseName") databaseName: String, @Param("tableName") tableName: String): List<Statistics>
}
