<#assign columnFullComment = column.columnComment>
<#assign columnComment = (columnFullComment?split("[（ ,，(：:]", "r"))[0]>
<#assign columnPath = column.columnName?replace("_", "-")>
<#assign propertyName = column.targetName>
<#assign fieldName = propertyName?uncap_first>
<#assign fieldType = column.targetDataType>
<#assign columnExceptKey = column.columnName?replace('_id', '')?replace('_key', '')?replace('_code', '')>
<#assign propertyExceptKey = propertyName?replace('Id', '')?replace('Key', '')?replace('Code', '')>
<#assign fieldNameExceptKey = propertyExceptKey?uncap_first>
<#-- -->
<#assign canBeEqual = (column.validStatus || column.dataType?contains("int") || column.dataType?ends_with("char"))>
<#assign canBeList = (!column.validStatus && (column.dataType?contains("int") || column.dataType?ends_with("char")))>
<#assign canBeRange = (!column.validStatus && (column.dataType?contains("int") || column.dataType == "double" || column.dataType == "float" || column.dataType == "decimal" || column.dataType == "numeric" || column.dataType?contains("date") || column.dataType?contains("time")))>
<#assign canBeNull = column.nullable>
<#-- -->
<#assign isContent = (column.content || column.dataType?contains("text"))>
<#assign isInteger = (column.dataType?contains("int"))>
<#assign isDecimal = (column.dataType == "double" || column.dataType == "float" || column.dataType == "decimal" || column.dataType == "numeric")>
<#assign isString = (column.dataType?ends_with("char"))>
<#assign isDate = (column.dataType == "date")>
<#assign isTime = (column.dataType == "time")>
<#assign isDateTime = (column.dataType?contains("date") || column.dataType?contains("time"))>
<#-- -->
<#assign fieldBasicType = fieldType>
<#if (fieldType=="Boolean")>
    <#assign fieldBasicType = "boolean">
<#elseif (fieldType=="Character")>
    <#assign fieldBasicType = "char">
<#elseif (fieldType=="Byte")>
    <#assign fieldBasicType = "byte">
<#elseif (fieldType=="Short")>
    <#assign fieldBasicType = "short">
<#elseif (fieldType=="Integer")>
    <#assign fieldBasicType = "int">
<#elseif (fieldType=="Long")>
    <#assign fieldBasicType = "long">
<#elseif (fieldType=="Float")>
    <#assign fieldBasicType = "float">
<#elseif (fieldType=="Double")>
    <#assign fieldBasicType = "double">
</#if>