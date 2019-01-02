<#assign className = table.tableClassName>
<#assign sortCount = 0>
package ${basePackage}.admin.vm.search;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import ${basePackage}.admin.databind.DateJsonDeserializer;
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
    <#if (column.columnName != table.validStatusField.fieldName)><#assign sortCount = sortCount + 2></#if>

    //region ${column.columnComment}
    <#if (column.columnName == table.validStatusField.fieldName || column.fkSelect || column.select)>

    /**
    * ${column.columnComment}
    */
    private ${column.targetDataType} ${column.targetColumnNameFirstLower};

    public void set${column.targetColumnName}(${column.targetDataType} ${column.targetColumnNameFirstLower}) {
        this.${column.targetColumnNameFirstLower} = ${column.targetColumnNameFirstLower};
    }

    public ${column.targetDataType} get${column.targetColumnName}() {
        return ${column.targetColumnNameFirstLower};
    }
    <#elseif (column.targetDataType == "Integer" || column.targetDataType == "Long" || column.targetDataType == "Short" || column.targetDataType == "Byte")>

    /**
    * ${column.columnComment}
    */
    private ${column.targetDataType} ${column.targetColumnNameFirstLower};

    public void set${column.targetColumnName}(${column.targetDataType} ${column.targetColumnNameFirstLower}) {
        this.${column.targetColumnNameFirstLower} = ${column.targetColumnNameFirstLower};
    }

    public ${column.targetDataType} get${column.targetColumnName}() {
        return ${column.targetColumnNameFirstLower};
    }
    <#elseif (column.targetDataType == "Date")>

    /**
    * 开始 ${column.columnComment}
    */
    @JsonDeserialize(using = DateJsonDeserializer.class)
    private ${column.targetDataType} ${column.targetColumnNameFirstLower}Min;

    /**
    * 结束 ${column.columnComment}
    */
    @JsonDeserialize(using = DateJsonDeserializer.class)
    private ${column.targetDataType} ${column.targetColumnNameFirstLower}Max;

    public void set${column.targetColumnName}Min(${column.targetDataType} ${column.targetColumnNameFirstLower}Min) {
        this.${column.targetColumnNameFirstLower}Min = ${column.targetColumnNameFirstLower}Min;
    }

    public ${column.targetDataType} get${column.targetColumnName}Min() {
        return ${column.targetColumnNameFirstLower}Min;
    }

    public void set${column.targetColumnName}Max(${column.targetDataType} ${column.targetColumnNameFirstLower}Max) {
        this.${column.targetColumnNameFirstLower}Max = ${column.targetColumnNameFirstLower}Max;
    }

    public ${column.targetDataType} get${column.targetColumnName}Max() {
        return ${column.targetColumnNameFirstLower}Max;
    }
    <#elseif (column.targetDataType == "BigDecimal" || column.targetDataType == "Double" || column.targetDataType == "Float")>

    /**
    * 最小 ${column.columnComment}
    */
    private ${column.targetDataType} ${column.targetColumnNameFirstLower}Min;

    /**
    * 最大 ${column.columnComment}
    */
    private ${column.targetDataType} ${column.targetColumnNameFirstLower}Max;

    public void set${column.targetColumnName}Min(${column.targetDataType} ${column.targetColumnNameFirstLower}Min) {
        this.${column.targetColumnNameFirstLower}Min = ${column.targetColumnNameFirstLower}Min;
    }

    public ${column.targetDataType} get${column.targetColumnName}Min() {
        return ${column.targetColumnNameFirstLower}Min;
    }

    public void set${column.targetColumnName}Max(${column.targetDataType} ${column.targetColumnNameFirstLower}Max) {
        this.${column.targetColumnNameFirstLower}Max = ${column.targetColumnNameFirstLower}Max;
    }

    public ${column.targetDataType} get${column.targetColumnName}Max() {
        return ${column.targetColumnNameFirstLower}Max;
    }
    <#elseif (column.targetDataType == "String")>

    /**
    * ${column.columnComment} (完全匹配）
    */
    private ${column.targetDataType} ${column.targetColumnNameFirstLower}StartWith;

    public void set${column.targetColumnName}StartWith(${column.targetDataType} ${column.targetColumnNameFirstLower}StartWith) {
        this.${column.targetColumnNameFirstLower}StartWith = ${column.targetColumnNameFirstLower}StartWith;
    }

    public ${column.targetDataType} get${column.targetColumnName}StartWith() {
        return ${column.targetColumnNameFirstLower}StartWith;
    }
    <#else>

    /**
    * ${column.columnComment}
    */
    private ${column.targetDataType} ${column.targetColumnNameFirstLower};

    public void set${column.targetColumnName}(${column.targetDataType} ${column.targetColumnNameFirstLower}) {
        this.${column.targetColumnNameFirstLower} = ${column.targetColumnNameFirstLower};
    }

    public ${column.targetDataType} get${column.targetColumnName}() {
        return ${column.targetColumnNameFirstLower};
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
        <#if (column.columnName == table.validStatusField.fieldName || column.fkSelect || column.select)>
        parameter.set${column.targetColumnName}(${column.targetColumnNameFirstLower});
        <#elseif (column.targetDataType == "Integer" || column.targetDataType == "Long" || column.targetDataType == "Short" || column.targetDataType == "Byte")>
        parameter.set${column.targetColumnName}(${column.targetColumnNameFirstLower});
        <#elseif (column.targetDataType == "Date" || column.targetDataType == "BigDecimal" || column.targetDataType == "Double" || column.targetDataType == "Float")>
        parameter.set${column.targetColumnName}Min(${column.targetColumnNameFirstLower}Min);
        parameter.set${column.targetColumnName}Max(${column.targetColumnNameFirstLower}Max);
        <#elseif (column.targetDataType == "String")>
        parameter.set${column.targetColumnName}StartWith(${column.targetColumnNameFirstLower}StartWith);
        <#else>
        parameter.set${column.targetColumnName}(${column.targetColumnNameFirstLower});
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
            case ${column.targetColumnName}Asc:
                parameter.set${column.targetColumnName}Asc();
                break;
            case ${column.targetColumnName}Desc:
                parameter.set${column.targetColumnName}Desc();
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
        ${column.targetColumnName}Asc(${count + 1}),
        ${column.targetColumnName}Desc(${count + 2})<#if ((count + 2) == sortCount)>;<#else>,</#if>
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
                    return ${column.targetColumnName}Asc;
                case ${count + 2}:
                    return ${column.targetColumnName}Desc;
                <#assign count = count + 2>
            </#if>
            </#list>
                default:
                    return null;
            }
        }
    }
}
