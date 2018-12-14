package org.xi.quick.codegeneratorkt

import org.xi.quick.codegeneratorkt.model.FkSelectField
import org.xi.quick.codegeneratorkt.model.SelectField
import org.xi.quick.codegeneratorkt.model.ValidStatusField
import java.util.HashMap
import java.util.HashSet


object StaticConfigData {

    var DATABASE_NAME: String? = null

    //region 字段相关

    var NOT_REQUIRED_FIELD_SET: Set<String> = HashSet()       //获取不需要的字段集合
    var IMG_URL_FIELD_SET: Set<String> = HashSet()            //获取图片路径的字段集合
    var VIDEO_URL_FIELD_SET: Set<String> = HashSet()          //获取视频路径的字段集合
    var DOC_URL_FIELD_SET: Set<String> = HashSet()            //获取文档路径的字段集合
    var PAGE_URL_FIELD_SET: Set<String> = HashSet()           //获取页面路径的字段集合
    var OTHER_URL_FIELD_SET: Set<String> = HashSet()          //获取其他路径的字段集合
    var ALL_URL_FIELD_SET: Set<String> = HashSet()            //获取所有路径的字段集合
    var CONTENT_FIELD_SET: Set<String> = HashSet()            //获取内容的字段集合
    var IGNORE_SEARCH_FIELD_SET: Set<String> = HashSet()      //获取忽略查询的字段集合

    var SELECT_FIELD_ARRAY: Array<SelectField> = arrayOf()        //获取是选择项的字段集合

    //endregion

    var VALID_STATUS_FIELD: ValidStatusField? = null
    var FK_SELECT_FIELDS: Array<FkSelectField> = arrayOf()

    var CODE_ENCODING: String = "UTF-8"
    var TABLE_NAME_MATCH_REGEX: String? = null

    var COMMON_PROPERTIES: MutableMap<String, Any> = HashMap()
}

object DataTypeMapping {

    var DATA_TYPE_MAP: Map<String, String> = HashMap()

    fun getFieldType(dataType: String?): String? {
        if(dataType.isNullOrBlank()) return null
        return DATA_TYPE_MAP[dataType]
    }
}