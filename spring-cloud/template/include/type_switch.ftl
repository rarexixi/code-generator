<#if column.ignoreSearch>
<#elseif column.columnName == table.validStatusField.fieldName>
<#elseif (column.columnFieldType == "Integer" || column.columnFieldType == "Long" || column.columnFieldType == "Short" || column.columnFieldType == "Byte")>
<#elseif (column.columnFieldType == "Date")>
<#elseif (column.columnFieldType == "BigDecimal" || column.columnFieldType == "Double" || column.columnFieldType == "Float")>
<#elseif (column.columnFieldType == "String")>
<#else>
</#if>
