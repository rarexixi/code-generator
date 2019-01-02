<#assign className = table.tableClassName>
<#assign classNameFirstLower = table.tableClassName?uncap_first>
<#assign classNameLower = table.tableClassName?lower_case>
<#assign tableShortComment = (table.tableComment?split("[（ ,，(]", "r"))[0]>
<#assign primaryKey = table.primaryKey>
<#assign primaryKeyParameters = table.primaryKeyParameters>
<#assign primaryKeyParameterValues = table.primaryKeyParameterValues>
<#macro mapperEl value>${r"${"}${value}}</#macro>

var app = new Vue({
    el: '#app',
    data: {
        <#list table.columns as column>
        <#if column.fkSelect>
        ${column.targetColumnName?uncap_first}SelectList: [],
        </#if>
        </#list>
        <#if !table.hasAutoIncrementUniquePrimaryKey>
        <#list primaryKey as column>
        old${column.targetColumnName}: '',
        </#list>
        </#if>
        searchParams: {
            <#list table.columns as column>
            <#assign fieldName = column.targetColumnName?uncap_first>
            <#if column.ignoreSearch>
            <#elseif (column.columnName == table.validStatusField.fieldName)>
            ${fieldName}: '${table.validStatusField.validValue}',
            <#elseif (column.fkSelect || column.select)>
            ${fieldName}: '',
            <#elseif (column.targetDataType == "Integer" || column.targetDataType == "Long" || column.targetDataType == "Short" || column.targetDataType == "Byte")>
            ${fieldName}: '',
            <#elseif column.targetDataType == "Date">
            ${fieldName}Range: [],
            <#elseif (column.targetDataType == "BigDecimal" || column.targetDataType == "Double" || column.targetDataType == "Float")>
            ${fieldName}Min: '',
            ${fieldName}Max: '',
            <#elseif (column.targetDataType == "String")>
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
            <#assign fieldName = column.targetColumnName?uncap_first>
            <#if column.notRequired>
            <#elseif column.fkSelect>
            ${fieldName}: ''<#if column_has_next>,</#if>
            <#elseif column.columnName == table.validStatusField.fieldName>
            ${fieldName}: '${table.validStatusField.validValue}'<#if column_has_next>,</#if>
            <#elseif column.targetDataType == "Date">
            ${fieldName}: ''<#if column_has_next>,</#if>
            <#else>
            ${fieldName}: ''<#if column_has_next>,</#if>
            </#if>
            </#list>
        },
        detail: {
            <#list table.columns as column>
            <#if column.fkSelect>
            ${column.targetColumnNameFirstLower}: 0<#if column_has_next>,</#if>
            <#elseif column.columnName == table.validStatusField.fieldName>
            ${column.targetColumnNameFirstLower}: ${table.validStatusField.validValue}<#if column_has_next>,</#if>
            <#elseif column.targetDataType == "Date">
            ${column.targetColumnNameFirstLower}: ''<#if column_has_next>,</#if>
            <#else>
            ${column.targetColumnNameFirstLower}: ''<#if column_has_next>,</#if>
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
        self.init${column.targetColumnName?replace('Id', '')}();
        </#if>
        </#list>
    },
    methods: {
        <#list table.columns as column>
        <#if column.fkSelect>
        <#assign columnComment = (column.columnComment?split("[（ ,，(]", "r"))[0]>
        <#assign fieldName = column.targetColumnName?uncap_first>
        init${column.targetColumnName?replace('Id', '')}: function () {
            var self = this;
            $.ajax({
                type: 'post',
                url: appConfig.baseApiPath + '/${column.fkSelectField.foreignClass?lower_case}/search',
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
                url: appConfig.baseApiPath + '/${classNameLower}/search',
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
            if (self.searchParams.${table.validStatusColumn.targetColumnNameFirstLower} === valid) {
                return;
            }
            self.resetSearch();
            self.searchParams.${table.validStatusColumn.targetColumnNameFirstLower} = valid;
            self.search();
        },
        </#if>
        resetSearch: function() {
            <#list table.columns as column>
            <#assign fieldName = column.targetColumnName?uncap_first>
            <#if column.ignoreSearch>
            <#elseif (column.columnName == table.validStatusField.fieldName)>
            this.searchParams.${fieldName} = '${table.validStatusField.validValue}';
            <#elseif (column.fkSelect || column.select)>
            this.searchParams.${fieldName} = '';
            <#elseif (column.targetDataType == "Integer" || column.targetDataType == "Long" || column.targetDataType == "Short" || column.targetDataType == "Byte")>
            this.searchParams.${fieldName} = '';
            <#elseif column.targetDataType == "Date">
            this.searchParams.${fieldName}Range = [];
            <#elseif (column.targetDataType == "BigDecimal" || column.targetDataType == "Double" || column.targetDataType == "Float")>
            this.searchParams.${fieldName}Min = '';
            this.searchParams.${fieldName}Max = '';
            <#elseif (column.targetDataType == "String")>
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
            self.addOrEditTitle = '添加${tableShortComment}';
        },
        edit: function (<#include "/include/table/primary_values.ftl">) {
            var self = this;
            self.resetSave();
            self.addOrEditDialogVisible = true;
            self.addOrEditTitle = '编辑${tableShortComment}';
            <#if !table.hasAutoIncrementUniquePrimaryKey>
            <#list primaryKey as column>
            <#assign fieldName = column.targetColumnName?uncap_first>
            self.old${column.targetColumnName} = ${fieldName};
            </#list>
            </#if>
            $.ajax({
                type: 'get',
                url: appConfig.baseApiPath + '/${classNameLower}/getDetail?' + <#list primaryKey as column><#if (column_index > 0)> + </#if>'<#if (column_index > 0)>&</#if>${column.targetColumnNameFirstLower}=' + ${column.targetColumnNameFirstLower}</#list>,
                dataType: 'json',
                success: function (response) {
                    if (response.success == true) {
                        <#list table.columns as column>
                        <#if column.notRequired>
                        <#else>
                        <#assign fieldName = column.targetColumnName?uncap_first>
                        self.addOrEditParams.${fieldName} = '' + response.result.${fieldName};
                        </#if>
                        </#list>
                    } else {
                        self.$message({
                            message: '获取详情失败！',
                            type: 'error'
                        });
                    }
                }
            });
        },
        save: function () {
            var self = this;
            var ajaxUrl;
            <#if table.hasAutoIncrementUniquePrimaryKey>
            if (<#list primaryKey as column><#if (column_index > 0)> && </#if>self.addOrEditParams.${column.targetColumnNameFirstLower} == ''</#list>) {
                ajaxUrl = appConfig.baseApiPath + '/${classNameLower}/add';
            } else {
                ajaxUrl = appConfig.baseApiPath + '/${classNameLower}/edit';
            }
            <#else>
            if (<#list primaryKey as column><#if (column_index > 0)> && </#if>self.old${column.targetColumnName} == ''</#list>) {
                ajaxUrl = appConfig.baseApiPath + '/${classNameLower}/add';
            } else {
                ajaxUrl = appConfig.baseApiPath + '/${classNameLower}/edit?'<#list primaryKey as column><#if (column_index > 0)> + '&'</#if> + 'old${column.targetColumnName}=' + self.old${column.targetColumnName}</#list>;
            }
            </#if>
            $.ajax({
                type: 'post',
                url: ajaxUrl,
                contentType: 'application/json',
                data: JSON.stringify(self.addOrEditParams),
                dataType: 'json',
                success: function (response) {
                    if (response.success == true) {
                        self.$message({
                            message: '操作成功！',
                            type: 'success'
                        });
                        self.resetSave();
                        setTimeout(self.search, 1000);
                    } else {
                        self.$message({
                            message: '操作失败！',
                            type: 'error'
                        });
                    }
                }
            });
        },
        resetSave: function() {
            var self = this;
            <#list table.columns as column>
            <#if column.notRequired>
            <#elseif column.fkSelect>
            self.addOrEditParams.${column.targetColumnNameFirstLower} = '';
            <#elseif column.columnName == table.validStatusField.fieldName>
            self.addOrEditParams.${column.targetColumnNameFirstLower} = '${table.validStatusField.validValue}';
            <#elseif column.targetDataType == "Date">
            self.addOrEditParams.${column.targetColumnNameFirstLower} = '';
            <#else>
            self.addOrEditParams.${column.targetColumnNameFirstLower} = '';
            </#if>
            </#list>
            self.closeDialog();
        },
        get: function (<#include "/include/table/primary_values.ftl">) {
            var self = this;
            self.detailDialogVisible = true;
            $.ajax({
                type: 'get',
                url: appConfig.baseApiPath + '/${classNameLower}/getDetail?' + <#list primaryKey as column><#if (column_index > 0)> + </#if>'<#if (column_index > 0)>&</#if>${column.targetColumnNameFirstLower}=' + ${column.targetColumnNameFirstLower}</#list>,
                dataType: 'json',
                success: function (response) {
                    if (response.success == true) {
                        self.detail = response.result;
                    } else {
                        self.$message({
                            message: '获取详情失败！',
                            type: 'error'
                        });
                    }
                }
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
            var url = '/${classNameLower}/enable?' + <#list primaryKey as column><#if (column_index > 0)> + </#if>'<#if (column_index > 0)>&</#if>${column.targetColumnNameFirstLower}=' + ${column.targetColumnNameFirstLower}</#list>;
            this.exec("确定启用吗？", url, "启用成功！", "启用失败！");
        },
        disable: function (<#include "/include/table/primary_values.ftl">) {
            var url = '/${classNameLower}/disable?' + <#list primaryKey as column><#if (column_index > 0)> + </#if>'<#if (column_index > 0)>&</#if>${column.targetColumnNameFirstLower}=' + ${column.targetColumnNameFirstLower}</#list>;
            this.exec("确定禁用吗？", url, "禁用成功！", "禁用失败！");
        },
        </#if>
        del: function (<#include "/include/table/primary_values.ftl">) {
            var url = '/${classNameLower}/delete?' + <#list primaryKey as column><#if (column_index > 0)> + </#if>'<#if (column_index > 0)>&</#if>${column.targetColumnNameFirstLower}=' + ${column.targetColumnNameFirstLower}</#list>;
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
