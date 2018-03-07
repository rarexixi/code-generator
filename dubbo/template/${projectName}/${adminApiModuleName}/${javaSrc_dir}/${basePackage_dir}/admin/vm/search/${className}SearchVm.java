<#assign className = table.tableClassName>
<#assign sortCount = 0>
package ${basePackage}.admin.vm.search;

import ${basePackage}.parameter.${className}SelectParameter;
import ${baseCommonPackage}.model.SearchPage;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.*;

<#include "/include/java_copyright.ftl">
public class ${className}SearchVm extends SearchPage implements Serializable {
<#list table.columns as column>
<#if column.ignoreSearch>
<#else>
    <#assign sortCount = sortCount + 2>

    //region ${column.columnComment}
    <#if column.columnName == table.validStatusField.fieldName>
    <#assign sortCount = sortCount - 2>

    /**
    * ${column.columnComment}
    */
    private ${column.columnFieldType} ${column.columnFieldNameFirstLower};

    public void set${column.columnFieldName}(${column.columnFieldType} ${column.columnFieldNameFirstLower}) {
        this.${column.columnFieldNameFirstLower} = ${column.columnFieldNameFirstLower};
    }

    public ${column.columnFieldType} get${column.columnFieldName}() {
        return ${column.columnFieldNameFirstLower};
    }
    <#elseif (column.columnFieldType == "Integer" || column.columnFieldType == "Long" || column.columnFieldType == "Short" || column.columnFieldType == "Byte")>

    /**
    * ${column.columnComment}
    */
    private ${column.columnFieldType} ${column.columnFieldNameFirstLower};

    public void set${column.columnFieldName}(${column.columnFieldType} ${column.columnFieldNameFirstLower}) {
        this.${column.columnFieldNameFirstLower} = ${column.columnFieldNameFirstLower};
    }

    public ${column.columnFieldType} get${column.columnFieldName}() {
        return ${column.columnFieldNameFirstLower};
    }
    <#elseif (column.columnFieldType == "Date" || column.columnFieldType == "BigDecimal" || column.columnFieldType == "Double" || column.columnFieldType == "Float")>

    /**
    * <#if column.columnFieldType == "Date">开始<#else>最小</#if> ${column.columnComment}
    */
    private ${column.columnFieldType} ${column.columnFieldNameFirstLower}Min;

    /**
    * <#if column.columnFieldType == "Date">结束<#else>最大</#if> ${column.columnComment}
    */
    private ${column.columnFieldType} ${column.columnFieldNameFirstLower}Max;

    public void set${column.columnFieldName}Min(${column.columnFieldType} ${column.columnFieldNameFirstLower}Min) {
        this.${column.columnFieldNameFirstLower}Min = ${column.columnFieldNameFirstLower}Min;
    }

    public ${column.columnFieldType} get${column.columnFieldName}Min() {
        return ${column.columnFieldNameFirstLower}Min;
    }

    public void set${column.columnFieldName}Max(${column.columnFieldType} ${column.columnFieldNameFirstLower}Max) {
        this.${column.columnFieldNameFirstLower}Max = ${column.columnFieldNameFirstLower}Max;
    }

    public ${column.columnFieldType} get${column.columnFieldName}Max() {
        return ${column.columnFieldNameFirstLower}Max;
    }
    <#elseif (column.columnFieldType == "String")>

    /**
    * ${column.columnComment} (完全匹配）
    */
    private ${column.columnFieldType} ${column.columnFieldNameFirstLower}StartWith;

    public void set${column.columnFieldName}StartWith(${column.columnFieldType} ${column.columnFieldNameFirstLower}StartWith) {
        this.${column.columnFieldNameFirstLower}StartWith = ${column.columnFieldNameFirstLower}StartWith;
    }

    public ${column.columnFieldType} get${column.columnFieldName}StartWith() {
        return ${column.columnFieldNameFirstLower}StartWith;
    }
    <#else>

    /**
    * ${column.columnComment}
    */
    private ${column.columnFieldType} ${column.columnFieldNameFirstLower};

    public void set${column.columnFieldName}(${column.columnFieldType} ${column.columnFieldNameFirstLower}) {
        this.${column.columnFieldNameFirstLower} = ${column.columnFieldNameFirstLower};
    }

    public ${column.columnFieldType} get${column.columnFieldName}() {
        return ${column.columnFieldNameFirstLower};
    }
    </#if>

    //endregion
</#if>
</#list>

    private ${className}SortEnum sortEnum;

    public void setSortEnum(Integer sort) {
        this.sortEnum = ${className}SortEnum.valueOf(sort);
    }

    public Integer getSortEnum() {
        return sortEnum.getValue();
    }

    public ${className}SelectParameter get${className}SelectParameter() {

        ${className}SelectParameter parameter = new ${className}SelectParameter();
    <#list table.columns as column>
    <#if column.ignoreSearch>
    <#else>
        <#if column.columnName == table.validStatusField.fieldName>
        parameter.set${column.columnFieldName}(${column.columnFieldNameFirstLower});
        <#elseif (column.columnFieldType == "Integer" || column.columnFieldType == "Long" || column.columnFieldType == "Short" || column.columnFieldType == "Byte")>
        parameter.set${column.columnFieldName}(${column.columnFieldNameFirstLower});
        <#elseif (column.columnFieldType == "Date" || column.columnFieldType == "BigDecimal" || column.columnFieldType == "Double" || column.columnFieldType == "Float")>
        parameter.set${column.columnFieldName}Min(${column.columnFieldNameFirstLower}Min);
        parameter.set${column.columnFieldName}Max(${column.columnFieldNameFirstLower}Max);
        <#elseif (column.columnFieldType == "String")>
        parameter.set${column.columnFieldName}StartWith(${column.columnFieldNameFirstLower}StartWith);
        <#else>
        parameter.set${column.columnFieldName}(${column.columnFieldNameFirstLower});
        </#if>
    </#if>
    </#list>
        parameter.setPageIndex(pageIndex);
        parameter.setPageSize(pageSize);
        setParameterSort(parameter);

        return parameter;
    }

    private void setParameterSort(${className}SelectParameter parameter) {

        if (sortEnum == null) {
            return;
        }

        switch (sortEnum) {
        <#list table.columns as column>
        <#if (column.ignoreSearch || column.columnName == table.validStatusField.fieldName)>
        <#else>
            case ${column.columnFieldName}Asc:
                parameter.set${column.columnFieldName}Asc();
                break;
            case ${column.columnFieldName}Desc:
                parameter.set${column.columnFieldName}Desc();
                break;
        </#if>
        </#list>
            default:
                break;
        }
    }

    public enum ${className}SortEnum {

    <#assign count = 0>
    <#list table.columns as column>
    <#if (column.ignoreSearch || column.columnName == table.validStatusField.fieldName)>
    <#else>
        ${column.columnFieldName}Asc(${count + 1}),
        ${column.columnFieldName}Desc(${count + 2})<#if ((count + 2) == sortCount)>;<#else>,</#if>
        <#assign count = count + 2>
    </#if>
    </#list>

        int value;
        ${className}SortEnum(int value) {
            this.value = value;
        }
        public int getValue() {
            return value;
        }

        public static ${className}SortEnum valueOf(int i) {
            switch (i) {
            <#assign count = 0>
            <#list table.columns as column>
            <#if (column.ignoreSearch || column.columnName == table.validStatusField.fieldName)>
            <#else>
                case ${count + 1}:
                    return ${column.columnFieldName}Asc;
                case ${count + 2}:
                    return ${column.columnFieldName}Desc;
                <#assign count = count + 2>
            </#if>
            </#list>
                default:
                    return null;
            }
        }
    }
}
