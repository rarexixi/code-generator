<#if table.validStatusColumn??>
        enable: function (item) {
            var self = this;
            var params = self.getPkParams(item);
            var url = appConfig.baseApiPath + '/${classNameFirstLower}/enable';
            this.exec("确定启用吗？", url, params, "启用成功！", "启用失败！");
        },
        disable: function (item) {
            var self = this;
            var params = self.getPkParams(item);
            var url = appConfig.baseApiPath + '/${classNameFirstLower}/disable';
            this.exec("确定禁用吗？", url, params, "禁用成功！", "禁用失败！");
        },
</#if>
        del: function (item) {
            var self = this;
            var params = self.getPkParams(item);
            var url = appConfig.baseApiPath + '/${classNameFirstLower}/delete';
            this.exec("确定删除吗？", url, params, "删除成功！", "删除失败！");
        },
        getPkParams: function (item) {
            var params = {
<#list pks as column>
                <#include "/include/column/properties.ftl">
                ${fieldName}: item.${fieldName}<#if (column?has_next)>,</#if>
</#list>
            };
            return params;
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
<#if (!isSplit)>
                    self.closeDialog();
</#if>
                    self.search();
                });
            }, function () {
            });
        },
