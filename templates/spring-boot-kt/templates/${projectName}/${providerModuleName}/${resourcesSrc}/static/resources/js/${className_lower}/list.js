<#include "/include/table/properties.ftl">
<#macro mapperEl value>${r"${"}${value}}</#macro>
var app = new Vue({
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
        var self = this;
        self.search();
        <#list table.fkSelectColumns as column>
        <#include "/include/column/properties.ftl">
        self.init${propertyExceptKey}();
        </#list>
    },
    methods: {
        <#include "/include/js/search.ftl">
        add: function () {
            var url = 'addoredit.html';
            location.href = url;
        },
        edit: function (item) {
            var pkParams = '';
            <#list pks as column>
            <#include "/include/column/properties.ftl">
            pkParams += <#if (!column?is_first)>'&${fieldName}='<#else>'${fieldName}='</#if> + item.${fieldName};
            </#list>
            var url = 'addoredit.html?' + pkParams;
            location.href = url;
        },
        get: function (item) {
            var pkParams = '';
            <#list pks as column>
            <#include "/include/column/properties.ftl">
            pkParams += <#if (!column?is_first)>'&${fieldName}='<#else>'${fieldName}='</#if> + item.${fieldName};
            </#list>
            var url = 'detail.html?' + pkParams;
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
            var self = this;
            var entity = self.${fieldNameExceptKey}SelectList.find(function (item) {
                return item.${column.fkSelectColumn.valueName?uncap_first} == ${column.fkSelectColumn.valueName?uncap_first};
            });
            return entity ? entity.${column.fkSelectColumn.textName?uncap_first} : '';
        },
        </#list>
        <#list table.fkSelectColumns as column>
        <#include "/include/column/properties.ftl">
        init${propertyExceptKey}: function () {
            var self = this;
            var url = appConfig.baseApiPath + '/${column.fkSelectColumn.foreignClassName?uncap_first}/getList';
            var params = {
                condition: {},
                order: {}
            };
            self.ajaxPost(url, params, '获取${columnComment}列表失败！', function (response) {
                self.${fieldNameExceptKey}SelectList = response.result;
            });
        },
        </#list>
        exportExcel: function () {
            var self = this;

            var params = {
                condition: self.searchParams,
                order: {}
            };
            var paramsStr = JSON.stringify(params, function (key, value) {
                if (value) return value;
                return undefined;
            });
            window.open(appConfig.baseApiPath + '/${classNameFirstLower}/export?params=' + encodeURIComponent(paramsStr));
        }
    }
});
