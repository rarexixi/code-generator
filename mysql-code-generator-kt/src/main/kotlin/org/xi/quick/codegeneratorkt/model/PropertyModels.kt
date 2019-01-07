package org.xi.quick.codegeneratorkt.model

import org.xi.quick.codegeneratorkt.extensions.getClassName
import org.xi.quick.codegeneratorkt.extensions.getPropertyName
import org.xi.quick.codegeneratorkt.extensions.getTargetTableName

class PathProperties {

    /**
     * 模版路径
     */
    lateinit var template: String

    /**
     * 输出路径
     */
    lateinit var out: String
}

class FileProperties {

    /**
     * 忽略的文件集合
     */
    var ignore: Set<String>? = HashSet()

    /**
     * 聚合文件集合
     */
    var aggregate: Set<String>? = HashSet()

    /**
     * 仅复制文件集合
     */
    var copy: Set<String>? = HashSet()
}

class ColumnProperties {

    /**
     * 基本列（所有表都存在的公共列，必须选择表名）
     */
    var base: ColumnProperty? = null

    /**
     * 不需要填写的字段集合
     */
    var notRequired: Array<ColumnProperty> = arrayOf()

    /**
     * 图片路径的字段集合
     */
    var imgUrl: Array<ColumnProperty> = arrayOf()

    /**
     * 视频路径的字段集合
     */
    var videoUrl: Array<ColumnProperty> = arrayOf()

    /**
     * 文档路径字段集合
     */
    var docUrl: Array<ColumnProperty> = arrayOf()

    /**
     * 页面路径的字段集合
     */
    var pageUrl: Array<ColumnProperty> = arrayOf()

    /**
     * 其他路径的字段集合
     */
    var otherUrl: Array<ColumnProperty> = arrayOf()

    /**
     * 内容类型字段集合
     */
    var content: Array<ColumnProperty> = arrayOf()


    /**
     * 选择项的字段集合
     */
    var select: Array<SelectColumnProperty> = arrayOf()

    /**
     * 外键选择字段
     */
    var fkSelect: Array<FkSelectColumnProperty> = arrayOf()

    /**
     * 有效性字段
     */
    var validStatus: Array<ValidStatusColumnProperty> = arrayOf()
}

open class ColumnProperty {

    /**
     * 字段所属表名
     */
    var tableName: String = ""
    /**
     * 字段集
     */
    var columnNameSet: Set<String> = HashSet()

    /**
     * 目标表名
     */
    val targetTableName: String
        get() = tableName.getTargetTableName()
    /**
     * 目标类名
     */
    val className: String
        get() = tableName.getClassName()
}

class SelectColumnProperty : ColumnProperty() {

    /**
     * 选项列表
     */
    var options: Array<SelectOption> = arrayOf()
}

class FkSelectColumnProperty : ColumnProperty() {

    /**
     * 外键
     */
    var fk: FkSelectColumn? = null
}

class ValidStatusColumnProperty : ColumnProperty() {

    /**
     * 有效状态
     */
    var status: ValidStatus? = null
}

class FkSelectColumn {

    /**
     * 外键表名
     */
    var foreignTableName: String = ""
    var valueColumnName: String = ""
    var textColumnName: String = ""

    val valueName: String
        get() = valueColumnName.getPropertyName()
    val textName: String
        get() = textColumnName.getPropertyName()

    /**
     * 外键目标表名
     */
    val foreignTargetTableName: String
        get() = foreignTableName.getTargetTableName()
    /**
     * 外键类名
     */
    val foreignClassName: String
        get() = foreignTableName.getClassName()
}

class SelectOption {

    var value: String = ""
    var text: String = ""
}

class ValidStatus {

    /**
     * 有效性值
     */
    var valid: String = ""
    /**
     * 无效性值
     */
    var invalid: String = ""
}