<#if table.validStatusColumn??>
            enable: function (item) {
                let self = this;
                let params = self.getPkParams(item);
                let url = '/${tablePath}/enable';
                self.confirmPost("确定启用吗？", url, params, "启用成功！", "启用失败！", response => {
                    <#if (!isSplit)>self.closeDialog();</#if>
                    self.search();
                });
            },
            disable: function (item) {
                let self = this;
                let params = self.getPkParams(item);
                let url = '/${tablePath}/disable';
                self.confirmPost("确定禁用吗？", url, params, "禁用成功！", "禁用失败！", response => {
                    <#if (!isSplit)>self.closeDialog();</#if>
                    self.search();
                });
            },
</#if>
            del: function (item) {
                let self = this;
                let params = self.getPkParams(item);
                let url = '/${tablePath}/delete';
                self.confirmPost("确定删除吗？", url, params, "删除成功！", "删除失败！", response => {
                    <#if (!isSplit)>self.closeDialog();</#if>
                    self.search();
                });
            },
            getPkParams: function (item) {
                return {
<#list pks as column>
                <#include "/include/column/properties.ftl">
                    ${fieldName}: item.${fieldName}<#if (column?has_next)>,</#if>
</#list>
                };
            },
