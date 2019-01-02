<#assign columnFullComment = column.columnComment>
<#assign columnComment = (columnFullComment?split("[（ ,，(]", "r"))[0]>
<#assign propertyName = column.targetColumnName>
<#assign fieldName = column.targetColumnName?uncap_first>
<#assign fieldType = column.targetDataType>
