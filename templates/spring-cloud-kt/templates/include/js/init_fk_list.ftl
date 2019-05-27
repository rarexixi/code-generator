<#list table.fkSelectColumns as column>
            <#include "/include/column/properties.ftl">
            get${propertyExceptKey}: function (${column.fkSelectColumn.valueName?uncap_first}) {
                return this.${fieldNameExceptKey}SelectList.find(item => item.${column.fkSelectColumn.valueName?uncap_first} == ${column.fkSelectColumn.valueName?uncap_first});
            },
            get${propertyExceptKey}Text: function (${column.fkSelectColumn.valueName?uncap_first}) {
                let entity = this.get${propertyExceptKey}(${column.fkSelectColumn.valueName?uncap_first});
                return entity ? entity.${column.fkSelectColumn.textName?uncap_first} : '';
            },
</#list>
<#list table.fkSelectColumns as column>
            <#include "/include/column/properties.ftl">
            init${propertyExceptKey}SelectList: function () {
                let self = this;
                let url = '/${column.fkSelectColumn.foreignTargetTableName?replace("_", "-")}/list';
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
