package org.xi.quick.codegeneratorkt.configuration.properties

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.NestedConfigurationProperty
import org.springframework.stereotype.Component
import org.xi.quick.codegeneratorkt.extensions.getCamelCaseName
import org.xi.quick.codegeneratorkt.model.FieldProperties
import org.xi.quick.codegeneratorkt.model.FileProperties
import org.xi.quick.codegeneratorkt.model.PathProperties
import java.util.HashMap
import java.util.regex.Pattern

@Component
@ConfigurationProperties(prefix = "generator")
object GeneratorProperties {

    /**
     * 模版输出编码
     */
    var encoding: String = "UTF-8"

    /**
     * 获取目标表名正则
     */
    var tableNameMatchRegex: String? = null

    /**
     * 数据库名
     */
    lateinit var databaseName: String

    /**
     * 路径配置
     */
    @NestedConfigurationProperty
    var paths: PathProperties? = null

    /**
     * 文件配置
     */
    @NestedConfigurationProperty
    var files: FileProperties? = null

    /**
     * 字段配置
     */
    @NestedConfigurationProperty
    var fields: FieldProperties? = null

    /**
     * 其他公共属性，用于生成到页面和路径
     */
    var commonProperties: Map<String, String> = HashMap()
    /**
     * 数据对应关系
     */
    var dataTypeMap: Map<String, String> = HashMap()

}
