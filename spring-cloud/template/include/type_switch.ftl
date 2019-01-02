<#if column.ignoreSearch>
<#elseif column.columnName == table.validStatusField.fieldName>
<#elseif (column.targetDataType == "Integer" || column.targetDataType == "Long" || column.targetDataType == "Short" || column.targetDataType == "Byte")>
<#elseif (column.targetDataType == "Date")>
<#elseif (column.targetDataType == "BigDecimal" || column.targetDataType == "Double" || column.targetDataType == "Float")>
<#elseif (column.targetDataType == "String")>
<#else>
</#if>
