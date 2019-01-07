<#list pks as column><#if (column_index > 0)>, </#if>@Param("${column.targetName?uncap_first}") ${column.targetDataType} ${column.targetName?uncap_first}</#list>
