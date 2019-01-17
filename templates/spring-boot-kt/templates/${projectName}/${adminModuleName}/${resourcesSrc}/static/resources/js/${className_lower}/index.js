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
        pageInfo: {},
        addOrEditParams: {
            <#list table.requiredColumns as column>
            <#include "/include/column/properties.ftl">
            <#if (column.validStatus)>
            <#-- ${fieldName}: ${table.validStatusColumn.validStatusOption.valid}<#if column?has_next>,</#if> -->
            <#else>
            ${fieldName}: ''<#if column?has_next>,</#if>
            </#if>
            </#list>
        },
        detail: {
            <#list table.columns as column>
            <#include "/include/column/properties.ftl">
            <#if (column.validStatus)>
            ${fieldName}: ${table.validStatusColumn.validStatusOption.valid}<#if column?has_next>,</#if>
            <#elseif (column.fkSelect || column.select)>
            ${fieldName}: <#if (isString)>''<#else>0</#if><#if column?has_next>,</#if>
            <#else>
            ${fieldName}: ''<#if column?has_next>,</#if>
            </#if>
            </#list>
        },
        addOrEditTitle: '',
        detailDialogVisible: false,
        addOrEditDialogVisible: false
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
            var self = this;
            self.resetSave();
            self.addOrEditDialogVisible = true;
            self.addOrEditTitle = '添加${tableComment}';
        },
        edit: function (item) {
            var self = this;
            self.resetSave();
            self.addOrEditDialogVisible = true;
            self.addOrEditTitle = '编辑${tableComment}';
            <#if !table.hasAutoIncUniPk>
            <#list pks as column>
            <#include "/include/column/properties.ftl">
            self.${fieldName} = item.${fieldName};
            </#list>
            </#if>

            var url = appConfig.baseApiPath + '/${classNameFirstLower}/getDetail';
            var params = self.getPkParams(item);
            self.ajaxGet(url, params, '获取${tableComment}详情失败！', function (response) {
                <#list table.columns as column>
                <#include "/include/column/properties.ftl">
                <#if column.notRequired>
                <#else>
                self.addOrEditParams.${fieldName} = response.result.${fieldName};
                </#if>
                </#list>
            });
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
            self.addOrEditParams.${fieldName} = '';
            </#if>
            </#list>
            self.closeDialog();
        },
        get: function (item) {
            var self = this;
            self.detailDialogVisible = true;

            var url = appConfig.baseApiPath + '/${classNameFirstLower}/getDetail';
            var params = self.getPkParams(item);
            self.ajaxGet(url, params, '获取详情失败！', function (response) {
                self.detail = response.result;
            });
        },
        closeDialog: function () {
            var self = this;
            self.detailDialogVisible = false;
            self.addOrEditDialogVisible = false;
        },
        <#if (table.hasUniPk)>
        handleSelectionChange: function (val) {
            this.multipleSelection = val;
        },
        </#if>
        <#include "/include/js/exec_list.ftl">
        <#assign isSplit = false>
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
