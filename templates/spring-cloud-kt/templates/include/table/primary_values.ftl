<#list pks as column><#if (column_index > 0)>, </#if>${column.targetName?uncap_first}</#list>
