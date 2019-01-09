<#assign columnFullComment = column.columnComment>
<#assign columnComment = (columnFullComment?split("[（ ,，(]", "r"))[0]>
<#assign propertyName = column.targetName>
<#assign fieldName = propertyName?uncap_first>
<#assign fieldType = column.targetDataType>
<#assign propertyExceptKey = propertyName?replace('Id', '')?replace('Key', '')?replace('Code', '')>
<#assign fieldNameExceptKey = propertyExceptKey?uncap_first>
<#-- -->
<#assign canBeEqual = (column.validStatus || column.dataType?contains("int") || column.dataType == "date" || column.dataType?ends_with("char"))>
<#assign canBeList = (!column.validStatus && (column.dataType?contains("int") || column.dataType?contains("date") || column.dataType?ends_with("char")))>
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