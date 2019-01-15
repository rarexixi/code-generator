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
        pkParams: {
            <#list pks as column>
            <#include "/include/column/properties.ftl">
            ${fieldName}: ''<#if column_has_next>,</#if>
            </#list>
        },
        detail: {
            <#list table.columns as column>
            <#include "/include/column/properties.ftl">
            <#if (column.validStatus)>
            <#-- ${fieldName}: ${table.validStatusColumn.validStatusOption.valid}<#if column_has_next>,</#if> -->
            <#elseif (column.fkSelect || column.select)>
            ${fieldName}: <#if (column.dataType?ends_with("char"))>''<#else>0</#if><#if column_has_next>,</#if>
            <#else>
            ${fieldName}: ''<#if column_has_next>,</#if>
            </#if>
            </#list>
        },
        addOrEditParams: {
            <#list table.requiredColumns as column>
            <#include "/include/column/properties.ftl">
            <#if (column.validStatus)>
            <#-- ${fieldName}: ${table.validStatusColumn.validStatusOption.valid}<#if column_has_next>,</#if> -->
            <#else>
            ${fieldName}: ''<#if column_has_next>,</#if>
            </#if>
            </#list>
        },
        addOrEditTitle: ''
    },
    mounted: function () {
        var self = this;
        <#list table.fkSelectColumns as column>
        <#include "/include/column/properties.ftl">
        self.init${propertyExceptKey}();
        </#list>
        if (location.search == '') {
            self.addOrEditTitle = '添加${tableComment}';
            document.title = self.addOrEditTitle;
        } else {
            self.addOrEditTitle = '更新{tableComment}';
            document.title = self.addOrEditTitle;

            <#list table.pks as column>
            <#include "/include/column/properties.ftl">
            self.pkParams.${fieldName} = commonFun.getParam('${fieldName}');
            </#list>
            self.get();
        }
    },
    methods: {
        add: function () {
            var self = this;
            self.resetSave();
            self.addOrEditDialogVisible = true;
            self.addOrEditTitle = '添加${tableComment}';
        },
        save: function () {
            var self = this;
            var ajaxUrl;
            <#if table.hasAutoIncUniPk>
            if (<#list pks as column><#include "/include/column/properties.ftl"><#if (column_index > 0)> && </#if>self.addOrEditParams.${fieldName} == ''</#list>) {
                ajaxUrl = appConfig.baseApiPath + '/${classNameFirstLower}/add';
            } else {
                ajaxUrl = appConfig.baseApiPath + '/${classNameFirstLower}/update';
            }
            <#else>
            if (<#list pks as column><#include "/include/column/properties.ftl"><#if (column_index > 0)> && </#if>self.${fieldName} == ''</#list>) {
                ajaxUrl = appConfig.baseApiPath + '/${classNameFirstLower}/add';
            } else {
                ajaxUrl = appConfig.baseApiPath + '/${classNameFirstLower}/update?'<#list pks as column><#include "/include/column/properties.ftl"><#if (column_index > 0)> + '&'</#if> + '${fieldName}=' + self.${fieldName}</#list>;
            }
            </#if>

            self.ajaxPost(ajaxUrl, self.addOrEditParams, '操作失败！', function (response) {
                self.$notify({
                    message: '操作成功！',
                    type: 'success'
                });
                self.resetSave();
                setTimeout(self.search, 1000);
            });
        },
        resetSave: function () {
            var self = this;
            <#list table.requiredColumns as column>
            <#include "/include/column/properties.ftl">
            <#if (column.validStatus)>
            <#-- self.addOrEditParams.${fieldName} = ${table.validStatusColumn.validStatusOption.valid}; -->
            <#else>
            self.addOrEditParams.${fieldName} = self.detail.${fieldName};
            </#if>
            </#list>
        },
        get: function () {
            var self = this;

            var url = appConfig.baseApiPath + '/${classNameFirstLower}/getDetail';
            var params = self.pkParams;
            self.ajaxGet(url, params, '获取详情失败！', function (response) {
                self.detail = response.result;
                <#list table.columns as column>
                <#include "/include/column/properties.ftl">
                <#if column.notRequired>
                <#else>
                self.addOrEditParams.${fieldName} = response.result.${fieldName};
                </#if>
                </#list>
            });
        },
        <#include "/include/js/select_get_text.ftl">
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
        back: function () {
            var self = this;
            location.href = 'list.html';
        }
    }
});
