package org.xi.quick.codegenerator.service;

import org.xi.quick.codegenerator.model.TableModel;

import java.util.List;
import java.util.Set;

public interface TableService {

    /**
     * 获取所有表名
     *
     * @return
     */
    Set<String> getAllTableNameList();

    /**
     * 获取单表
     *
     * @param tableName
     * @return
     */
    TableModel getTable(String tableName);

    /**
     * 获取所有列表
     *
     * @return
     */
    List<TableModel> getAllTables();
}
