package org.xi.quick.codegeneratorkt.model

import org.xi.quick.codegeneratorkt.entity.Table
import org.xi.quick.codegeneratorkt.extensions.getClassName
import org.xi.quick.codegeneratorkt.extensions.getTargetTableName
import java.util.ArrayList

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
    var tableClassName: String
        private set
    // 表说明
    var tableComment: String
        private set

    // endregion

    // region 扩展

    // 主键列表
    var primaryKeys: List<ColumnModel> = ArrayList()
        private set
    // 是否有主键
    var hasPrimaryKey: Boolean = false
        private set
    // 是否有唯一自增主键
    var hasAutoIncrementUniquePrimaryKey: Boolean = false
        private set
    // 唯一主键
    var uniquePrimaryKey: ColumnModel? = null
        private set

    // 索引
    var indexes: List<ColumnModel> = ArrayList()
        private set

    // 选择项
    var selectColumns: List<ColumnModel> = ArrayList()
        private set

    // 外键选择项
    var fkSelectColumns: List<ColumnModel> = ArrayList()
        private set

    // 有效性字段列
    var validStatusColumn: ColumnModel? = null
        private set

    init {

        databaseName = table.tableSchema ?: ""
        tableName = table.tableName ?: ""
        targetTableName  = tableName.getTargetTableName()
        tableClassName = tableName.getClassName()
        tableComment = table.tableComment ?: tableClassName

        primaryKeys = columns.filter { column -> column.columnKey == "PRI" }
        indexes = columns.filter { column -> column.index }
        selectColumns = columns.filter { column -> column.select }
        fkSelectColumns = columns.filter { column -> column.fkSelect }
        validStatusColumn = columns.firstOrNull { column -> column.validStatus }

        hasPrimaryKey = primaryKeys.isNotEmpty()
        if (hasPrimaryKey) {
            uniquePrimaryKey = if (primaryKeys.size == 1) primaryKeys[0] else null
            hasAutoIncrementUniquePrimaryKey = uniquePrimaryKey != null && uniquePrimaryKey!!.autoIncrement
        }
    }

    // endregion
}