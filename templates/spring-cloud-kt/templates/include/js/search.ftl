
            changePage: function (pageIndex) {
                let self = this;
                if (self.searchPage.pageIndex == pageIndex) {
                    return;
                }
                self.searchPage.pageIndex = pageIndex;
                self.search();
            },
            changePageSize: function (pageSize) {
                let self = this;
                if (self.searchPage.pageSize == pageSize) {
                    return;
                }
                self.searchPage.pageSize = pageSize;
                self.searchPage.pageIndex = 1;
                self.search();
            },
            handleSortChange: function ({column, prop, order}) {
                let self = this;
                self.sortParams = {};
                if (prop) {
                    self.sortParams[prop + "Sort"] = order == 'ascending' ? sortEnum.ASC : sortEnum.DESC;
                }
                self.search();
            },
            search: function () {
                let self = this;
                self.checkedList = [];

                let params = {
                    pageSize: self.searchPage.pageSize,
                    pageIndex: self.searchPage.pageIndex,
                    condition: self.searchParams,
                    order: self.sortParams
                };

                let url = '/${tablePath}/page-list';
                self.ajaxPost(url, params, '获取${tableComment}列表失败！', response => {
                    self.pageInfo = response.result;
                });
            },
            resetSearch: function () {
                let self = this;
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

                self.searchPage.pageIndex = searchPage.defaultPageIndex;
                self.searchPage.pageSize = searchPage.defaultPageSize;
            },
<#if table.validStatusColumn??>
            changeValidSearch: function (valid) {
                let self = this;
                if (self.searchParams.${table.validStatusColumn.targetName?uncap_first} === valid) {
                    return;
                }
                self.resetSearch();
                self.searchParams.${table.validStatusColumn.targetName?uncap_first} = valid;
                self.search();
            },
</#if>