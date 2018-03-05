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
        addOrEditParams: {
            <#list table.columns as column>
            <#if column.notRequired>
            <#elseif column.fkSelect>
            ${column.columnFieldNameFirstLower}: 0<#if column_has_next>,</#if>
            <#elseif column.columnName == table.validStatusField.fieldName>
            ${column.columnFieldNameFirstLower}: ${table.validStatusField.validValue}<#if column_has_next>,</#if>
            <#elseif column.columnFieldType == "Date">
            //${column.columnFieldNameFirstLower}: ''<#if column_has_next>,</#if>
            <#else>
            ${column.columnFieldNameFirstLower}: ''<#if column_has_next>,</#if>
            </#if>
            </#list>
        }
    },
    mounted: function () {
        <#list table.columns as column>
        <#if column.fkSelect>
        this.init${column.columnFieldName?replace('Id', '')}();
        </#if>
        </#list>
        <#list primaryKey as column>
        var ${column.columnFieldNameFirstLower} = commonFun.getParam('${column.columnFieldNameFirstLower}');
        </#list>
        <#if !table.hasAutoIncrementUniquePrimaryKey>
        <#list primaryKey as column>this.old${column.columnFieldName} = ${column.columnFieldNameFirstLower};</#list>
        </#if>
        if (<#list primaryKey as column><#if (column_index > 0)> && </#if>${column.columnFieldNameFirstLower} != null && ${column.columnFieldNameFirstLower} != ''</#list>) {
            this.getEditDetail();
        }
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
        save: function () {
            var that = this;
            $.ajax({
                type: 'post',
                url: '/${classNameLower}/save'<#if !table.hasAutoIncrementUniquePrimaryKey> + "?"<#list primaryKey as column><#if (column_index > 0)> + "&"</#if> + "old${column.columnFieldName}=" + that.old${column.columnFieldName}</#list></#if>,
                data: this.addOrEditParams,
                dataType: 'json',
                success: function (response) {
                    if (response.success == true) {
                        commonNotify.success("操作成功！", that.cancelSave);
                    } else {
                        commonNotify.danger("操作失败！");
                    }
                }
            });
        },
        getEditDetail: function () {
            var that = this;
            $.ajax({
                type: 'get',
                url: '/${classNameLower}/getdetail' + window.location.search,
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
        cancelSave: function() {
            window.location.href = 'list.html';
        }
    }
});
