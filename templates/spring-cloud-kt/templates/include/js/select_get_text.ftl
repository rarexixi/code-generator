<#if (table.validStatusColumn??)>
        get${table.validStatusColumn.targetName}Text: function (value) {
            var self = this;
            var entity = self.${table.validStatusColumn.targetName?uncap_first}SelectList.find(function (item) {
                return item.value == value;
            });
            return entity ? entity.text : '';
        },
</#if>
<#list table.selectColumns as column>
        <#include "/include/column/properties.ftl">
        get${propertyName}Text: function (value) {
            var self = this;
            var entity = self.${fieldNameExceptKey}SelectList.find(function (item) {
                return item.value == value;
            });
            return entity ? entity.text : '';
        },
</#list>