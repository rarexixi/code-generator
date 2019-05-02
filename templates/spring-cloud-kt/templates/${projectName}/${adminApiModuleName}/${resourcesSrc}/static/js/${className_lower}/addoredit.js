<#include "/include/table/properties.ftl">
<#macro mapperEl value>${r"${"}${value}}</#macro>
let app = new Vue({
    el: '#app',
    data: {
        <#include "/include/js/data_select_list.ftl">
        <#list table.fkSelectColumns as column>
        <#include "/include/column/properties.ftl">
        ${fieldNameExceptKey}SelectList: [],
        </#list>
        pkParams: {
            <#list pks as column>
            <#include "/include/column/properties.ftl">
            ${fieldName}: ''<#if column?has_next>,</#if>
            </#list>
        },
        addOrEditParams: {
            <#list table.requiredColumns as column>
            <#include "/include/column/properties.ftl">
            <#if (column.validStatus)>
            <#-- ${fieldName}: ${table.validStatusColumn.validStatusOption.valid}<#if column?has_next>,</#if> -->
            <#else>
            ${fieldName}: ''<#if column?has_next>,</#if>
            </#if>
            </#list>
        },
        addOrEditTitle: ''
    },
    mounted: function () {
        let self = this;
        <#list table.fkSelectColumns as column>
        <#include "/include/column/properties.ftl">
        self.init${propertyExceptKey}();
        </#list>
        if (location.search == '') {
            self.addOrEditTitle = '添加${tableComment}';
        } else {
            self.addOrEditTitle = '更新${tableComment}';

            <#list table.pks as column>
            <#include "/include/column/properties.ftl">
            self.pkParams.${fieldName} = commonFun.getParam('${fieldName}');
            </#list>
            self.get();
        }
        document.title = self.addOrEditTitle;
    },
    methods: {
        save: function () {
            let self = this;
            let ajaxUrl;
            <#if table.hasAutoIncUniPk>
            if (<#list pks as column><#include "/include/column/properties.ftl"><#if (column_index > 0)> && </#if>self.addOrEditParams.${fieldName} == ''</#list>) {
                ajaxUrl = appConfig.baseApiPath + '/${tablePath}/add';
            } else {
                ajaxUrl = appConfig.baseApiPath + '/${tablePath}/update';
            }
            <#else>
            if (<#list pks as column><#include "/include/column/properties.ftl"><#if (column_index > 0)> && </#if>self.${fieldName} == ''</#list>) {
                ajaxUrl = appConfig.baseApiPath + '/${tablePath}/add';
            } else {
                ajaxUrl = appConfig.baseApiPath + '/${tablePath}/update?'<#list pks as column><#include "/include/column/properties.ftl"><#if (column_index > 0)> + '&'</#if> + '${fieldName}=' + self.${fieldName}</#list>;
            }
            </#if>

            self.ajaxPost(ajaxUrl, self.addOrEditParams, '操作失败！', response => {
                self.$notify({
                    message: '操作成功！',
                    type: 'success'
                });
            });
        },
        get: function () {
            let self = this;

            let url = appConfig.baseApiPath + '/${tablePath}/detail';
            let params = self.pkParams;
            self.ajaxGet(url, params, '获取详情失败！', response => {
                <#list table.columns as column>
                <#include "/include/column/properties.ftl">
                <#if column.notRequired>
                <#else>
                self.addOrEditParams.${fieldName} = response.result.${fieldName};
                </#if>
                </#list>
            });
        },
        <#list table.fkSelectColumns as column>
        <#include "/include/column/properties.ftl">
        init${propertyExceptKey}: function () {
            let self = this;
            let url = appConfig.baseApiPath + '/${column.fkSelectColumn.foreignTargetTableName?replace("_", "-")}/list';
            let params = {
                condition: {
                    <#list column.fkSelectColumn.conditions as condition>
                    ${condition.field}: '${condition.value}'<#if condition?has_next>,</#if>
                    </#list>
                },
                order: {}
            };
            self.ajaxPost(url, params, '获取${columnComment}列表失败！', response => {
                self.${fieldNameExceptKey}SelectList = response.result;
            });
        },
        </#list>
        back: function () {
            let self = this;
            location.href = 'list.html';
        }
    }
});
