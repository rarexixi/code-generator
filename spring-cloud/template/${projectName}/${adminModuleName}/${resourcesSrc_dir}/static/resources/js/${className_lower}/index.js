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
        <#if !table.hasAutoIncrementUniquePrimaryKey>
        <#list primaryKey as column>
        old${column.columnFieldName}: '',
        </#list>
        </#if>
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
        pageInfo: {},
        addOrEditParams: {
            <#list table.columns as column>
            <#if column.notRequired>
            <#elseif column.fkSelect>
            ${column.columnFieldNameFirstLower}: 0<#if column_has_next>,</#if>
            <#elseif column.columnName == table.validStatusField.fieldName>
            ${column.columnFieldNameFirstLower}: ${table.validStatusField.validValue}<#if column_has_next>,</#if>
            <#elseif column.columnFieldType == "Date">
            ${column.columnFieldNameFirstLower}: ''<#if column_has_next>,</#if>
            <#else>
            ${column.columnFieldNameFirstLower}: ''<#if column_has_next>,</#if>
            </#if>
            </#list>
        },
        detail: {
            <#list table.columns as column>
            <#if column.fkSelect>
            ${column.columnFieldNameFirstLower}: 0<#if column_has_next>,</#if>
            <#elseif column.columnName == table.validStatusField.fieldName>
            ${column.columnFieldNameFirstLower}: ${table.validStatusField.validValue}<#if column_has_next>,</#if>
            <#elseif column.columnFieldType == "Date">
            ${column.columnFieldNameFirstLower}: ''<#if column_has_next>,</#if>
            <#else>
            ${column.columnFieldNameFirstLower}: ''<#if column_has_next>,</#if>
            </#if>
            </#list>
        }
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
                var that = this;
                var listLength = 0;
                if (that.pageInfo.list) {
                    listLength = that.pageInfo.list.length;
                }

                if (that.checkedList.length === listLength && listLength > 0) {
                    that.allChecked = true;
                } else {
                    that.allChecked = false;
                }
            },
            deep: true
        }
    },
    methods: {
        <#list table.columns as column>
        <#if column.fkSelect>
        init${column.columnFieldName?replace('Id', '')}: function () {
            var that = this;
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
                        that.${column.fkSelectField.foreignClass?uncap_first}List = response.result.list;
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
            var that = this;
            that.checkedList = [];
            $.ajax({
                type: 'post',
                url: '/${classNameLower}/find',
                contentType : 'application/json',
                data : JSON.stringify(that.searchParams),
                dataType: 'json',
                success: function (response) {
                    if (response.success == true) {
                        that.pageInfo = response.result;
                    } else {
                        commonNotify.danger("获取列表失败！");
                    }
                }
            });
        },
        <#if table.validStatusColumn??>
        changeValidSearch: function(valid) {
            var that = this;
            if (that.searchParams.${table.validStatusColumn.columnFieldNameFirstLower} === valid) {
                return;
            }
            that.searchParams.${table.validStatusColumn.columnFieldNameFirstLower} = valid;
            that.search();
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
            this.resetSave();
        },
        edit: function (${primaryKeyParameterValues}) {
            this.resetSave();
            var that = this;
            <#if !table.hasAutoIncrementUniquePrimaryKey>
            <#list primaryKey as column>that.old${column.columnFieldName} = ${column.columnFieldNameFirstLower};</#list>
            </#if>
            $.ajax({
                type: 'get',
                url: '/${classNameLower}/getdetail?' + <#list primaryKey as column><#if (column_index > 0)> + </#if>'<#if (column_index > 0)>&</#if>${column.columnFieldNameFirstLower}=' + ${column.columnFieldNameFirstLower}</#list>,
                dataType: 'json',
                success: function (response) {
                    if (response.success == true) {
                        <#list table.columns as column>
                        <#if column.notRequired>
                        <#else>
                        that.addOrEditParams.${column.columnFieldNameFirstLower} = response.result.${column.columnFieldNameFirstLower};
                        </#if>
                        </#list>
                    } else {
                        commonNotify.danger("获取详情失败！");
                    }
                }
            });
        },
        save: function () {
            var that = this;
            $.ajax({
                type: 'post',
                url: '/${classNameLower}/save'<#if !table.hasAutoIncrementUniquePrimaryKey> + "?"<#list primaryKey as column><#if (column_index > 0)> + "&"</#if> + "old${column.columnFieldName}=" + that.old${column.columnFieldName}</#list></#if>,
                contentType : 'application/json',
                data : JSON.stringify(that.addOrEditParams),
                dataType: 'json',
                success: function (response) {
                    if (response.success == true) {
                        commonNotify.success("操作成功！", that.search);
                    } else {
                        commonNotify.danger("操作失败！");
                    }
                }
            });
        },
        resetSave: function() {
            <#list table.columns as column>
            <#if column.notRequired>
            <#elseif column.fkSelect>
            this.addOrEditParams.${column.columnFieldNameFirstLower} = 0;
            <#elseif column.columnName == table.validStatusField.fieldName>
            this.addOrEditParams.${column.columnFieldNameFirstLower} = ${table.validStatusField.validValue};
            <#elseif column.columnFieldType == "Date">
            ${column.columnFieldNameFirstLower}: ''<#if column_has_next>,</#if>
            <#else>
            this.addOrEditParams.${column.columnFieldNameFirstLower} = '';
            </#if>
            </#list>
        },
        get: function (${primaryKeyParameterValues}) {
            var that = this;
            $.ajax({
                type: 'get',
                url: '/${classNameLower}/getdetail?' + <#list primaryKey as column><#if (column_index > 0)> + </#if>'<#if (column_index > 0)>&</#if>${column.columnFieldNameFirstLower}=' + ${column.columnFieldNameFirstLower}</#list>,
                dataType: 'json',
                success: function (response) {
                    if (response.success == true) {
                        that.detail = response.result;
                    } else {
                        commonNotify.danger("获取详情失败！");
                    }
                }
            });
        },
        <#if (table.hasPrimaryKey && table.uniquePrimaryKey??)>
        checkAll: function (moduleName) {
            var that = this;

            if (that.allChecked) {
                that.checkedList = [];
            } else {
                that.checkedList = [];
                if (that.pageInfo.list) {
                    that.pageInfo.list.forEach(function (item) {
                        that.checkedList.push(item.${table.uniquePrimaryKey.columnFieldName?uncap_first});
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
            var that = this;
            commonNotify.confirm(confirmMsg, function() {
                $.ajax({
                    type: 'post',
                    url: url,
                    contentType : 'application/json',
                    data : JSON.stringify(that.checkedList),
                    dataType: 'json',
                    success: function (response) {
                        if (response.success == true) {
                            commonNotify.success(successMsg, that.search);
                        } else {
                            commonNotify.danger(failMsg);
                        }
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
            var that = this;
            commonNotify.confirm(confirmMsg, function() {
                $.ajax({
                    type: 'get',
                    url: url,
                    dataType: 'json',
                    success: function (response) {
                        if (response.success == true) {
                            commonNotify.success(successMsg, that.search);
                        } else {
                            commonNotify.danger(failMsg);
                        }
                    }
                })
            });
        }
    }
});
