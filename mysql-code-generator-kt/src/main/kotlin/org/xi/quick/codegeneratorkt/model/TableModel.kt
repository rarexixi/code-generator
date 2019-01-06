package org.xi.quick.codegeneratorkt.model

import org.xi.quick.codegeneratorkt.configuration.properties.GeneratorProperties
import org.xi.quick.codegeneratorkt.entity.Table
import org.xi.quick.codegeneratorkt.extensions.getClassName
import org.xi.quick.codegeneratorkt.extensions.getTargetTableName
import org.xi.quick.codegeneratorkt.extensions.isBaseColumn

class TableModel(table: Table,
                 val columns: List<ColumnModel>,
                 val statistics: List<IndexModel>) {

    // region 默认

    // 数据库名
    var databaseName: String
        private set
    // 表名
    var tableName: String
        private set
    // 获取目标表名
    var targetTableName: String
        private set
    // 表对应的类名
    var className: String
        private set
    // 表说明
    var comment: String
        private set

    // endregion

    // region 扩展

    // 是否有主键
    var hasPk: Boolean = false
        private set
    // 主键列表
    var pks: List<ColumnModel> = listOf()
        private set
    // 是否有唯一主键
    var hasUniPk: Boolean = false
        private set
    // 唯一主键
    var uniPk: ColumnModel? = null
        private set
    // 唯一主键是否自增
    var hasAutoIncUniPk: Boolean = false
        private set

    // 索引
    var indexes: List<ColumnModel> = listOf()
        private set

    // 除了公共列以外的列
    var columnsExceptBase: List<ColumnModel> = listOf()
        private set

    // 选择项
    var selectColumns: List<ColumnModel> = listOf()
        private set

    // 外键选择项
    var fkSelectColumns: List<ColumnModel> = listOf()
        private set

    // 有效性字段列
    var validStatusColumn: ColumnModel? = null
        private set

    init {

        databaseName = table.tableSchema ?: ""
        tableName = table.tableName ?: ""
        targetTableName = tableName.getTargetTableName()
        className = tableName.getClassName()
        comment = table.tableComment ?: className

        pks = columns.filter { column -> column.columnKey == "PRI" }
        indexes = columns.filter { column -> column.index }
        columnsExceptBase = columns.filter { column -> !column.columnName.isBaseColumn() }
        selectColumns = columns.filter { column -> column.select }
        fkSelectColumns = columns.filter { column -> column.fkSelect }
        validStatusColumn = columns.firstOrNull { column -> column.validStatus }

        hasPk = pks.isNotEmpty()
        if (hasPk) {
            hasUniPk = pks.size == 1
            if (hasUniPk) {
                uniPk = pks[0]
                hasAutoIncUniPk = uniPk!!.autoIncrement
            }
        }
    }

    // endregion
}