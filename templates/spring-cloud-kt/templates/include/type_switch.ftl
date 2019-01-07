
<#assign canBeEqual = (column.columnName == table.validStatusColumn.columnName || column.dataType?contains("int") || column.dataType?contains("date") || column.dataType?ends_with("char"))>
<#assign canBeNull = column.nullable>
<#assign canBeList = (canBeEqual && column.columnName != table.validStatusColumn.columnName)>
<#assign canBeRange = (canBeList || column.dataType == "double" || column.dataType == "float" || column.dataType == "decimal" || column.dataType == "numeric" || column.dataType?contains("date") || column.dataType?contains("time"))>


<#if (column.ignoreSearch || column.dataType?ends_with("text"))>
<#else>
    <#if (canBeEqual)>
    </#if>
    <#if (canBeList)>
    </#if>
    <#if (canBeRange)>
    </#if>
    <#if (canBeNull)>
    </#if>
    <#if (column.dataType?ends_with("char"))>
    </#if>
</#if>

<#if column.ignoreSearch>
<#elseif (column.columnName == table.validStatusColumn.columnName)>
<#elseif (column.dataType?contains("int"))>
<#elseif (column.dataType == "double" || column.dataType == "float")>
<#elseif (column.dataType == "decimal" || column.dataType == "numeric")>
<#elseif (column.dataType?contains("date") || column.dataType?contains("time"))>
    <#if (column.dataType == "date")>
    </#if>
<#elseif (column.dataType?ends_with("char"))>
<#elseif (column.dataType?ends_with("text"))>
<#else>
</#if>
