package org.xi.quick.codegeneratorkt.service.impl

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.xi.quick.codegeneratorkt.configuration.properties.GeneratorProperties
import org.xi.quick.codegeneratorkt.mapper.ColumnsMapper
import org.xi.quick.codegeneratorkt.mapper.IndexMapper
import org.xi.quick.codegeneratorkt.mapper.TablesMapper
import org.xi.quick.codegeneratorkt.model.ColumnModel
import org.xi.quick.codegeneratorkt.model.IndexModel
import org.xi.quick.codegeneratorkt.model.TableModel
import org.xi.quick.codegeneratorkt.service.TableService

@Service("tableService")
class TableServiceImpl : TableService {

    @Autowired
    lateinit var tablesMapper: TablesMapper

    @Autowired
    lateinit var columnsMapper: ColumnsMapper

    @Autowired
    lateinit var indexesMapper: IndexMapper

    @Autowired
    lateinit var generator: GeneratorProperties

    /**
     * 获取所有表名
     *
     * @return
     */
    override fun getAllTableNameList(): Set<String> {
        return tablesMapper.getAllTableNameList(generator.databaseName)
    }

    /**
     * 获取表
     *
     * @return
     */
    override fun getTables(vararg tableNames: String): List<TableModel> {

        val tables = tablesMapper.getTables(generator.databaseName, *tableNames)

        val tableModels = tables.map { table ->
            let {
                val columnList = columnsMapper.getColumnsWithIndex(generator.databaseName, table.tableName!!)
                val statisticsList = indexesMapper.getIndexes(generator.databaseName, table.tableName!!)

                val columnModels = columnList.map { it -> ColumnModel(it) }.toList()
                val statisticModels = statisticsList.map { it -> IndexModel(it) }.toList()

                TableModel(table, columnModels, statisticModels)
            }
        }.toList()

        return tableModels
    }

}
