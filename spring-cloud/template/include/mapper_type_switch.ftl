<#if column.ignoreSearch>
<#elseif column.columnName == table.validStatusField.fieldName>
<#elseif (column.dataType == "varchar" || column.targetDataType == "char" || column.targetDataType == "Short" || column.targetDataType == "Byte")>
<#elseif (column.dataType == "Date")>
<#elseif (column.dataType == "BigDecimal" || column.targetDataType == "Double" || column.targetDataType == "Float")>
<#elseif (column.dataType == "String")>
<#else>
</#if>
