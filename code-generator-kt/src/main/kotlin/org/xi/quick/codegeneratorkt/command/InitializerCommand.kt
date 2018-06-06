package org.xi.quick.codegeneratorkt.command

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.CommandLineRunner
import org.springframework.core.annotation.Order
import org.springframework.stereotype.Component
import org.xi.quick.codegeneratorkt.DataTypeMapping
import org.xi.quick.codegeneratorkt.StaticConfigData
import org.xi.quick.codegeneratorkt.model.SelectField
import org.xi.quick.codegeneratorkt.properties.FieldProperties
import org.xi.quick.codegeneratorkt.properties.GeneratorProperties
import java.util.*
import java.util.regex.Pattern


@Component
@Order(1)
class InitializerCommand : CommandLineRunner {

    @Value("\${spring.datasource.url}")
    private lateinit var dbUrl: String
    @Value("\${spring.datasource.username}")
    private lateinit var dbUsername: String
    @Value("\${spring.datasource.password}")
    private lateinit var dbPassword: String

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

        StaticConfigData.DATABASE_NAME = getDatabaseNameFromJdbcUrl(dbUrl)

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
        StaticConfigData.COMMON_PROPERTIES["dbUrl"] = dbUrl
        StaticConfigData.COMMON_PROPERTIES["dbUsername"] = dbUsername
        StaticConfigData.COMMON_PROPERTIES["dbPassword"] = dbPassword

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

        val pattern = Pattern.compile("/[^(/|\\?)]*\\?")
        val matcher = pattern.matcher(url!!)
        if (matcher.find()) {
            val group = matcher.group()
            return group.substring(1, group.length - 1)
        }

        return ""
    }

    fun getSelectFieldNameSet(select: Array<SelectField>): Set<String> {
        val nameSet = HashSet<String>()
        for (selectField in select) {
            nameSet.addAll(selectField.nameSet)
        }
        return nameSet
    }
}