<#if (table.hasUniPk)>
            <#if table.validStatusColumn??>
            enableSelected: function () {
                this.execSelected("确定启用吗？", '/${tablePath}/enable', "启用成功！", "启用失败！");
            },
            disableSelected: function () {
                this.execSelected("确定禁用吗？", '/${tablePath}/disable', "禁用成功！", "禁用失败！");
            },
            </#if>
            delSelected: function () {
                this.execSelected("确定删除吗？", '/${tablePath}/delete', "删除成功！", "删除失败！");
            },
            execSelected: function (confirmMsg, url, successMsg, failMsg) {
                let self = this;
                let params = {
                    ${table.uniPk.targetName?uncap_first}List: self.multipleSelection.map(item => item.${table.uniPk.targetName?uncap_first})
                };
                self.confirmPost(confirmMsg, url, params, successMsg, failMsg, response => self.search());
            },
</#if>