package org.xi.quick.codegeneratorkt.model

import org.xi.quick.codegeneratorkt.configuration.properties.GeneratorProperties
import org.xi.quick.codegeneratorkt.entity.Column
import org.xi.quick.codegeneratorkt.extensions.getCamelCaseName
import org.xi.quick.codegeneratorkt.extensions.getTargetDataType

class ColumnModel(column: Column) {

    //region 默认

    // 数据库名
    var databaseName: String
        private set
    // 表名
    var tableName: String
        private set
    // 列名
    var columnName: String
        private set
    // 列位置
    var columnPosition: Long
        private set
    // 默认值
    var columnDefault: String
        private set
    // 是否为空
    var nullable: Boolean
        private set
    // 数据类型，int，varchar
    var dataType: String
        private set
    // 字符长度
    var charLength: Long
        private set
    // 字节长度
    var byteLength: Long
        private set
    // 列类型，int(11)，varchar(50)
    var columnType: String
        private set
    //
    var columnKey: String
        private set
    //
    var extra: String
        private set
    // 列说明
    var columnComment: String
        private set
    // 是否是索引
    var index: Boolean
        private set

    //endregion

    //region 扩展

    // 获取列对应的JAVA字段名
    var targetColumnName: String
        private set
    // 获取列对应的JAVA字段类型
    var targetDataType: String
        private set

    // 是自增长
    var autoIncrement: Boolean = false
        private set
    // 是主键
    var primaryKey: Boolean = false
        private set

    // 是选择项列
    var select: Boolean = false
        private set
    // 选择项列表
    var selectOptions: Array<SelectOption>? = null
        private set

    // 是外键选择列
    var fkSelect: Boolean = false
        private set
    // 外键选择列
    var fkSelectColumn: FkSelectColumn? = null
        private set

    // 是有效性列
    var validStatus: Boolean = false
        private set
    // 有效性列
    var validStatusOption: ValidStatus? = null
        private set

    // 不需要填写
    var notRequired: Boolean = false
        private set

    // 是图片路径
    var imgUrl: Boolean = false
        private set
    // 是视频路径
    var videoUrl: Boolean = false
        private set
    // 是文档路径
    var docUrl: Boolean = false
        private set
    // 是页面路径
    var pageUrl: Boolean = false
        private set
    // 是其他路径
    var otherUrl: Boolean = false
        private set
    // 是路径
    var url: Boolean = false
        private set
    // 是内容
    var content: Boolean = false
        private set
    // 忽略查询
    var ignoreSearch: Boolean = false
        private set

    //endregion

    private fun matchColumn(columnProperties: Array<ColumnProperty>): Boolean {

        for (column in columnProperties) {
            if (column.columnNameSet.contains(columnName) && (column.tableName.isNullOrBlank() || column.tableName == tableName)) {
                return true
            }
        }
        return false
    }

    init {
        //region 默认

        databaseName = column.tableSchema ?: ""
        tableName = column.tableName ?: ""
        columnName = column.columnName ?: ""
        columnPosition = column.ordinalPosition ?: -1
        columnDefault = column.columnDefault ?: ""
        nullable = column.isNullable == null || column.isNullable.equals("YES", true)
        dataType = column.dataType ?: ""
        charLength = column.characterMaximumLength ?: -1
        byteLength = column.characterOctetLength ?: -1
        columnType = column.columnType ?: ""
        columnKey = column.columnKey ?: ""
        extra = column.extra ?: ""
        columnComment =
                if (column.columnComment.isNullOrBlank()) columnName.getCamelCaseName()
                else (column.columnComment ?: "")
        index = column.index ?: false

        // endregion

        //region 扩展

        targetColumnName = columnName.getCamelCaseName()
        targetDataType = this.dataType.getTargetDataType()
        autoIncrement = extra.toLowerCase() == "auto_increment"
        primaryKey = columnKey == "PRI"


        var columnProperties = GeneratorProperties.columns
        if (columnProperties != null) {

            // 判断是否是有效性列
            for (property in columnProperties.validStatus) {
                if (property.columnNameSet.contains(columnName) && (property.tableName.isNullOrBlank() || property.tableName == tableName)) {
                    validStatusOption = property.status
                    validStatus = true
                    break
                }
            }

            // 判断是否是逻辑外键选择项
            for (property in columnProperties.fkSelect) {
                if (property.columnNameSet.contains(columnName) && (property.tableName.isNullOrBlank() || property.tableName == tableName)) {
                    fkSelectColumn = property.fk
                    fkSelect = true
                    break
                }
            }

            // 判断是否是选择字段
            for (property in columnProperties.select) {
                if (property.columnNameSet.contains(columnName) && (property.tableName.isNullOrBlank() || property.tableName == tableName)) {
                    selectOptions = property.options
                    select = true
                    break
                }
            }


            notRequired = matchColumn(columnProperties.notRequired)

            imgUrl = matchColumn(columnProperties.imgUrl)
            videoUrl = matchColumn(columnProperties.videoUrl)
            docUrl = matchColumn(columnProperties.docUrl)
            pageUrl = matchColumn(columnProperties.pageUrl)
            otherUrl = matchColumn(columnProperties.otherUrl)

            url = imgUrl || videoUrl || docUrl || pageUrl || otherUrl

            content = matchColumn(columnProperties.content)
            ignoreSearch = url || content
        }


        // endregion
    }
}
