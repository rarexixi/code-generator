<#if (table.validStatusColumn??)>
            get${table.validStatusColumn.targetName}Text: function (value) {
                let self = this;
                let entity = self.${table.validStatusColumn.targetName?uncap_first}SelectList.find(item => item.value == value);
                return entity ? entity.text : '';
            },
</#if>
<#list table.selectColumns as column>
            <#include "/include/column/properties.ftl">
            get${propertyExceptKey}Text: function (value) {
                let self = this;
                let entity = self.${fieldNameExceptKey}SelectList.find(item => item.value == value);
                return entity ? entity.text : '';
            },
</#list>