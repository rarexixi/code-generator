<#if (table.validStatusColumn??)>
        ${table.validStatusColumn.targetName?uncap_first}SelectList: [{
            value: ${table.validStatusColumn.validStatusOption.valid}, text: '有效'
        }, {
            value: ${table.validStatusColumn.validStatusOption.invalid}, text: '无效'
        }],
</#if>
<#list table.selectColumns as column>
        <#include "/include/column/properties.ftl">
        ${fieldNameExceptKey}SelectList: [<#list column.selectOptions as option>{
            <#if (isInteger)>
            value: ${option.value}, text: '${option.text}'
            <#else>
            value: '${option.value}', text: '${option.text}'
            </#if>
        }<#if option?has_next>, </#if></#list>],
</#list>
