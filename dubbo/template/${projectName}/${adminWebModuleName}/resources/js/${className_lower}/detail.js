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
        this.get();
    },
    methods: {
        get: function () {
            var self = this;
            $.ajax({
                type: 'get',
                url: appConfig.baseApiPath + '/${classNameLower}/getdetail' + window.location.search,
                dataType: 'json',
                success: function (response) {
                    if (response.success == true) {
                        self.detail = response.result;
                    } else {
                        commonNotify.danger("获取详情失败！");
                    }
                }
            });
        },
        del: function () {
            var self = this;
            commonNotify.confirm("确定删除吗？", function() {
                $.ajax({
                    type: 'get',
                    url: appConfig.baseApiPath + '/${classNameLower}/delete' + window.location.search,
                    dataType: 'json',
                    success: function (response) {
                        if (response.success == true) {
                            commonNotify.success("删除成功！", self.goback);
                        } else {
                            commonNotify.danger("删除失败！");
                        }
                    }
                })
            });
        },
        goback: function() {
            window.location.href = 'list.html';
        }
    }
});
