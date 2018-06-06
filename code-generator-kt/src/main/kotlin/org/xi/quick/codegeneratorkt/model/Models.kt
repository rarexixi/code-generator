package org.xi.quick.codegeneratorkt.model

import org.xi.quick.codegeneratorkt.extensions.getCamelCaseName


class SelectField {

    var tableName: String? = null
    var nameSet: Set<String> = HashSet()
    var options: Array<SelectOption>? = null
}

class FkSelectField {

    /**
     * 当前表外键名称
     */
    var nameSet: Set<String> = HashSet()
    /**
     * 关联表名
     */
    var foreignTable: String? = null
    /**
     * 关联表的关联key的字段名
     */
    var keyField: String? = null
        get() = field.getCamelCaseName()
    /**
     * 关联表的关联value的字段名
     */
    var valueField: String? = null
        get() = field.getCamelCaseName()

    val foreignClass: String?
        get() = foreignTable.getCamelCaseName()
}

class ValidStatusField {

    /**
     * 列名称
     */
    var fieldName: String? = null

    /**
     * 有效性值
     */
    var validValue: String? = null
    /**
     * 无效性值
     */
    var invalidValue: String? = null
}

class SelectOption {

    var value: String? = null
    var text: String? = null
}