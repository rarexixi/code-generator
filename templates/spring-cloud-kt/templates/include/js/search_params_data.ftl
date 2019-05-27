            searchParams: {
<#if (table.validStatusColumn??)>
                ${table.validStatusColumn.targetName?uncap_first}: 'null',
</#if>
<#list table.indexes as column>
                <#include "/include/column/properties.ftl">
                <#if (column.validStatus)>
                <#elseif (column.select || column.fkSelect || column.pk || isInteger)>
                ${fieldName}: '',
                <#elseif (isDecimal)>
                ${fieldName}Min: '',
                ${fieldName}Max: '',
                <#elseif (isDate || isTime || isDateTime)>
                ${fieldName}Range: [],
                <#elseif (isContent)>
                <#elseif (isString)>
                ${fieldName}StartWith: '',
                <#else>
                </#if>
</#list>
            },
            sortParams: {
<#if table.hasAutoIncUniPk>
            <#list pks as column>
                <#include "/include/column/properties.ftl">
                ${fieldName}Sort: sortEnum.DESC
            </#list>
</#if>
            },
            searchPage: {
                pageIndex: searchPage.defaultPageIndex,
                pageSize: searchPage.defaultPageSize
            },
            multipleSelection: [],
            pageInfo: {},
