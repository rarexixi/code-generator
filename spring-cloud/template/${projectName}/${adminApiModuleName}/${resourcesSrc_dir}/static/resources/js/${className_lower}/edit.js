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
        }
    },
    mounted: function () {
        <#list table.columns as column>
        <#if column.fkSelect>
        this.init${column.targetColumnName?replace('Id', '')}();
        </#if>
        </#list>
        <#list primaryKey as column>
        <#assign fieldName = column.targetColumnName?uncap_first>
        var ${fieldName} = commonFun.getParam('${fieldName}');
        </#list>
        <#if !table.hasAutoIncrementUniquePrimaryKey>
        <#list primaryKey as column>
        <#assign fieldName = column.targetColumnName?uncap_first>
        this.old${column.targetColumnName} = ${fieldName};
        </#list>
        </#if>
        if (<#list primaryKey as column><#assign fieldName = column.targetColumnName?uncap_first><#if (column_index > 0)> && </#if>${fieldName} != null && ${fieldName} != ''</#list>) {
            this.getEditDetail();
        }

        this.addOrEditTitle = window.location.search == '' ? '添加' : '更新';
        document.title = this.addOrEditTitle + document.title;
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
                contentType : 'application/json',
                data : JSON.stringify({
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
        save: function () {
            var self = this;
            var ajaxUrl;
            <#if table.hasAutoIncrementUniquePrimaryKey>
            if (<#list primaryKey as column><#if (column_index > 0)> && </#if>self.addOrEditParams.${column.targetColumnName?uncap_first} == ''</#list>) {
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
                        self.$message({
                            message: '操作成功！',
                            type: 'success'
                        });
                        setTimeout(self.cancelSave, 1000);
                    } else {
                        self.$message({
                            message: '操作失败！',
                            type: 'error'
                        });
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
        cancelSave: function() {
            window.location.href = 'list.html';
        }
    }
});
