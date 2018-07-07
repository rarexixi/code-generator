package org.xi.quick.codegeneratorkt.service.impl

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.xi.quick.codegeneratorkt.StaticConfigData
import org.xi.quick.codegeneratorkt.entity.Column
import org.xi.quick.codegeneratorkt.mapper.ColumnsMapper
import org.xi.quick.codegeneratorkt.mapper.StatisticsMapper
import org.xi.quick.codegeneratorkt.mapper.TablesMapper
import org.xi.quick.codegeneratorkt.model.ColumnModel
import org.xi.quick.codegeneratorkt.model.StatisticsModel
import org.xi.quick.codegeneratorkt.model.TableModel
import org.xi.quick.codegeneratorkt.service.TableService

@Service("tableService")
class TableServiceImpl : TableService {

    @Autowired
    lateinit var tablesMapper: TablesMapper

    @Autowired
    lateinit var columnsMapper: ColumnsMapper

    @Autowired
    lateinit var statisticsMapper: StatisticsMapper

    /**
     * 获取所有表名
     *
     * @return
     */
    override fun getAllTableNameList(): Set<String> {
        return tablesMapper.getAllTableNameList(StaticConfigData.DATABASE_NAME!!)
    }

    /**
     * 获取单表
     *
     * @param tableName
     * @return
     */
    override fun getTable(tableName: String): TableModel {

        val table = tablesMapper.getTable(StaticConfigData.DATABASE_NAME!!, tableName)
        val columnList = columnsMapper.getColumnsWithIndex(StaticConfigData.DATABASE_NAME!!, table.tableName!!)
        val statisticsList = statisticsMapper.getStatistics(StaticConfigData.DATABASE_NAME!!, table.tableName!!)

        val columnModels = columnList.map { ColumnModel(it) }.toList()
        val statisticModels = statisticsList.map { StatisticsModel(it) }.toList()

        return TableModel(table, columnModels, statisticModels)
    }

    /**
     * 获取所有列表
     *
     * @return
     */
    override fun getAllTables(): List<TableModel> {

        val tables = tablesMapper.getAllTables(StaticConfigData.DATABASE_NAME!!)
        val tableModels = tables.map { table ->
            let {
                val columnList = columnsMapper.getColumnsWithIndex(StaticConfigData.DATABASE_NAME!!, table.tableName!!)
                val statisticsList = statisticsMapper.getStatistics(StaticConfigData.DATABASE_NAME!!, table.tableName!!)

                val columnModels = columnList.map { ColumnModel(it) }.toList()
                val statisticModels = statisticsList.map { StatisticsModel(it) }.toList()

                TableModel(table, columnModels, statisticModels)
            }
        }.toList()

        return tableModels
    }

}
