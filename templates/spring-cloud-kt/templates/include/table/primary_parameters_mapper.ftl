<#list pks as column><#if (column_index > 0)>, </#if>${column.targetDataType} ${column.targetName?uncap_first}</#list>
