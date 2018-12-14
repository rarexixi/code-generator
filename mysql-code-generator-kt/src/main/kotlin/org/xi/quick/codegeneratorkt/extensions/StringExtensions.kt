package org.xi.quick.codegeneratorkt.extensions

import org.xi.quick.codegeneratorkt.StaticConfigData
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
fun String?.getCamelCaseName(): String? {

    return this.getCamelCaseNameBy("_")
}

/**
 * 获取驼峰命名
 *
 * @param delimiter
 * @return
 */
fun String?.getCamelCaseNameBy(delimiter: String): String? {

    if (this.isNullOrBlank()) return this

    return this!!.replace("$delimiter+[a-z]".toRegex()) {
        it.value[1].toUpperCase() + ""
    }
}

/**
 * 获取目标表名
 */
fun String?.getTargetTableName(): String? {

    if (this.isNullOrBlank()) return this

    val pattern = Pattern.compile(StaticConfigData.TABLE_NAME_MATCH_REGEX)
    val matcher = pattern.matcher(this)
    if (matcher.find()) {
        return matcher.group()
    }
    return this
}