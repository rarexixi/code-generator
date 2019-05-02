<#include "/include/table/properties.ftl">
<#macro mapperEl value>${r"${"}${value}}</#macro>
let app = new Vue({
    el: '#app',
    data: {
        <#include "/include/js/data_select_list.ftl">
        <#list table.fkSelectColumns as column>
        <#include "/include/column/properties.ftl">
        ${fieldNameExceptKey}SelectList: [],
        </#list>
        <#if !table.hasAutoIncUniPk>
        <#list pks as column>
        <#include "/include/column/properties.ftl">
        ${fieldName}: '',
        </#list>
        </#if>
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
        searchPage: {
            pageIndex: 1,
            pageSize: 10
        },

        multipleSelection: [],
        pageInfo: {}
    },
    mounted: function () {
        let self = this;
        self.search();
        <#list table.fkSelectColumns as column>
        <#include "/include/column/properties.ftl">
        self.init${propertyExceptKey}();
        </#list>
    },
    methods: {
        <#include "/include/js/search.ftl">
        add: function () {
            let url = 'addoredit.html';
            location.href = url;
        },
        edit: function (item) {
            let pkParams = '';
            <#list pks as column>
            <#include "/include/column/properties.ftl">
            pkParams += <#if (column?is_first)>'${fieldName}='<#else>'&${fieldName}='</#if> + item.${fieldName};
            </#list>
            let url = 'addoredit.html?' + pkParams;
            location.href = url;
        },
        get: function (item) {
            let pkParams = '';
            <#list pks as column>
            <#include "/include/column/properties.ftl">
            pkParams += <#if (column?is_first)>'${fieldName}='<#else>'&${fieldName}='</#if> + item.${fieldName};
            </#list>
            let url = 'detail.html?' + pkParams;
            location.href = url;
        },
        <#if (table.hasUniPk)>
        handleSelectionChange: function (val) {
            this.multipleSelection = val;
        },
        </#if>
        <#include "/include/js/exec_list.ftl">
        <#assign isSplit = true>
        <#include "/include/js/exec.ftl">
        <#include "/include/js/select_get_text.ftl">
        <#list table.fkSelectColumns as column>
        <#include "/include/column/properties.ftl">
        get${propertyName}Text: function (${column.fkSelectColumn.valueName?uncap_first}) {
            let self = this;
            let entity = self.${fieldNameExceptKey}SelectList.find(item => item.${column.fkSelectColumn.valueName?uncap_first} == ${column.fkSelectColumn.valueName?uncap_first});
            return entity ? entity.${column.fkSelectColumn.textName?uncap_first} : '';
        },
        </#list>
        <#list table.fkSelectColumns as column>
        <#include "/include/column/properties.ftl">
        init${propertyExceptKey}: function () {
            let self = this;
            let url = appConfig.baseApiPath + '/${column.fkSelectColumn.foreignTargetTableName?replace("_", "-")}/list';
            let params = {
                condition: {
                    <#list column.fkSelectColumn.conditions as condition>
                    ${condition.field}: '${condition.value}'<#if condition?has_next>,</#if>
                    </#list>
                },
                order: {}
            };
            self.ajaxPost(url, params, '获取${columnComment}列表失败！', response => {
                self.${fieldNameExceptKey}SelectList = response.result;
            });
        },
        </#list>
        exportExcel: function () {
            let self = this;

            let params = {
                condition: self.searchParams,
                order: self.sortParams
            };
            let paramsStr = JSON.stringify(params, (key, value) => {
                if (value) return value;
                return undefined;
            });
            window.open(appConfig.baseApiPath + '/${tablePath}/export?params=' + encodeURIComponent(paramsStr));
        }
    }
});
