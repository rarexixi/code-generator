package org.xi.quick.codegeneratorkt.service

import org.xi.quick.codegeneratorkt.model.ColumnModel
import org.xi.quick.codegeneratorkt.model.TableModel

interface TableService {

    /**
     * 获取所有表都存在的公共列
     *
     * @return
     */
    fun getBaseColumns(): List<ColumnModel>

    /**
     * 获取所有表名
     *
     * @return
     */
    fun getAllTableNameList(): Set<String>

    /**
     * 获取所有列表
     *
     * @return
     */
    fun getTables(vararg tableNames: String): List<TableModel>
}