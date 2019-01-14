<#if (table.hasUniPk)>
        <#if table.validStatusColumn??>
        enableSelected: function () {
            this.execSelected("确定启用吗？", appConfig.baseApiPath + '/${classNameFirstLower}/enable', "启用成功！", "启用失败！");
        },
        disableSelected: function () {
            this.execSelected("确定禁用吗？", appConfig.baseApiPath + '/${classNameFirstLower}/disable', "禁用成功！", "禁用失败！");
        },
        </#if>
        delSelected: function () {
            this.execSelected("确定删除吗？", appConfig.baseApiPath + '/${classNameFirstLower}/delete', "删除成功！", "删除失败！");
        },
        execSelected: function (confirmMsg, url, successMsg, failMsg) {
            var self = this;
            var checkedList = [];
            for (var i = 0; i < self.multipleSelection.length; i++) {
                var item = self.multipleSelection[i];
                checkedList.push(item.${table.uniPk.targetName?uncap_first});
            }

            var params = {
                ${table.uniPk.targetName?uncap_first}List: checkedList
            };
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
                    self.search();
                });
            }, function () {
            });
        },
</#if>