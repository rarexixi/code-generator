<#include "/include/table/properties.ftl">
<#assign sortCount = 0>
package ${basePackage}.admin.vm.search;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import ${basePackage}.admin.databind.DateJsonDeserializer;
import ${basePackage}.condition.extension.${className}ConditionExtension;
import ${baseCommonPackage}.model.SearchPage;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.*;

<#include "/include/java_copyright.ftl">
public class ${className}SearchVm implements Serializable {
<#list table.columns as column>
<#include "/include/column/properties.ftl">
<#if column.ignoreSearch>
<#else>
    <#if (column.columnName != table.validStatusField.fieldName)><#assign sortCount = sortCount + 2></#if>

    //region ${column.columnComment}
    <#if (column.columnName == table.validStatusColumn.columnName || column.fkSelect || column.select)>

    /**
    * ${column.columnComment}
    */
    private ${column.targetDataType} ${fieldName};

    public void set${propertyName}(${column.targetDataType} ${fieldName}) {
        this.${fieldName} = ${fieldName};
    }

    public ${column.targetDataType} get${propertyName}() {
        return ${fieldName};
    }
    <#elseif (column.dataType?contains("int"))>

    /**
    * ${column.columnComment}
    */
    private ${column.targetDataType} ${fieldName};

    public void set${propertyName}(${column.targetDataType} ${fieldName}) {
        this.${fieldName} = ${fieldName};
    }

    public ${column.targetDataType} get${propertyName}() {
        return ${fieldName};
    }
    <#elseif (column.dataType?contains("date") || column.dataType?contains("time"))>

    /**
    * 开始 ${column.columnComment}
    */
    @JsonDeserialize(using = DateJsonDeserializer.class)
    private ${column.targetDataType} ${fieldName}Min;

    /**
    * 结束 ${column.columnComment}
    */
    @JsonDeserialize(using = DateJsonDeserializer.class)
    private ${column.targetDataType} ${fieldName}Max;

    public void set${propertyName}Min(${column.targetDataType} ${fieldName}Min) {
        this.${fieldName}Min = ${fieldName}Min;
    }

    public ${column.targetDataType} get${propertyName}Min() {
        return ${fieldName}Min;
    }

    public void set${propertyName}Max(${column.targetDataType} ${fieldName}Max) {
        this.${fieldName}Max = ${fieldName}Max;
    }

    public ${column.targetDataType} get${propertyName}Max() {
        return ${fieldName}Max;
    }
    <#elseif (column.targetDataType == "BigDecimal" || column.targetDataType == "Double" || column.targetDataType == "Float")>

    /**
    * 最小 ${column.columnComment}
    */
    private ${column.targetDataType} ${fieldName}Min;

    /**
    * 最大 ${column.columnComment}
    */
    private ${column.targetDataType} ${fieldName}Max;

    public void set${propertyName}Min(${column.targetDataType} ${fieldName}Min) {
        this.${fieldName}Min = ${fieldName}Min;
    }

    public ${column.targetDataType} get${propertyName}Min() {
        return ${fieldName}Min;
    }

    public void set${propertyName}Max(${column.targetDataType} ${fieldName}Max) {
        this.${fieldName}Max = ${fieldName}Max;
    }

    public ${column.targetDataType} get${propertyName}Max() {
        return ${fieldName}Max;
    }
    <#elseif (column.targetDataType == "String")>

    /**
    * ${column.columnComment} (开始匹配）
    */
    private ${column.targetDataType} ${fieldName}StartWith;

    public void set${propertyName}StartWith(${column.targetDataType} ${fieldName}StartWith) {
        this.${fieldName}StartWith = ${fieldName}StartWith;
    }

    public ${column.targetDataType} get${propertyName}StartWith() {
        return ${fieldName}StartWith;
    }
    <#else>

    /**
    * ${column.columnComment}
    */
    private ${column.targetDataType} ${fieldName};

    public void set${propertyName}(${column.targetDataType} ${fieldName}) {
        this.${fieldName} = ${fieldName};
    }

    public ${column.targetDataType} get${propertyName}() {
        return ${fieldName};
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

    public ${className}ConditionExtension get${className}ConditionExtension() {

        ${className}ConditionExtension parameter = new ${className}ConditionExtension();
    <#list table.columns as column>
    <#include "/include/column/properties.ftl">
    <#if column.ignoreSearch>
    <#else>
        <#if (column.columnName == table.validStatusColumn.columnName || column.fkSelect || column.select)>
        parameter.set${propertyName}(${fieldName});
        <#elseif (column.dataType?contains("int"))>
        parameter.set${propertyName}(${fieldName});
        <#elseif (column.dataType?contains("date") || column.dataType?contains("time"))>
        parameter.set${propertyName}Min(${fieldName}Min);
        parameter.set${propertyName}Max(${fieldName}Max);
        <#elseif (column.dataType == "double" || column.dataType == "float")>
        parameter.set${propertyName}Min(${fieldName}Min);
        parameter.set${propertyName}Max(${fieldName}Max);
        <#elseif (column.dataType == "decimal" || column.dataType == "numeric")>
        parameter.set${propertyName}Min(${fieldName}Min);
        parameter.set${propertyName}Max(${fieldName}Max);
        <#elseif (column.targetDataType == "String")>
        parameter.set${propertyName}StartWith(${fieldName}StartWith);
        <#else>
        parameter.set${propertyName}(${fieldName});
        </#if>
    </#if>
    </#list>
        setParameterSort(parameter);

        return parameter;
    }

    private void setParameterSort(${className}ConditionExtension parameter) {

        if (sortEnum == null) {
            return;
        }

        switch (sortEnum) {
        <#list table.columns as column>
        <#include "/include/column/properties.ftl">
        <#if (column.ignoreSearch || column.columnName == table.validStatusColumn.columnName)>
        <#else>
            case ${propertyName}Asc:
                parameter.set${propertyName}Asc();
                break;
            case ${propertyName}Desc:
                parameter.set${propertyName}Desc();
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
    <#include "/include/column/properties.ftl">
    <#if (column.ignoreSearch || column.columnName == table.validStatusColumn.columnName)>
    <#else>
        ${propertyName}Asc(${count + 1}),
        ${propertyName}Desc(${count + 2})<#if ((count + 2) == sortCount)>;<#else>,</#if>
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
            <#include "/include/column/properties.ftl">
            <#if (column.ignoreSearch || column.columnName == table.validStatusColumn.columnName)>
            <#else>
                case ${count + 1}:
                    return ${propertyName}Asc;
                case ${count + 2}:
                    return ${propertyName}Desc;
                <#assign count = count + 2>
            </#if>
            </#list>
                default:
                    return null;
            }
        }
    }
}
