package org.xi.quick.codegeneratorkt.service.impl

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties
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
    lateinit var dataSourceProperties: DataSourceProperties
    
    fun getDatabaseName(): String {
        val regex = Regex("""(?<=//)[^?]*""")
        return regex.find(dataSourceProperties.url)!!.value.split("/")[1]
    }

    /**
     * 获取所有表都存在的公共列
     *
     * @return
     */
    override fun getBaseColumns(): List<ColumnModel> {

        val baseTableName = GeneratorProperties.columns.base.tableName
        val baseColumns = GeneratorProperties.columns.base.columnNameSet
        if (baseTableName.isBlank() || baseColumns.isEmpty()) return listOf()

        val columnList = columnsMapper.getColumnsWithIndex(getDatabaseName(), baseTableName)
        return columnList.filter { baseColumns.contains(it.columnName) }.map { ColumnModel(it) }
    }

    /**
     * 获取所有表名
     *
     * @return
     */
    override fun getAllTableNameList(): Set<String> {
        return tablesMapper.getAllTableNameList(getDatabaseName())
    }

    /**
     * 获取表
     *
     * @return
     */
    override fun getTables(vararg tableNames: String): List<TableModel> {

        val tables = tablesMapper.getTables(getDatabaseName(), *tableNames)

        return tables.map { table ->
            let {
                val columnList = columnsMapper.getColumnsWithIndex(getDatabaseName(), table.tableName!!)
                val statisticsList = indexesMapper.getIndexes(getDatabaseName(), table.tableName!!)

                val columnModels = columnList.map { it -> ColumnModel(it) }
                val statisticModels = statisticsList.map { it -> IndexModel(it) }

                TableModel(table, columnModels, statisticModels)
            }
        }
    }

}
