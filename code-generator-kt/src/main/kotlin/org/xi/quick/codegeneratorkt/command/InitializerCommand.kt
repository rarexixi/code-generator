package org.xi.quick.codegeneratorkt.command

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties
import org.springframework.core.annotation.Order
import org.springframework.stereotype.Component
import org.xi.quick.codegeneratorkt.DataTypeMapping
import org.xi.quick.codegeneratorkt.StaticConfigData
import org.xi.quick.codegeneratorkt.properties.FieldProperties
import org.xi.quick.codegeneratorkt.properties.GeneratorProperties
import java.util.*

@Component
@Order(1)
class InitializerCommand : CommandLineRunner {

    @Autowired
    lateinit var dataSourceProperties: DataSourceProperties

    @Autowired
    lateinit var generator: GeneratorProperties

    @Autowired
    lateinit var generatorField: FieldProperties

    /**
     * 获取所有路径的字段集合
     *
     * @return
     */
    val allUrlFieldSet: Set<String>
        get() {
            val urlSet = HashSet<String>()
            urlSet.addAll(generatorField.imgUrl)
            urlSet.addAll(generatorField.videoUrl)
            urlSet.addAll(generatorField.docUrl)
            urlSet.addAll(generatorField.pageUrl)
            urlSet.addAll(generatorField.otherUrl)
            return urlSet
        }

    /**
     * 获取忽略查询的字段集合
     *
     * @return
     */
    val ignoreSearchFieldSet: Set<String>
        get() {
            val fieldSet = HashSet<String>()
            fieldSet.addAll(allUrlFieldSet)
            fieldSet.addAll(generatorField.content)
            return fieldSet
        }

    @Throws(Exception::class)
    override fun run(vararg strings: String) {

        StaticConfigData.DATABASE_NAME = getDatabaseNameFromJdbcUrl(dataSourceProperties.url)

        StaticConfigData.VALID_STATUS_FIELD = generator.validStatusField
        StaticConfigData.FK_SELECT_FIELDS = generator.fkSelectFields

        StaticConfigData.NOT_REQUIRED_FIELD_SET = generatorField.notRequired
        StaticConfigData.IMG_URL_FIELD_SET = generatorField.imgUrl
        StaticConfigData.VIDEO_URL_FIELD_SET = generatorField.videoUrl
        StaticConfigData.DOC_URL_FIELD_SET = generatorField.docUrl
        StaticConfigData.PAGE_URL_FIELD_SET = generatorField.pageUrl
        StaticConfigData.OTHER_URL_FIELD_SET = generatorField.otherUrl
        StaticConfigData.ALL_URL_FIELD_SET = allUrlFieldSet
        StaticConfigData.CONTENT_FIELD_SET = generatorField.content
        StaticConfigData.IGNORE_SEARCH_FIELD_SET = ignoreSearchFieldSet
        StaticConfigData.SELECT_FIELD_ARRAY = generatorField.select

        StaticConfigData.CODE_ENCODING = generator.encoding

        StaticConfigData.COMMON_PROPERTIES.putAll(generator.commonProperties)
        StaticConfigData.COMMON_PROPERTIES["now"] = Date()
        StaticConfigData.COMMON_PROPERTIES["dbUrl"] = dataSourceProperties.url
        StaticConfigData.COMMON_PROPERTIES["dbUsername"] = dataSourceProperties.username
        StaticConfigData.COMMON_PROPERTIES["dbPassword"] = dataSourceProperties.password

        if (generator.validStatusField != null)
            StaticConfigData.COMMON_PROPERTIES["validStatusField"] = generator.validStatusField!!

        DataTypeMapping.DATA_TYPE_MAP = generator.dataTypeMap
    }

    /**
     * 从数据库连接串获取数据库名
     *
     * @param url
     * @return
     */
    private fun getDatabaseNameFromJdbcUrl(url: String?): String {
        if (url == null) return ""
        var matchResult = Regex("""(?<=/)[^(/|\?)]*(?=\?)""").find(url)
        return if (matchResult == null) "" else matchResult.value
    }
}