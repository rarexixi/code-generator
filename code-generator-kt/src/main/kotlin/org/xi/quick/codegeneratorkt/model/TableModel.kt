package org.xi.quick.codegeneratorkt.model

import org.xi.quick.codegeneratorkt.entity.Table
import org.xi.quick.codegeneratorkt.StaticConfigData
import org.xi.quick.codegeneratorkt.extensions.getCamelCaseName
import java.util.ArrayList

class TableModel(table: Table,
                 val columns: List<ColumnModel>,
                 val statistics: List<StatisticsModel>) {

    // region 默认

    val databaseName: String? = table.tableSchema       // 数据库名

    val tableName: String? = table.tableName            // 表名

    val tableClassName: String? = table.tableName.getCamelCaseName()    // 表对应的JAVA类名

    val tableComment: String? = if (table.tableComment.isNullOrBlank()) tableClassName else table.tableComment  // 表说明

    val validStatusField: ValidStatusField?
        get() = StaticConfigData.VALID_STATUS_FIELD

    // endregion

    // region 扩展

    private var primaryKey: MutableList<ColumnModel> = ArrayList()
    // 是否有主键
    var isHasPrimaryKey: Boolean = false
        private set
    // 是否有唯一自增主键
    var isHasAutoIncrementUniquePrimaryKey: Boolean = false
        private set
    // 获取唯一主键
    var uniquePrimaryKey: ColumnModel? = null
        private set
    // 主键对应的JAVA参数，单个(Integer id)，多个(Integer userId, Integer userTypeId)
    var primaryKeyParameters: String? = null
        private set
    // 主键对应的JAVA参数值，单个(id) ，多个(userId, userTypeId)
    var primaryKeyParameterValues: String? = null
        private set
    var primaryKeyOldParameters: String? = null
        private set
    var primaryKeyOldParameterValues: String? = null
        private set
    // 有效性字段列
    var validStatusColumn: ColumnModel? = null
        private set

    init {

        for (column in columns) {
            if (column.columnKey == "PRI") {
                primaryKey.add(column)
            }
        }

        isHasPrimaryKey = primaryKey != null && !primaryKey.isEmpty()
        if (isHasPrimaryKey) {
            uniquePrimaryKey = if (primaryKey.size == 1) primaryKey[0] else null
            isHasAutoIncrementUniquePrimaryKey = uniquePrimaryKey != null && uniquePrimaryKey!!.autoIncrement
            primaryKeyParameters = primaryKey.joinToString(", ") { column ->
                column.columnFieldType + " " + column.columnFieldNameFirstLower
            }
            primaryKeyParameterValues = primaryKey.joinToString(", ") { column ->
                column.columnFieldNameFirstLower + ""
            }
            primaryKeyOldParameters = primaryKey.joinToString(", ") { column ->
                column.columnFieldType + " old" + column.columnFieldName
            }
            primaryKeyOldParameterValues = primaryKey.joinToString(", ") { column ->
                "old" + column.columnFieldName
            }
        }
        val columnOptional = columns
                .stream()
                .filter { column -> column.validStatus }
                .findFirst()

        validStatusColumn = if (columnOptional.isPresent) columnOptional.get() else null

    }

    // 获取主键列表
    fun getPrimaryKey(): List<ColumnModel>? {
        return primaryKey
    }

    // endregion
}