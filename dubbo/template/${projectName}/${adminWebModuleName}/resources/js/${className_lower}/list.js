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
        ${column.targetColumnName?uncap_first}SelectList: [],
        </#if>
        </#list>
        searchParams: {
            <#list table.columns as column>
            <#if column.ignoreSearch>
            <#elseif (column.columnName == table.validStatusField.fieldName || column.fkSelect || column.select)>
            ${column.targetColumnNameFirstLower}: '',
            <#elseif (column.targetDataType == "Integer" || column.targetDataType == "Long" || column.targetDataType == "Short" || column.targetDataType == "Byte")>
            ${column.targetColumnNameFirstLower}: '',
            <#elseif column.targetDataType == "Date">
            ${column.targetColumnNameFirstLower}Min: '',
            ${column.targetColumnNameFirstLower}Max: '',
            <#elseif (column.targetDataType == "BigDecimal" || column.targetDataType == "Double" || column.targetDataType == "Float")>
            ${column.targetColumnNameFirstLower}Min: '',
            ${column.targetColumnNameFirstLower}Max: '',
            <#elseif (column.targetDataType == "String")>
            ${column.targetColumnNameFirstLower}StartWith: '',
            <#else>
            ${column.targetColumnNameFirstLower}: '',
            </#if>
            </#list>
            pageIndex: 1,
            pageSize: 10
        },
        checkedList: [],
        allChecked: false,
        pageInfo: {},
        pager: {}
    },
    mounted: function () {
        var self = this;
        self.search();
        <#list table.columns as column>
        <#if column.fkSelect>
        self.init${column.targetColumnName?replace('Id', '')}();
        </#if>
        </#list>
        self.pager = new bootSelectPager('#pager', {
            changePageSize: function (pageSize) {
                self.changePageSize(pageSize)
            },
            changePageIndex: function (pageIndex) {
                self.changePage(pageIndex);
            }
        });
    },
    watch: {
        'checkedList': {
            handler: function (val, oldVal) {
                var self = this;
                var listLength = 0;
                if (self.pageInfo.list) {
                    listLength = self.pageInfo.list.length;
                }

                if (self.checkedList.length === listLength && listLength > 0) {
                    self.allChecked = true;
                } else {
                    self.allChecked = false;
                }
            },
            deep: true
        }
    },
    methods: {
        <#list table.columns as column>
        <#if column.fkSelect>
        init${column.targetColumnName?replace('Id', '')}: function () {
            var self = this;
            $.ajax({
                type: 'post',
                url: appConfig.baseApiPath + '/${column.fkSelectField.foreignClass?lower_case}/find',
                contentType : 'application/json',
                data : JSON.stringify({
                    pageIndex: 1,
                    pageSize: 1000
                }),
                dataType: 'json',
                success: function (response) {
                    if (response.success == true) {
                        self.${column.targetColumnName?uncap_first}SelectList = response.result.list;
                    } else {
                        commonNotify.danger("获取列表失败！");
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
                contentType : 'application/json',
                data : JSON.stringify(self.searchParams),
                dataType: 'json',
                success: function (response) {
                    if (response.success == true) {
                        self.pageInfo = response.result;
                        self.pager.refreshPageInfo({
                            pageIndex: self.pageInfo.pageNum,
                            pageSize: self.pageInfo.pageSize,
                            total: self.pageInfo.total
                        });
                    } else {
                        commonNotify.danger("获取列表！");
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
            <#if column.ignoreSearch>
            <#elseif (column.columnName == table.validStatusField.fieldName || column.fkSelect || column.select)>
            this.searchParams.${column.targetColumnNameFirstLower} = '';
            <#elseif (column.targetDataType == "Integer" || column.targetDataType == "Long" || column.targetDataType == "Short" || column.targetDataType == "Byte")>
            this.searchParams.${column.targetColumnNameFirstLower} = '';
            <#elseif column.targetDataType == "Date">
            this.searchParams.${column.targetColumnNameFirstLower}Min = '';
            this.searchParams.${column.targetColumnNameFirstLower}Max = '';
            <#elseif (column.targetDataType == "BigDecimal" || column.targetDataType == "Double" || column.targetDataType == "Float")>
            this.searchParams.${column.targetColumnNameFirstLower}Min = '';
            this.searchParams.${column.targetColumnNameFirstLower}Max = '';
            <#elseif (column.targetDataType == "String")>
            this.searchParams.${column.targetColumnNameFirstLower}StartWith = '';
            <#else>
            this.searchParams.${column.targetColumnNameFirstLower} = '';
            </#if>
            </#list>
            this.searchParams.pageIndex = 1;
            this.searchParams.pageSize = 10;
        },
        add: function() {
            var url = 'addoredit.html';
            window.location.href = url;
        },
        edit: function (${primaryKeyParameterValues}) {
            var url = 'addoredit.html?' + <#list primaryKey as column><#if (column_index > 0)> + </#if>'<#if (column_index > 0)>&</#if>${column.targetColumnNameFirstLower}=' + ${column.targetColumnNameFirstLower}</#list>;
            window.location.href = url;
        },
        get: function (${primaryKeyParameterValues}) {
            var url = 'detail.html?' + <#list primaryKey as column><#if (column_index > 0)> + </#if>'<#if (column_index > 0)>&</#if>${column.targetColumnNameFirstLower}=' + ${column.targetColumnNameFirstLower}</#list>;
            window.location.href = url;
        },
        <#if (table.hasPrimaryKey && table.uniquePrimaryKey??)>
        checkAll: function (moduleName) {
            var self = this;

            if (self.allChecked) {
                self.checkedList = [];
            } else {
                self.checkedList = [];
                if (self.pageInfo.list) {
                    self.pageInfo.list.forEach(function (item) {
                        self.checkedList.push(item.${table.uniquePrimaryKey.targetColumnName?uncap_first});
                    });
                }
            }
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
            commonNotify.confirm(confirmMsg, function() {
                $.ajax({
                    type: 'post',
                    url: appConfig.baseApiPath + url,
                    contentType : 'application/json',
                    data : JSON.stringify(self.checkedList),
                    dataType: 'json',
                    success: function (response) {
                        if (response.success == true) {
                            commonNotify.success(successMsg, self.search);
                        } else {
                            commonNotify.danger(failMsg);
                        }
                        self.checkedList = [];
                    }
                });
            });
        },
        </#if>
        <#if table.validStatusColumn??>
        enable: function (${primaryKeyParameterValues}) {
            var url = '/${classNameLower}/enable?' + <#list primaryKey as column><#if (column_index > 0)> + </#if>'<#if (column_index > 0)>&</#if>${column.targetColumnNameFirstLower}=' + ${column.targetColumnNameFirstLower}</#list>;
            this.exec("确定启用吗？", url, "启用成功！", "启用失败！");
        },
        disable: function (${primaryKeyParameterValues}) {
            var url = '/${classNameLower}/disable?' + <#list primaryKey as column><#if (column_index > 0)> + </#if>'<#if (column_index > 0)>&</#if>${column.targetColumnNameFirstLower}=' + ${column.targetColumnNameFirstLower}</#list>;
            this.exec("确定禁用吗？", url, "禁用成功！", "禁用失败！");
        },
        </#if>
        del: function (${primaryKeyParameterValues}) {
            var url = '/${classNameLower}/delete?' + <#list primaryKey as column><#if (column_index > 0)> + </#if>'<#if (column_index > 0)>&</#if>${column.targetColumnNameFirstLower}=' + ${column.targetColumnNameFirstLower}</#list>;
            this.exec("确定删除吗？", url, "删除成功！", "删除失败！");
        },
        exec: function (confirmMsg, url, successMsg, failMsg) {
            var self = this;
            commonNotify.confirm(confirmMsg, function() {
                $.ajax({
                    type: 'get',
                    url: appConfig.baseApiPath + url,
                    dataType: 'json',
                    success: function (response) {
                        if (response.success == true) {
                            commonNotify.success(successMsg, self.search);
                        } else {
                            commonNotify.danger(failMsg);
                        }
                    }
                })
            });
        }
    }
});
