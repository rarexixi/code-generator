<#include "/include/table/properties.ftl">
<#macro mapperEl value>${r"${"}${value}}</#macro>
var app = new Vue({
    el: '#app',
    data: {
        <#list table.columns as column>
        <#if column.fkSelect>
        ${column.columnFieldName?uncap_first}SelectList: [],
        <#elseif (column.select)>
        ${column.columnFieldName?uncap_first}SelectList: [<#list column.selectOptions as option>{
            value: ${option.value}, text: '${option.text}'
        }<#if option_has_next>,</#if></#list>],
        <#elseif column.columnName == table.validStatusColumn.columnName>
        ${column.columnFieldName?uncap_first}SelectList: [{
            value: ${table.validStatusField.validValue}, text: '有效'
        }, {
            value: ${table.validStatusField.invalidValue}, text: '无效'
        }],
        </#if>
        </#list>
        <#if !table.hasAutoIncrementUniquePrimaryKey>
        <#list pks as column>
        old${column.columnFieldName}: '',
        </#list>
        </#if>
        searchParams: {
            <#list table.columns as column>
            <#assign fieldName = column.columnFieldName?uncap_first>
            <#if column.ignoreSearch>
            <#elseif (column.columnName == table.validStatusColumn.columnName)>
            ${fieldName}: ${table.validStatusField.validValue},
            <#elseif (column.fkSelect || column.select)>
            ${fieldName}: '',
            <#elseif (column.columnFieldType == "Integer" || column.columnFieldType == "Long" || column.columnFieldType == "Short" || column.columnFieldType == "Byte")>
            ${fieldName}: '',
            <#elseif (column.dataType?contains("date") || column.dataType?contains("time"))>
            ${fieldName}Range: [],
            <#elseif (column.columnFieldType == "BigDecimal" || column.columnFieldType == "Double" || column.columnFieldType == "Float")>
            ${fieldName}Min: '',
            ${fieldName}Max: '',
            <#elseif (column.columnFieldType == "String")>
            ${fieldName}StartWith: '',
            <#else>
            ${fieldName}: '',
            </#if>
            </#list>
            sortEnums: [2],
            pageIndex: 1,
            pageSize: 10
        },
        multipleSelection: [],
        pageInfo: {},
        addOrEditParams: {
            <#list table.columns as column>
            <#assign fieldName = column.columnFieldName?uncap_first>
            <#if column.notRequired>
            <#elseif column.fkSelect>
            ${fieldName}: ''<#if column_has_next>,</#if>
            <#elseif column.columnName == table.validStatusColumn.columnName>
            <#-- ${fieldName}: ${table.validStatusField.validValue}<#if column_has_next>,</#if> -->
            <#elseif (column.dataType?contains("date") || column.dataType?contains("time"))>
            ${fieldName}: ''<#if column_has_next>,</#if>
            <#else>
            ${fieldName}: ''<#if column_has_next>,</#if>
            </#if>
            </#list>
        },
        detail: {
            <#list table.columns as column>
            <#assign fieldName = column.columnFieldName?uncap_first>
            <#if column.fkSelect>
            ${fieldName}: <#if (column.columnFieldType == "String")>''<#else>0</#if><#if column_has_next>,</#if>
            <#elseif column.columnName == table.validStatusColumn.columnName>
            ${fieldName}: ${table.validStatusField.validValue}<#if column_has_next>,</#if>
            <#elseif (column.dataType?contains("date") || column.dataType?contains("time"))>
            ${fieldName}: ''<#if column_has_next>,</#if>
            <#else>
            ${fieldName}: ''<#if column_has_next>,</#if>
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
        <#list table.columns as column>
        <#if column.fkSelect>
        self.init${column.columnFieldName?replace('Id', '')}();
        </#if>
        </#list>
    },
    methods: {
        <#list table.columns as column>
        <#if column.fkSelect>
        <#assign columnComment = (column.columnComment?split("[（ ,，(]", "r"))[0]>
        <#assign fieldName = column.columnFieldName?uncap_first>
        init${column.columnFieldName?replace('Id', '')}: function () {
            var self = this;
            var url = appConfig.baseApiPath + '/${column.fkSelectField.foreignClass?lower_case}/list';
            var params = {};
            self.ajaxPost(url, params, '获取${columnComment}列表失败！', function(response) {
                self.${column.columnFieldName?uncap_first}SelectList = response.result;
            });
        },
        </#if>
        </#list>
        changePage: function(pageIndex) {
            if (this.searchParams.pageIndex == pageIndex) {
                return;
            }
            this.searchParams.pageIndex = pageIndex;
            this.search();
        },
        changePageSize: function(pageSize) {
            if (this.searchParams.pageSize == pageSize) {
                return;
            }
            this.searchParams.pageSize = pageSize;
            this.searchParams.pageIndex = 1;
            this.search();
        },
        search: function () {
            var self = this;
            self.checkedList = [];

            var url = appConfig.baseApiPath + '/${classNameLower}/search';
            self.ajaxPost(url, self.searchParams, '获取${tableComment}列表失败！', function(response) {
                self.pageInfo = response.result;
            });
        },
        <#if table.validStatusColumn??>
        changeValidSearch: function(valid) {
            var self = this;
            if (self.searchParams.${table.validStatusColumn.columnFieldNameFirstLower} === valid) {
                return;
            }
            self.resetSearch();
            self.searchParams.${table.validStatusColumn.columnFieldNameFirstLower} = valid;
            self.search();
        },
        </#if>
        resetSearch: function() {
            <#list table.columns as column>
            <#assign fieldName = column.columnFieldName?uncap_first>
            <#if column.ignoreSearch>
            <#elseif (column.columnName == table.validStatusColumn.columnName)>
            this.searchParams.${fieldName} = ${table.validStatusField.validValue};
            <#elseif (column.fkSelect || column.select)>
            this.searchParams.${fieldName} = '';
            <#elseif (column.columnFieldType == "Integer" || column.columnFieldType == "Long" || column.columnFieldType == "Short" || column.columnFieldType == "Byte")>
            this.searchParams.${fieldName} = '';
            <#elseif (column.dataType?contains("date") || column.dataType?contains("time"))>
            this.searchParams.${fieldName}Range = [];
            <#elseif (column.columnFieldType == "BigDecimal" || column.columnFieldType == "Double" || column.columnFieldType == "Float")>
            this.searchParams.${fieldName}Min = '';
            this.searchParams.${fieldName}Max = '';
            <#elseif (column.columnFieldType == "String")>
            this.searchParams.${fieldName}StartWith = '';
            <#else>
            this.searchParams.${fieldName} = '';
            </#if>
            </#list>
            this.searchParams.pageIndex = 1;
            this.searchParams.pageSize = 10;
        },
        add: function() {
            var self = this;
            self.resetSave();
            self.addOrEditDialogVisible = true;
            self.addOrEditTitle = '添加${tableComment}';
        },
        edit: function (<#include "/include/table/primary_values.ftl">) {
            var self = this;
            self.resetSave();
            self.addOrEditDialogVisible = true;
            self.addOrEditTitle = '编辑${tableComment}';
            <#if !table.hasAutoIncrementUniquePrimaryKey>
            <#list pks as column>
            <#assign fieldName = column.columnFieldName?uncap_first>
            self.old${column.columnFieldName} = ${fieldName};
            </#list>
            </#if>

            var url = appConfig.baseApiPath + '/${classNameLower}/getDetail';
            var params = {
                <#list pks as column>
                ${column.columnFieldNameFirstLower}: ${column.columnFieldNameFirstLower}<#if (column_has_next)>,</#if>
                </#list>
            };
            self.ajaxGet(url, params, '获取${tableComment}详情失败！', function(response) {
                <#list table.columns as column>
                <#if column.notRequired>
                <#else>
                <#assign fieldName = column.columnFieldName?uncap_first>
                self.addOrEditParams.${fieldName} = response.result.${fieldName};
                </#if>
                </#list>
            });
        },
        save: function () {
            var self = this;
            var ajaxUrl;
            <#if table.hasAutoIncrementUniquePrimaryKey>
            if (<#list pks as column><#if (column_index > 0)> && </#if>self.addOrEditParams.${column.columnFieldNameFirstLower} == ''</#list>) {
                ajaxUrl = appConfig.baseApiPath + '/${classNameLower}/add';
            } else {
                ajaxUrl = appConfig.baseApiPath + '/${classNameLower}/edit';
            }
            <#else>
            if (<#list pks as column><#if (column_index > 0)> && </#if>self.old${column.columnFieldName} == ''</#list>) {
                ajaxUrl = appConfig.baseApiPath + '/${classNameLower}/add';
            } else {
                ajaxUrl = appConfig.baseApiPath + '/${classNameLower}/edit?'<#list pks as column><#if (column_index > 0)> + '&'</#if> + 'old${column.columnFieldName}=' + self.old${column.columnFieldName}</#list>;
            }
            </#if>

            self.ajaxPost(ajaxUrl, self.addOrEditParams, '操作失败！', function(response) {
                self.$notify({
                    message: '操作成功！',
                    type: 'success'
                });
                self.resetSave();
                setTimeout(self.search, 1000);
            });
        },
        resetSave: function() {
            var self = this;
            <#list table.columns as column>
            <#if column.notRequired>
            <#elseif column.columnName == table.validStatusColumn.columnName>
            <#-- self.addOrEditParams.${column.columnFieldNameFirstLower} = ${table.validStatusField.validValue}; -->
            <#elseif (column.dataType?contains("date") || column.dataType?contains("time"))>
            self.addOrEditParams.${column.columnFieldNameFirstLower} = '';
            <#else>
            self.addOrEditParams.${column.columnFieldNameFirstLower} = '';
            </#if>
            </#list>
            self.closeDialog();
        },
        get: function (<#include "/include/table/primary_values.ftl">) {
            var self = this;
            self.detailDialogVisible = true;

            var url = appConfig.baseApiPath + '/${classNameLower}/getDetail';
            var params = {
                <#list pks as column>
                ${column.columnFieldNameFirstLower}: ${column.columnFieldNameFirstLower}<#if (column_has_next)>,</#if>
                </#list>
            };
            self.ajaxGet(url, params, '获取详情失败！', function(response) {
                self.detail = response.result;
            });
        },
        closeDialog: function () {
            var self = this;
            self.detailDialogVisible = false;
            self.addOrEditDialogVisible = false;
        },
        <#if (table.hasPrimaryKey && table.uniquePrimaryKey??)>
        handleSelectionChange: function(val) {
            this.multipleSelection = val;
        },
        <#if table.validStatusColumn??>
        enableSelected: function () {
            this.execSelected("确定启用吗？", appConfig.baseApiPath + '/${classNameLower}/enableList', "启用成功！", "启用失败！");
        },
        disableSelected: function () {
            this.execSelected("确定禁用吗？", appConfig.baseApiPath + '/${classNameLower}/disableList', "禁用成功！", "禁用失败！");
        },
        </#if>
        delSelected: function () {
            this.execSelected("确定删除吗？", appConfig.baseApiPath + '/${classNameLower}/deleteList', "删除成功！", "删除失败！");
        },
        execSelected: function (confirmMsg, url, successMsg, failMsg) {
            var self = this;
            var checkedList = [];
            for (var i = 0; i < self.multipleSelection.length; i++) {
                var item = self.multipleSelection[i];
                checkedList.push(item.<#include "/include/table/primary_values.ftl">);
            }
            self.$confirm(confirmMsg, '', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning'
            }).then(function () {
                self.ajaxPost(url, checkedList, failMsg, function(response) {
                    self.$notify({
                        type: 'success',
                        message: successMsg
                    });
                    self.search();
                });
            });
        },
        </#if>
        <#if table.validStatusColumn??>
        enable: function (<#include "/include/table/primary_values.ftl">) {
            var url = appConfig.baseApiPath + '/${classNameLower}/enable?' + <#list pks as column><#if (column_index > 0)> + </#if>'<#if (column_index > 0)>&</#if>${column.columnFieldNameFirstLower}=' + ${column.columnFieldNameFirstLower}</#list>;
            this.exec("确定启用吗？", url, {}, "启用成功！", "启用失败！");
        },
        disable: function (<#include "/include/table/primary_values.ftl">) {
            var url = appConfig.baseApiPath + '/${classNameLower}/disable?' + <#list pks as column><#if (column_index > 0)> + </#if>'<#if (column_index > 0)>&</#if>${column.columnFieldNameFirstLower}=' + ${column.columnFieldNameFirstLower}</#list>;
            this.exec("确定禁用吗？", url, {}, "禁用成功！", "禁用失败！");
        },
        </#if>
        del: function (<#include "/include/table/primary_values.ftl">) {
            var url = appConfig.baseApiPath + '/${classNameLower}/delete?' + <#list pks as column><#if (column_index > 0)> + </#if>'<#if (column_index > 0)>&</#if>${column.columnFieldNameFirstLower}=' + ${column.columnFieldNameFirstLower}</#list>;
            this.exec("确定删除吗？", url, {}, "删除成功！", "删除失败！");
        },
        exec: function (confirmMsg, url, params, successMsg, failMsg) {
            var self = this;
            self.$confirm(confirmMsg, '', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning'
            }).then(function () {

                self.ajaxGet(url, params, failMsg, function(response) {
                    self.$notify({
                        type: 'success',
                        message: successMsg
                    });
                    self.search();
                });
            });
        },
        <#list table.columns as column>
        <#if column.fkSelect>
        get${column.columnFieldName}Text: function (${column.fkSelectField.keyField?uncap_first}) {
            var self = this;
            var entity = self.${column.columnFieldName?uncap_first}SelectList.find(function (item) {
                return item.${column.fkSelectField.keyField?uncap_first} == ${column.fkSelectField.keyField?uncap_first};
            });
            return entity ? entity.${column.fkSelectField.valueField?uncap_first} : '';
        },
        <#elseif (column.select || column.columnName == table.validStatusColumn.columnName)>
        get${column.columnFieldName}Text: function (value) {
            var self = this;
            var entity = self.${column.columnFieldName?uncap_first}SelectList.find(function (item) {
                return item.value == value;
            });
            return entity ? entity.text : '';
        },
        </#if>
        </#list>
        exportExcel: function() {
            var self = this;
            var params = JSON.stringify(self.searchParams, function (key, value) {
                if (value) return value;
                return undefined;
            });
            window.open(appConfig.baseApiPath + '/${classNameLower}/export?params=' + encodeURIComponent(params));
        }
    }
});
