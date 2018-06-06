package org.xi.quick.codegeneratorkt.properties

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component
import org.xi.quick.codegeneratorkt.model.SelectField
import java.util.HashSet

@Component
@ConfigurationProperties(prefix = "generator.field")
class FieldProperties {

    /**
     * 不需要填写的字段集合
     */
    var notRequired: Set<String> = HashSet()

    /**
     * 图片路径的字段集合
     */
    var imgUrl: Set<String> = HashSet()

    /**
     * 视频路径的字段集合
     */
    var videoUrl: Set<String> = HashSet()

    /**
     * 文档路径字段集合
     */
    var docUrl: Set<String> = HashSet()

    /**
     * 页面路径的字段集合
     */
    var pageUrl: Set<String> = HashSet()

    /**
     * 其他路径的字段集合
     */
    var otherUrl: Set<String> = HashSet()

    /**
     * 内容类型字段集合
     */
    var content: Set<String> = HashSet()

    /**
     * 选择项的字段集合
     */
    var select: Array<SelectField> = arrayOf()
}