package org.xi.quick.codegenerator.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.xi.quick.codegenerator.entity.Statistics;

import java.util.List;

@Mapper
public interface StatisticsMapper {

    /**
     * 获取表的索引
     *
     * @param databaseName
     * @param tableName
     * @return
     */
    List<Statistics> getStatistics(@Param("databaseName") String databaseName, @Param("tableName") String tableName);
}
