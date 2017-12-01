package org.xi.quick.codegenerator.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.xi.quick.codegenerator.entity.Column;

import java.util.List;

@Mapper
public interface ColumnsMapper {

    /**
     * 获取表的列
     *
     * @param databaseName
     * @param tableName
     * @return
     */
    List<Column> getColumns(@Param("databaseName") String databaseName, @Param("tableName") String tableName);
}
