package org.xi.quick.codegeneratorkt.service

import org.xi.quick.codegeneratorkt.model.TableModel

interface TableService {

    /**
     * 获取所有表名
     *
     * @return
     */
    fun getAllTableNameList(): Set<String>

    /**
     * 获取单表
     *
     * @param tableName
     * @return
     */
    fun getTable(tableName: String): TableModel?

    /**
     * 获取所有列表
     *
     * @return
     */
    fun getAllTables(): List<TableModel>
}