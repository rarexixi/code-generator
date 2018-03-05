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
            //${column.columnFieldNameFirstLower}Min: '',
            //${column.columnFieldNameFirstLower}Max: '',
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
    methods: {
        <#list table.columns as column>
        <#if column.fkSelect>
        init${column.columnFieldName?replace('Id', '')}: function () {
            var that = this;
            $.ajax({
                type: 'get',
                url: '/${column.fkSelectField.foreignClass?lower_case}/find',
                data: {
                    pageIndex: 1,
                    pageSize: 1000
                },
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
            $.ajax({
                type: 'get',
                url: '/${classNameLower}/find',
                data: this.searchParams,
                dataType: 'json',
                success: function (response) {
                    if (response.success == true) {
                        that.pageInfo = response.result;
                    } else {
                        commonNotify.danger("获取列表！");
                    }
                }
            });
        },
        resetSearch: function() {
            <#list table.columns as column>
            <#if column.ignoreSearch>
            <#elseif column.columnName == table.validStatusField.fieldName>
            this.searchParams.${column.columnFieldNameFirstLower} = '';
            <#elseif (column.columnFieldType == "Integer" || column.columnFieldType == "Long" || column.columnFieldType == "Short" || column.columnFieldType == "Byte")>
            this.searchParams.${column.columnFieldNameFirstLower} = '';
            <#elseif column.columnFieldType == "Date">
            //this.searchParams.${column.columnFieldNameFirstLower}Min = '';
            //this.searchParams.${column.columnFieldNameFirstLower}Max = '';
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
        del: function (${primaryKeyParameterValues}) {
            var that = this;
            commonNotify.confirm("确定删除吗？", function() {
                $.ajax({
                    type: 'get',
                    url: '/${classNameLower}/delete?' + <#list primaryKey as column><#if (column_index > 0)> + </#if>'<#if (column_index > 0)>&</#if>${column.columnFieldNameFirstLower}=' + ${column.columnFieldNameFirstLower}</#list>,
                    dataType: 'json',
                    success: function (response) {
                        if (response.success == true) {
                            commonNotify.success("删除成功！", that.search);
                        } else {
                            commonNotify.danger("删除失败！");
                        }
                    }
                });
            });
        }
    }
});
