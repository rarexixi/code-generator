package org.xi.quick.codegeneratorkt.properties

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component
import org.xi.quick.codegeneratorkt.model.FkSelectField
import org.xi.quick.codegeneratorkt.model.ValidStatusField
import java.util.HashMap


@Component
@ConfigurationProperties(prefix = "generator")
class GeneratorProperties {

    /**
     * 模版输出编码
     */
    var encoding: String = "UTF-8"

    /**
     * 有效性字段
     */
    var validStatusField: ValidStatusField? = null

    /**
     * 外键选择字段
     */
    var fkSelectFields: Array<FkSelectField> = arrayOf()

    /**
     * 其他公共属性，用于生成到页面
     */
    var commonProperties: Map<String, String> = HashMap()
    /**
     * 数据对应关系
     */
    var dataTypeMap: Map<String, String> = HashMap()
}
