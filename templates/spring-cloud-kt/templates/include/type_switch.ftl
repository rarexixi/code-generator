
<#assign canBeEqual = (column.validStatus || column.dataType?contains("int") || column.dataType == "date" || column.dataType?ends_with("char"))>
<#assign canBeList = (column.dataType?contains("int") || column.dataType?contains("date") || column.dataType?ends_with("char"))>
<#assign canBeRange = (column.dataType?contains("int") || column.dataType == "double" || column.dataType == "float" || column.dataType == "decimal" || column.dataType == "numeric" || column.dataType?contains("date") || column.dataType?contains("time"))>
<#assign canBeNull = column.nullable>

<#assign isContent = (column.content || column.dataType?contains("text"))>
<#assign isInteger = (column.dataType?contains("int"))>
<#assign isDecimal = (column.dataType == "double" || column.dataType == "float" || column.dataType == "decimal" || column.dataType == "numeric")>
<#assign isString = (column.dataType?ends_with("char"))>
<#assign isDate = (column.dataType == "date")>
<#assign isTime = (column.dataType == "time")>
<#assign isDateTime = (column.dataType?contains("date") || column.dataType?contains("time"))>


<#if (column.ignoreSearch || column.dataType?ends_with("text"))>
<#else>
    <#if (isInteger)>
    <#elseif (canBeList)>
    <#elseif (canBeRange)>
    <#elseif (canBeNull)>
    <#elseif (column.dataType?ends_with("char"))>
    </#if>
</#if>

<#if (column.validStatus)>
<#elseif (column.select)>
<#elseif (column.fkSelect)>
<#elseif (column.pk)>
<#elseif (isInteger)>
<#elseif (isDecimal)>
<#elseif (isString)>
<#elseif (isDate)>
<#elseif (isTime)>
<#elseif (isDateTime)>
<#elseif (isContent)>
</#if>

<#if column.ignoreSearch>
<#elseif (column.select)>
<#elseif (column.fkSelect)>
<#elseif (column.validStatus)>
<#elseif (column.dataType?contains("int"))>
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
