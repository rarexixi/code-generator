package org.xi.quick.codegeneratorkt.configuration.properties

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.NestedConfigurationProperty
import org.springframework.stereotype.Component
import org.xi.quick.codegeneratorkt.model.ColumnProperties
import org.xi.quick.codegeneratorkt.model.FileProperties
import org.xi.quick.codegeneratorkt.model.PathProperties
import org.xi.quick.codegeneratorkt.model.TableProperty
import java.util.HashMap

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
    var tableNameMatchRegex: String = ".*"

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
     * 表的配置
     */
    var tables: Array<TableProperty> = arrayOf()

    /**
     * 字段配置
     */
    @NestedConfigurationProperty
    var columns: ColumnProperties? = null

    /**
     * 其他公共属性，用于生成到页面和路径
     */
    var commonProperties: Map<String, String> = HashMap()
    /**
     * 数据对应关系
     */
    var dataTypeMap: Map<String, String> = HashMap()

}
