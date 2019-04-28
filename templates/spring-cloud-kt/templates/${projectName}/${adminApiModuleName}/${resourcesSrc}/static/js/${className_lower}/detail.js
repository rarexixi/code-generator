<#include "/include/table/properties.ftl">
<#macro mapperEl value>${r"${"}${value}}</#macro>
let app = new Vue({
    el: '#app',
    data: {
        <#include "/include/js/data_select_list.ftl">
        pkParams: {
            <#list pks as column>
            <#include "/include/column/properties.ftl">
            ${fieldName}: ''<#if column?has_next>,</#if>
            </#list>
        },
        detail: {
            <#list table.columns as column>
            <#include "/include/column/properties.ftl">
            <#if (column.validStatus)>
            ${fieldName}: ${table.validStatusColumn.validStatusOption.valid}<#if column?has_next>,</#if>
            <#elseif (column.fkSelect || column.select)>
            ${fieldName}: <#if (isString)>''<#else>0</#if><#if column?has_next>,</#if>
            <#else>
            ${fieldName}: ''<#if column?has_next>,</#if>
            </#if>
            </#list>
        }
    },
    mounted: function () {
        let self = this;

        <#list table.pks as column>
        <#include "/include/column/properties.ftl">
        self.pkParams.${fieldName} = commonFun.getParam('${fieldName}');
        </#list>

        self.get();
    },
    methods: {
        get: function () {
            let self = this;
            let url = appConfig.baseApiPath + '/${tablePath}/detail';
            self.ajaxGet(url, self.pkParams, '获取详情失败！', response => {
                self.detail = response.result;
            });
        },
        edit: function () {
            location.href = 'addoredit.html' + location.search;
        },
        <#if table.validStatusColumn??>
        enable: function () {
            let self = this;
            let url = appConfig.baseApiPath + '/${tablePath}/enable';
            self.confirmPost("确定启用吗？", url, self.pkParams, "启用成功！", "启用失败！", response => self.get());
        },
        disable: function () {
            let self = this;
            let url = appConfig.baseApiPath + '/${tablePath}/disable';
            self.confirmPost("确定禁用吗？", url, self.pkParams, "禁用成功！", "禁用失败！", response => self.get());
        },
        </#if>
        del: function () {
            let self = this;
            let url = appConfig.baseApiPath + '/${tablePath}/delete';
            self.confirmPost("确定删除吗？", url, self.pkParams, "删除成功！", "删除失败！", response => back.get());
        },
        <#include "/include/js/select_get_text.ftl">
        back: function () {
            let self = this;
            location.href = 'list.html';
        }
    }
});
