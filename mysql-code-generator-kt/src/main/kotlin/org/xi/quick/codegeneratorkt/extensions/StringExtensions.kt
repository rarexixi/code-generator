package org.xi.quick.codegeneratorkt.extensions

import org.xi.quick.codegeneratorkt.configuration.properties.GeneratorProperties
import java.util.regex.Pattern

/**
 * 获取首字母小写
 *
 * @return
 */
fun String.getFirstLower(): String {

    return this.substring(0, 1).toLowerCase() + this.substring(1)
}

/**
 * 获取首字母大写
 *
 * @return
 */
fun String.getFirstUpper(): String {

    return this.substring(0, 1).toUpperCase() + this.substring(1)
}

/**
 * 获取驼峰命名
 *
 * @return
 */
fun String.getCamelCaseName(): String {

    return this.getCamelCaseNameBy("_").getFirstUpper()
}

/**
 * 获取驼峰命名
 *
 * @param delimiter
 * @return
 */
fun String.getCamelCaseNameBy(delimiter: String): String {

    if (this.isBlank()) return this

    return this.replace("$delimiter+[a-z]".toRegex()) {
        it.value[1].toUpperCase() + ""
    }
}

// region 表/列相关

/**
 * 获取目标数据类型
 *
 * @param dataType
 * @return
 */
fun String.getTargetDataType(): String {
    var dataType = this
    if (dataType.isBlank()) return dataType
    var targetDataType = GeneratorProperties.dataTypeMap[dataType]
    return targetDataType ?: dataType
}

/**
 * 获取目标表名
 */
fun String.getTargetTableName(): String {
    val tableName = this
    if (tableName.isBlank()) return tableName

    val tablePropertyList = GeneratorProperties.tables.filter { it.tableName == tableName }
    if (tablePropertyList.isNotEmpty()) {
        return tablePropertyList[0].targetTableName
    }

    val pattern = Pattern.compile(GeneratorProperties.tableNameMatchRegex)
    val matcher = pattern.matcher(tableName)
    if (matcher.find()) {
        return matcher.group()
    }
    return tableName
}

/**
 * 获取目标类名
 */
fun String.getClassName(): String {
    val tableName = this
    return tableName.getTargetTableName().getCamelCaseName()
}

/**
 * 获取目标属性名
 */
fun String.getPropertyName(): String {
    val columnName = this
    return columnName.getCamelCaseName()
}

fun String.isBaseColumn(): Boolean {
    val columnName = this

    val baseTableName = GeneratorProperties.columns.base.tableName
    val baseColumns = GeneratorProperties.columns.base.columnNameSet

    if (baseTableName.isNullOrBlank() || baseColumns.isEmpty()) return false
    return baseColumns.contains(columnName)
}

// endregion