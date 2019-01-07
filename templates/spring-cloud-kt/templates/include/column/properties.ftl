<#assign columnFullComment = column.columnComment>
<#assign columnComment = (columnFullComment?split("[（ ,，(]", "r"))[0]>
<#assign propertyName = column.targetName>
<#assign fieldName = propertyName?uncap_first>
<#assign fieldType = column.targetDataType>
<#assign canBeEqual = (column.validStatus || column.dataType?contains("int") || column.dataType == "date" || column.dataType?ends_with("char"))>
<#assign canBeList = (column.dataType?contains("int") || column.dataType?contains("date") || column.dataType?ends_with("char"))>
<#assign canBeRange = (column.dataType?contains("int") || column.dataType == "double" || column.dataType == "float" || column.dataType == "decimal" || column.dataType == "numeric" || column.dataType?contains("date") || column.dataType?contains("time"))>
<#assign canBeNull = column.nullable>
