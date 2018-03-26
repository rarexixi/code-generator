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
        ${column.fkSelectField.foreignClass?uncap_first}List: [],
        </#if>
        </#list>
        searchParams: {
            <#list table.columns as column>
            <#if column.ignoreSearch>
            <#elseif column.columnName == table.validStatusField.fieldName>
            ${column.columnFieldNameFirstLower}: '',
            <#elseif (column.columnFieldType == "Integer" || column.columnFieldType == "Long" || column.columnFieldType == "Short" || column.columnFieldType == "Byte")>
            ${column.columnFieldNameFirstLower}: '',
            <#elseif column.columnFieldType == "Date">
            ${column.columnFieldNameFirstLower}Min: '',
            ${column.columnFieldNameFirstLower}Max: '',
            <#elseif (column.columnFieldType == "BigDecimal" || column.columnFieldType == "Double" || column.columnFieldType == "Float")>
            ${column.columnFieldNameFirstLower}Min: '',
            ${column.columnFieldNameFirstLower}Max: '',
            <#elseif (column.columnFieldType == "String")>
            ${column.columnFieldNameFirstLower}StartWith: '',
            <#else>
            ${column.columnFieldNameFirstLower}: '',
            </#if>
            </#list>
            pageIndex: 1,
            pageSize: 10
        },
        checkedList: [],
        allChecked: false,
        pageInfo: {}
    },
    mounted: function () {
        this.search();
        <#list table.columns as column>
        <#if column.fkSelect>
        this.init${column.columnFieldName?replace('Id', '')}();
        </#if>
        </#list>
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
        init${column.columnFieldName?replace('Id', '')}: function () {
            var self = this;
            $.ajax({
                type: 'post',
                url: '/${column.fkSelectField.foreignClass?lower_case}/find',
                contentType : 'application/json',
                data : JSON.stringify({
                    pageIndex: 1,
                    pageSize: 1000
                }),
                dataType: 'json',
                success: function (response) {
                    if (response.success == true) {
                        self.${column.fkSelectField.foreignClass?uncap_first}List = response.result.list;
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
                url: '/${classNameLower}/find',
                contentType : 'application/json',
                data : JSON.stringify(self.searchParams),
                dataType: 'json',
                success: function (response) {
                    if (response.success == true) {
                        self.pageInfo = response.result;
                    } else {
                        commonNotify.danger("获取列表！");
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
            self.searchParams.${table.validStatusColumn.columnFieldNameFirstLower} = valid;
            self.search();
        },
        </#if>
        resetSearch: function() {
            <#list table.columns as column>
            <#if column.ignoreSearch>
            <#elseif column.columnName == table.validStatusField.fieldName>
            this.searchParams.${column.columnFieldNameFirstLower} = '';
            <#elseif (column.columnFieldType == "Integer" || column.columnFieldType == "Long" || column.columnFieldType == "Short" || column.columnFieldType == "Byte")>
            this.searchParams.${column.columnFieldNameFirstLower} = '';
            <#elseif column.columnFieldType == "Date">
            this.searchParams.${column.columnFieldNameFirstLower}Min = '';
            this.searchParams.${column.columnFieldNameFirstLower}Max = '';
            <#elseif (column.columnFieldType == "BigDecimal" || column.columnFieldType == "Double" || column.columnFieldType == "Float")>
            this.searchParams.${column.columnFieldNameFirstLower}Min = '';
            this.searchParams.${column.columnFieldNameFirstLower}Max = '';
            <#elseif (column.columnFieldType == "String")>
            this.searchParams.${column.columnFieldNameFirstLower}StartWith = '';
            <#else>
            this.searchParams.${column.columnFieldNameFirstLower} = '';
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
            var url = 'addoredit.html?' + <#list primaryKey as column><#if (column_index > 0)> + </#if>'<#if (column_index > 0)>&</#if>${column.columnFieldNameFirstLower}=' + ${column.columnFieldNameFirstLower}</#list>;
            window.location.href = url;
        },
        get: function (${primaryKeyParameterValues}) {
            var url = 'detail.html?' + <#list primaryKey as column><#if (column_index > 0)> + </#if>'<#if (column_index > 0)>&</#if>${column.columnFieldNameFirstLower}=' + ${column.columnFieldNameFirstLower}</#list>;
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
                        self.checkedList.push(item.${table.uniquePrimaryKey.columnFieldName?uncap_first});
                    });
                }
            }
        },
        <#if table.validStatusColumn??>
        enableSelected: function () {
            this.execSelected("确定激活吗？", '/${classNameLower}/enablelist', "激活成功！", "激活失败！");
        },
        disableSelected: function () {
            this.execSelected("确定冻结吗？", '/${classNameLower}/disablelist', "冻结成功！", "冻结失败！");
        },
        </#if>
        delSelected: function () {
            this.execSelected("确定删除吗？", '/${classNameLower}/deletelist', "删除成功！", "删除失败！");
        },
        execSelected: function (confirmMsg, url, successMsg, failMsg) {
            var self = this;
            commonNotify.confirm(confirmMsg, function() {
                $.ajax({
                    type: 'post',
                    url: url,
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
            var url = '/${classNameLower}/enable?' + <#list primaryKey as column><#if (column_index > 0)> + </#if>'<#if (column_index > 0)>&</#if>${column.columnFieldNameFirstLower}=' + ${column.columnFieldNameFirstLower}</#list>;
            this.exec("确定激活吗？", url, "激活成功！", "激活失败！");
        },
        disable: function (${primaryKeyParameterValues}) {
            var url = '/${classNameLower}/disable?' + <#list primaryKey as column><#if (column_index > 0)> + </#if>'<#if (column_index > 0)>&</#if>${column.columnFieldNameFirstLower}=' + ${column.columnFieldNameFirstLower}</#list>;
            this.exec("确定冻结吗？", url, "冻结成功！", "冻结失败！");
        },
        </#if>
        del: function (${primaryKeyParameterValues}) {
            var url = '/${classNameLower}/delete?' + <#list primaryKey as column><#if (column_index > 0)> + </#if>'<#if (column_index > 0)>&</#if>${column.columnFieldNameFirstLower}=' + ${column.columnFieldNameFirstLower}</#list>;
            this.exec("确定删除吗？", url, "删除成功！", "删除失败！");
        },
        exec: function (confirmMsg, url, successMsg, failMsg) {
            var self = this;
            commonNotify.confirm(confirmMsg, function() {
                $.ajax({
                    type: 'get',
                    url: url,
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
