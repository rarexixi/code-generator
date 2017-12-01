package org.xi.quick.codegenerator.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.xi.quick.codegenerator.entity.Table;

import java.util.List;
import java.util.Set;

@Mapper
public interface TablesMapper {

    /**
     * 获取所有表名
     *
     * @param databaseName
     * @return
     */
    Set<String> getAllTableNameList(@Param("databaseName") String databaseName);

    /**
     * 获取单表
     *
     * @param databaseName
     * @param tableName
     * @return
     */
    Table getTable(@Param("databaseName") String databaseName, @Param("tableName") String tableName);

    /**
     * 获取列表
     *
     * @param databaseName
     * @param tableName
     * @return
     */
    List<Table> getTables(@Param("databaseName") String databaseName, @Param("tableName") String tableName);
}
