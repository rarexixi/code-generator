
        changePage: function (pageIndex) {
            var self = this;
            if (self.searchPage.pageIndex == pageIndex) {
                return;
            }
            self.searchPage.pageIndex = pageIndex;
            self.search();
        },
        changePageSize: function (pageSize) {
            var self = this;
            if (self.searchPage.pageSize == pageSize) {
                return;
            }
            self.searchPage.pageSize = pageSize;
            self.searchPage.pageIndex = 1;
            self.search();
        },
        search: function () {
            var self = this;
            self.checkedList = [];

            var params = {
                pageSize: self.searchPage.pageSize,
                pageIndex: self.searchPage.pageIndex,
                condition: self.searchParams,
                order: {}
            };

            var url = appConfig.baseApiPath + '/${classNameFirstLower}/getPageInfo';
            self.ajaxPost(url, params, '获取${tableComment}列表失败！', function (response) {
                self.pageInfo = response.result;
            });
        },
        resetSearch: function () {
            var self = this;
<#if (table.validStatusColumn??)>
            // self.searchParams.${table.validStatusColumn.targetName?uncap_first} = 'null';
</#if>
<#list table.indexes as column>
            <#include "/include/column/properties.ftl">
            <#if (column.validStatus)>
            <#elseif (column.select || column.fkSelect || column.pk || isInteger)>
            self.searchParams.${fieldName} = '';
            <#elseif (isDecimal)>
            self.searchParams.${fieldName}Min = '';
            self.searchParams.${fieldName}Max = '';
            <#elseif (isDate || isTime || isDateTime)>
            self.searchParams.${fieldName}Range = [];
            <#elseif (isContent)>
            <#elseif (isString)>
            self.searchParams.${fieldName}StartWith = '';
            <#else>
            </#if>
</#list>

            self.searchPage.pageIndex = 1;
            self.searchPage.pageSize = 10;
        },
<#if table.validStatusColumn??>
        changeValidSearch: function (valid) {
            var self = this;
            if (self.searchParams.${table.validStatusColumn.targetName?uncap_first} === valid) {
                return;
            }
            self.resetSearch();
            self.searchParams.${table.validStatusColumn.targetName?uncap_first} = valid;
            self.search();
        },
</#if>