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
        <#if !table.hasAutoIncrementUniquePrimaryKey>
        <#list primaryKey as column>
        old${column.targetColumnName}: '',
        </#list>
        </#if>
        addOrEditTitle: '',
        addOrEditParams: {
            <#list table.columns as column>
            <#if column.notRequired>
            <#elseif column.fkSelect>
            ${column.targetColumnNameFirstLower}: 0<#if column_has_next>,</#if>
            <#elseif column.columnName == table.validStatusField.fieldName>
            ${column.targetColumnNameFirstLower}: ${table.validStatusField.validValue}<#if column_has_next>,</#if>
            <#elseif column.targetDataType == "Date">
            ${column.targetColumnNameFirstLower}: ''<#if column_has_next>,</#if>
            <#else>
            ${column.targetColumnNameFirstLower}: ''<#if column_has_next>,</#if>
            </#if>
            </#list>
        }
    },
    mounted: function () {
        <#list table.columns as column>
        <#if column.fkSelect>
        this.init${column.targetColumnName?replace('Id', '')}();
        </#if>
        </#list>
        <#list primaryKey as column>
        var ${column.targetColumnNameFirstLower} = commonFun.getParam('${column.targetColumnNameFirstLower}');
        </#list>
        <#if !table.hasAutoIncrementUniquePrimaryKey>
        <#list primaryKey as column>
        this.old${column.targetColumnName} = ${column.targetColumnNameFirstLower};
        </#list>
        </#if>
        if (<#list primaryKey as column><#if (column_index > 0)> && </#if>${column.targetColumnNameFirstLower} != null && ${column.targetColumnNameFirstLower} != ''</#list>) {
            this.getEditDetail();
        }

        this.addOrEditTitle = window.location.search == '' ? '添加' : '更新';
        document.title = this.addOrEditTitle + document.title;
    },
    methods: {
        <#list table.columns as column>
        <#if column.fkSelect>
        init${column.targetColumnName?replace('Id', '')}: function () {
            var self = this;
            $.ajax({
                type: 'post',
                url: appConfig.baseApiPath + '/${column.fkSelectField.foreignClass?lower_case}/search',
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
                contentType : 'application/json',
                data : JSON.stringify(self.addOrEditParams),
                dataType: 'json',
                success: function (response) {
                    if (response.success == true) {
                        commonNotify.success("操作成功！", self.cancelSave);
                    } else {
                        commonNotify.danger("操作失败！");
                    }
                }
            });
        },
        getEditDetail: function () {
            var self = this;
            $.ajax({
                type: 'get',
                url: appConfig.baseApiPath + '/${classNameLower}/getDetail' + window.location.search,
                dataType: 'json',
                success: function (response) {
                    if (response.success == true) {
                        <#list table.columns as column>
                        <#if column.notRequired>
                        <#else>
                        self.addOrEditParams.${column.targetColumnNameFirstLower} = response.result.${column.targetColumnNameFirstLower};
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
