<#if column.ignoreSearch>
<#elseif column.columnName == table.validStatusField.fieldName>
<#elseif (column.dataType == "varchar" || column.columnFieldType == "char" || column.columnFieldType == "Short" || column.columnFieldType == "Byte")>
<#elseif (column.dataType == "Date")>
<#elseif (column.dataType == "BigDecimal" || column.columnFieldType == "Double" || column.columnFieldType == "Float")>
<#elseif (column.dataType == "String")>
<#else>
</#if>
