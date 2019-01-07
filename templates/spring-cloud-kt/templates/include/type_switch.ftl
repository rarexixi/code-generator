
<#assign canBeEqual = (column.validStatus || column.dataType?contains("int") || column.dataType == "date" || column.dataType?ends_with("char"))>
<#assign canBeList = (column.dataType?contains("int") || column.dataType?contains("date") || column.dataType?ends_with("char"))>
<#assign canBeRange = (column.dataType?contains("int") || column.dataType == "double" || column.dataType == "float" || column.dataType == "decimal" || column.dataType == "numeric" || column.dataType?contains("date") || column.dataType?contains("time"))>
<#assign canBeNull = column.nullable>

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
<#elseif (column.select)>
<#elseif (column.fkSelect)>
<#elseif (column.validStatus)>
<#elseif (column.dataType == "bigint")>
<#elseif (column.dataType?ends_with("int") || column.dataType == "integer")>
<#elseif (column.dataType == "double" || column.dataType == "float")>
<#elseif (column.dataType == "decimal" || column.dataType == "numeric")>
<#elseif (column.dataType == "date")>
<#elseif (column.dataType?contains("date") || column.dataType?contains("time"))>
    <#if (column.dataType == "date")>
    </#if>
<#elseif (column.dataType?ends_with("char"))>
<#elseif (column.dataType?ends_with("text"))>
<#else>
</#if>
