<#assign columnFullComment = column.columnComment>
<#assign columnComment = (columnFullComment?split("[（ ,，(]", "r"))[0]>
<#assign propertyName = column.columnFieldName>
<#assign fieldName = column.columnFieldName?uncap_first>
<#assign fieldType = column.columnFieldType>