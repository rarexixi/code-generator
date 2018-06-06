package org.xi.quick.codegeneratorkt.model

import org.xi.quick.codegeneratorkt.entity.Column
import org.xi.quick.codegeneratorkt.DataTypeMapping
import org.xi.quick.codegeneratorkt.StaticConfigData
import org.xi.quick.codegeneratorkt.extensions.getCamelCaseName
import org.xi.quick.codegeneratorkt.extensions.getFirstLower
import org.xi.quick.codegeneratorkt.model.FkSelectField
import org.xi.quick.codegeneratorkt.model.SelectOption

class ColumnModel(column: Column) {

    //region 默认

    /**
     * 数据库名
     */
    var databaseName: String? = column.tableSchema
    /**
     * 表名
     */
    var tableName: String? = column.tableName
    /**
     * 列名
     */
    var columnName: String? = column.columnName
    /**
     * 列位置
     */
    var columnPosition: Long? = column.ordinalPosition
    /**
     * 默认值
     */
    var columnDefault: String? = column.columnDefault
    /**
     * 是否为空
     */
    var nullable: Boolean = column.isNullable == null || column.isNullable == "NO"
    /**
     * 数据类型，int，varchar
     */
    var dataType: String? = column.dataType
    /**
     * 字符长度
     */
    var charLength: Long? = column.characterMaximumLength
    /**
     * 字节长度
     */
    var byteLength: Long? = column.characterOctetLength
    /**
     * 列类型，int(11)，varchar(50)
     */
    var columnType: String? = column.columnType
    /**
     *
     */
    var columnKey: String? = column.columnKey
    /**
     *
     */
    var extra: String? = column.extra
    /**
     * 列说明
     */
    var columnComment: String? = if (column.columnComment.isNullOrBlank()) column.columnName.getCamelCaseName() else column.columnComment
    /**
     * 是否是索引
     */
    var index: Boolean = if (column.index == null) false else column.index!!

    //endregion

    //region 扩展

    /**
     * 获取列对应的JAVA字段名
     *
     * @return
     */
    var columnFieldName: String? = null
        private set
    /**
     * 获取列对应的JAVA字段名首字母小写
     *
     * @return
     */
    var columnFieldNameFirstLower: String? = null
        private set
    /**
     * 获取列对应的JAVA字段类型
     *
     * @return
     */
    var columnFieldType: String? = null
        private set

    var fkSelectField: FkSelectField? = null
        private set
    var fkSelect: Boolean = false
        private set

    /**
     * 是自增长
     *
     * @return
     */
    var autoIncrement: Boolean = false
        private set
    /**
     * 是主键
     *
     * @return
     */
    var primaryKey: Boolean = false
        private set
    /**
     * 有效性字段
     *
     * @return
     */
    var validStatus: Boolean = false
        private set

    /**
     * 是选择项
     *
     * @return
     */
    var select: Boolean = false
        private set
    /**
     * 选择项列表
     *
     * @return
     */
    var selectOptions: Array<SelectOption>? = null
        private set
    /**
     * 不需要填写
     *
     * @return
     */
    var notRequired: Boolean = false
        private set

    /**
     * 是图片路径
     *
     * @return
     */
    var imgUrl: Boolean = false
        private set
    /**
     * 是视频路径
     *
     * @return
     */
    var videoUrl: Boolean = false
        private set
    /**
     * 是文档路径
     *
     * @return
     */
    var docUrl: Boolean = false
        private set
    /**
     * 是页面路径
     *
     * @return
     */
    var pageUrl: Boolean = false
        private set
    /**
     * 是其他路径
     *
     * @return
     */
    var otherUrl: Boolean = false
        private set
    /**
     * 是路径
     *
     * @return
     */
    var url: Boolean = false
        private set
    /**
     * 是内容
     *
     * @return
     */
    var content: Boolean = false
        private set
    /**
     * 忽略查询
     *
     * @return
     */
    var ignoreSearch: Boolean = false
        private set

    init {
        columnFieldName = columnName.getCamelCaseName()
        columnFieldNameFirstLower = columnFieldName?.getFirstLower()
        columnFieldType = DataTypeMapping.getFieldType(this.dataType)
        autoIncrement = extra!!.toLowerCase() == "auto_increment"
        primaryKey = columnKey == "PRI"
        validStatus = this.columnName.equals(StaticConfigData.VALID_STATUS_FIELD?.fieldName)

        // 判断是否是逻辑外键选择项字段
        fkSelect = false
        for (field in StaticConfigData.FK_SELECT_FIELDS!!) {
            if (field.nameSet != null && field.nameSet.contains(columnName)) {
                fkSelectField = field
                fkSelect = true
                break
            }
        }

        // 判断是否是选择字段
        select = false
        for (selectField in StaticConfigData.SELECT_FIELD_ARRAY!!) {
            if (selectField.nameSet.contains(columnName) && (selectField.tableName.isNullOrBlank() || selectField.tableName == tableName)) {
                select = true
                selectOptions = selectField.options
            }
        }

        notRequired = StaticConfigData.NOT_REQUIRED_FIELD_SET.contains(columnName)

        imgUrl = StaticConfigData.IMG_URL_FIELD_SET.contains(columnName)
        videoUrl = StaticConfigData.VIDEO_URL_FIELD_SET.contains(columnName)
        docUrl = StaticConfigData.DOC_URL_FIELD_SET.contains(columnName)
        pageUrl = StaticConfigData.PAGE_URL_FIELD_SET.contains(columnName)
        otherUrl = StaticConfigData.OTHER_URL_FIELD_SET.contains(columnName)
        url = StaticConfigData.ALL_URL_FIELD_SET.contains(columnName)
        content = StaticConfigData.CONTENT_FIELD_SET.contains(columnName)
        ignoreSearch = StaticConfigData.IGNORE_SEARCH_FIELD_SET.contains(columnName)
    }

    //endregion
}
