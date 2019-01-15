<#include "/include/table/properties.ftl">
<#macro mapperEl value>${r"${"}${value}}</#macro>
var app = new Vue({
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
            ${fieldName}: <#if (column.dataType?ends_with("char"))>''<#else>0</#if><#if column?has_next>,</#if>
            <#else>
            ${fieldName}: ''<#if column?has_next>,</#if>
            </#if>
            </#list>
        }
    },
    mounted: function () {
        var self = this;

        <#list table.pks as column>
        <#include "/include/column/properties.ftl">
        self.pkParams.${fieldName} = commonFun.getParam('${fieldName}');
        </#list>

        self.get();
    },
    methods: {
        get: function () {
            var self = this;
            var url = appConfig.baseApiPath + '/${classNameFirstLower}/getDetail';
            self.ajaxGet(url, self.pkParams, '获取详情失败！', function (response) {
                self.detail = response.result;
            });
        },
        edit: function () {
            location.href = 'addoredit.html' + location.search;
        },
        <#if table.validStatusColumn??>
        enable: function () {
            var self = this;
            var url = appConfig.baseApiPath + '/${classNameFirstLower}/enable';
            this.exec("确定启用吗？", url, self.pkParams, "启用成功！", "启用失败！");
        },
        disable: function () {
            var self = this;
            var url = appConfig.baseApiPath + '/${classNameFirstLower}/disable';
            this.exec("确定禁用吗？", url, self.pkParams, "禁用成功！", "禁用失败！");
        },
        </#if>
        del: function () {
            var self = this;
            var url = appConfig.baseApiPath + '/${classNameFirstLower}/delete';
            this.exec("确定删除吗？", url, self.pkParams, "删除成功！", "删除失败！");
        },
        exec: function (confirmMsg, url, params, successMsg, failMsg) {
            var self = this;
            self.$confirm(confirmMsg, '', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning'
            }).then(function () {
                self.ajaxPost(url, params, failMsg, function (response) {
                    self.$notify({
                        type: 'success',
                        message: successMsg
                    });
                    self.get();
                });
            }, function () {
            });
        },
        <#include "/include/js/select_get_text.ftl">
        back: function () {
            var self = this;
            location.href = 'list.html';
        }
    }
});
