<#if table.validStatusColumn??>
            enable: function (item) {
                this.exec(item, '/${tablePath}/enable', '确定启用吗？', '启用成功！', '启用失败！');
            },
            disable: function (item) {
                this.exec(item, '/${tablePath}/disable', '确定禁用吗？', '禁用成功！', '禁用失败！');
            },
</#if>
            del: function (item) {
                this.exec(item, '/${tablePath}/delete', '确定删除吗？', '删除成功！', '删除失败！');
            },
            exec: function (item, url, confirmMsg, successMsg, failMsg) {
                let self = this;
                let params = self.getPkParams(item);
                self.confirmPost(confirmMsg, url, params, successMsg, failMsg, response => {
                    self.closeDialog();
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
