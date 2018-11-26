<#assign className = table.tableClassName>
<#assign classNameFirstLower = table.tableClassName?uncap_first>
<#assign classNameLower = table.tableClassName?lower_case>
<#assign primaryKey = table.primaryKey>
<#assign primaryKeyParameters = table.primaryKeyParameters>
<#assign primaryKeyParameterValues = table.primaryKeyParameterValues>
<#macro mapperEl value>${r"${"}${value}}</#macro>

var app = new Vue({
    el: '#app',
    data: {
        <#list table.columns as column>
        <#if column.fkSelect>
        ${column.columnFieldName?uncap_first}SelectList: [],
        </#if>
        </#list>
        searchParams: {
            <#list table.columns as column>
            <#assign fieldName = column.columnFieldName?uncap_first>
            <#if column.ignoreSearch>
            <#elseif (column.columnName == table.validStatusField.fieldName)>
            ${fieldName}: '${table.validStatusField.validValue}',
            <#elseif (column.fkSelect || column.select)>
            ${fieldName}: '',
            <#elseif (column.columnFieldType == "Integer" || column.columnFieldType == "Long" || column.columnFieldType == "Short" || column.columnFieldType == "Byte")>
            ${fieldName}: '',
            <#elseif column.columnFieldType == "Date">
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
            sortEnums: [1],
            pageIndex: 1,
            pageSize: 10
        },
        multipleSelection: [],
        pageInfo: {}
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
            $.ajax({
                type: 'post',
                url: appConfig.baseApiPath + '/${column.fkSelectField.foreignClass?lower_case}/find',
                contentType: 'application/json',
                data: JSON.stringify({
                    pageIndex: 1,
                    pageSize: 1000
                }),
                dataType: 'json',
                success: function (response) {
                    if (response.success == true) {
                        self.${fieldName}SelectList = response.result.list;
                    } else {
                        self.$message({
                            message: '获取${columnComment}列表失败！',
                            type: 'error'
                        });
                    }
                }
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
            $.ajax({
                type: 'post',
                url: appConfig.baseApiPath + '/${classNameLower}/find',
                contentType: 'application/json',
                data: JSON.stringify(self.searchParams),
                dataType: 'json',
                success: function (response) {
                    if (response.success == true) {
                        self.pageInfo = response.result;
                    } else {
                        self.$message({
                            type: 'error',
                            message: '获取列表失败！'
                        });
                    }
                }
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
            <#elseif (column.columnName == table.validStatusField.fieldName)>
            this.searchParams.${fieldName} = '${table.validStatusField.validValue}';
            <#elseif (column.fkSelect || column.select)>
            this.searchParams.${fieldName} = '';
            <#elseif (column.columnFieldType == "Integer" || column.columnFieldType == "Long" || column.columnFieldType == "Short" || column.columnFieldType == "Byte")>
            this.searchParams.${fieldName} = '';
            <#elseif column.columnFieldType == "Date">
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
            var url = 'addoredit.html';
            window.location.href = url;
        },
        edit: function (<#include "/include/table/primary_values.ftl">) {
            var url = 'addoredit.html?' + <#list primaryKey as column><#if (column_index > 0)> + </#if>'<#if (column_index > 0)>&</#if>${column.columnFieldNameFirstLower}=' + ${column.columnFieldNameFirstLower}</#list>;
            window.location.href = url;
        },
        get: function (<#include "/include/table/primary_values.ftl">) {
            var url = 'detail.html?' + <#list primaryKey as column><#if (column_index > 0)> + </#if>'<#if (column_index > 0)>&</#if>${column.columnFieldNameFirstLower}=' + ${column.columnFieldNameFirstLower}</#list>;
            window.location.href = url;
        },
        <#if (table.hasPrimaryKey && table.uniquePrimaryKey??)>
        handleSelectionChange: function(val) {
            this.multipleSelection = val;
        },
        <#if table.validStatusColumn??>
        enableSelected: function () {
            this.execSelected("确定启用吗？", '/${classNameLower}/enableList', "启用成功！", "启用失败！");
        },
        disableSelected: function () {
            this.execSelected("确定禁用吗？", '/${classNameLower}/disableList', "禁用成功！", "禁用失败！");
        },
        </#if>
        delSelected: function () {
            this.execSelected("确定删除吗？", '/${classNameLower}/deleteList', "删除成功！", "删除失败！");
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
            }).then(() => {
                $.ajax({
                    type: 'post',
                    url: appConfig.baseApiPath + url,
                    contentType: 'application/json',
                    data: JSON.stringify(checkedList),
                    dataType: 'json',
                    success: function (response) {
                        if (response.success == true) {
                            self.$message({
                                type: 'success',
                                message: successMsg
                            });
                            self.search();
                        } else {
                            self.$message({
                                type: 'error',
                                message: failMsg
                            });
                        }
                        self.multipleSelection = [];
                    }
                });
            });
        },
        </#if>
        <#if table.validStatusColumn??>
        enable: function (<#include "/include/table/primary_values.ftl">) {
            var url = '/${classNameLower}/enable?' + <#list primaryKey as column><#if (column_index > 0)> + </#if>'<#if (column_index > 0)>&</#if>${column.columnFieldNameFirstLower}=' + ${column.columnFieldNameFirstLower}</#list>;
            this.exec("确定启用吗？", url, "启用成功！", "启用失败！");
        },
        disable: function (<#include "/include/table/primary_values.ftl">) {
            var url = '/${classNameLower}/disable?' + <#list primaryKey as column><#if (column_index > 0)> + </#if>'<#if (column_index > 0)>&</#if>${column.columnFieldNameFirstLower}=' + ${column.columnFieldNameFirstLower}</#list>;
            this.exec("确定禁用吗？", url, "禁用成功！", "禁用失败！");
        },
        </#if>
        del: function (<#include "/include/table/primary_values.ftl">) {
            var url = '/${classNameLower}/delete?' + <#list primaryKey as column><#if (column_index > 0)> + </#if>'<#if (column_index > 0)>&</#if>${column.columnFieldNameFirstLower}=' + ${column.columnFieldNameFirstLower}</#list>;
            this.exec("确定删除吗？", url, "删除成功！", "删除失败！");
        },
        exec: function (confirmMsg, url, successMsg, failMsg) {
            var self = this;
            self.$confirm(confirmMsg, '', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning'
            }).then(() => {
                $.ajax({
                    type: 'get',
                    url: appConfig.baseApiPath + url,
                    dataType: 'json',
                    success: function (response) {
                        if (response.success == true) {
                            self.$message({
                                type: 'success',
                                message: successMsg
                            });
                            self.search();
                        } else {
                            self.$message({
                                type: 'error',
                                message: failMsg
                            });
                        }
                    }
                })
            });
        }
    }
});
