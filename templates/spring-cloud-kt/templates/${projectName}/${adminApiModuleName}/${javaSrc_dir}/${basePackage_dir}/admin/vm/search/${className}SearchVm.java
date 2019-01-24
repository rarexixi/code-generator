<#include "/include/table/properties.ftl">
package ${basePackage}.admin.vm.search;

import ${basePackage}.models.condition.${className}Condition;
import ${basePackage}.models.condition.extension.${className}ConditionExtension;
import ${basePackage}.admin.vm.SearchVm;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.*;

<#include "/include/java_copyright.ftl">
public class ${className}SearchVm implements SearchVm {
    <#if (table.validStatusColumn??)>

    /**
     * ${table.validStatusColumn.columnComment}
     */
    private ${table.validStatusColumn.targetDataType} ${table.validStatusColumn.targetName?uncap_first};
    </#if>
    <#list table.indexes as column>
    <#if (column.validStatus)>
    <#else>
    <#include "/include/column/properties.ftl">
    <#if (canBeEqual)>

    /**
     * ${columnFullComment}
     */
    private ${fieldType} ${fieldName};
    </#if>
    <#if (canBeList)>

    /**
     * ${columnComment} 列表
     */
    private List<${fieldType}> ${fieldName}List;
    </#if>
    <#if (canBeRange)>

    /**
     * 最小 ${columnComment}
     */
    private ${fieldType} ${fieldName}Min;

    /**
     * 最大 ${columnComment}
     */
    private ${fieldType} ${fieldName}Max;
    </#if>
    <#if (canBeNull)>

    /**
     * ${columnComment}为null
     */
    private Boolean ${fieldName}IsNull;
    </#if>
    <#if (isString)>

    /**
     * ${columnComment}为空
     */
    private Boolean ${fieldName}IsEmpty;

    /**
     * ${columnComment}
     */
    private ${fieldType} ${fieldName}StartWith;

    /**
     * ${columnComment}
     */
    private ${fieldType} ${fieldName}Contains;
    </#if>
    </#if>
    </#list>
    <#if (table.validStatusColumn??)>

    public void set${table.validStatusColumn.targetName}(${table.validStatusColumn.targetDataType} ${table.validStatusColumn.targetName?uncap_first}) {
        this.${table.validStatusColumn.targetName?uncap_first} = ${table.validStatusColumn.targetName?uncap_first};
    }

    public ${table.validStatusColumn.targetDataType} get${table.validStatusColumn.targetName?uncap_first}() {
        return ${table.validStatusColumn.targetName?uncap_first};
    }
    </#if>
    <#list table.indexes as column>
    <#if (column.validStatus)>
    <#else>
    <#include "/include/column/properties.ftl">
    <#if (canBeEqual)>

    public void set${propertyName}(${fieldType} ${fieldName}) {
        this.${fieldName} = ${fieldName};
    }

    public ${fieldType} get${propertyName}() {
        return ${fieldName};
    }
    </#if>
    <#if (canBeList)>

    public void set${propertyName}List(List<${fieldType}> ${fieldName}List) {
        this.${fieldName}List = ${fieldName}List;
    }

    public List<${fieldType}> get${propertyName}List() {
        return ${fieldName}List;
    }
    </#if>
    <#if (canBeRange)>

    public void set${propertyName}Min(${fieldType} ${fieldName}Min) {
        this.${fieldName}Min = ${fieldName}Min;
    }

    public ${fieldType} get${propertyName}Min() {
        return ${fieldName}Min;
    }

    public void set${propertyName}Max(${fieldType} ${fieldName}Max) {
        this.${fieldName}Max = ${fieldName}Max;
    }

    public ${fieldType} get${propertyName}Max() {
        return ${fieldName}Max;
    }
    <#if (isDate || isTime || isDateTime)>

    public void set${propertyName}Range(Date[] dateRange) {
        if (dateRange == null || dateRange.length != 2) return;
        this.${fieldName}Min = dateRange[0];
        this.${fieldName}Max = dateRange[1];
    }
    </#if>
    </#if>
    <#if (canBeNull)>

    public void set${propertyName}IsNull(Boolean ${fieldName}IsNull) {
        this.${fieldName}IsNull = ${fieldName}IsNull;
    }

    public Boolean get${propertyName}IsNull() {
        return ${fieldName}IsNull;
    }
    </#if>
    <#if (isString)>

    public void set${propertyName}IsEmpty(Boolean ${fieldName}IsEmpty) {
        this.${fieldName}IsEmpty = ${fieldName}IsEmpty;
    }

    public Boolean get${propertyName}IsEmpty() {
        return ${fieldName}IsEmpty;
    }

    public void set${propertyName}StartWith(${fieldType} ${fieldName}StartWith) {
        this.${fieldName}StartWith = ${fieldName}StartWith;
    }

    public ${fieldType} get${propertyName}StartWith() {
        return ${fieldName}StartWith;
    }

    public void set${propertyName}Contains(${fieldType} ${fieldName}Contains) {
        this.${fieldName}Contains = ${fieldName}Contains;
    }

    public ${fieldType} get${propertyName}Contains() {
        return ${fieldName}Contains;
    }
    </#if>
    </#if>
    </#list>

    public ${className}Condition getCondition() {

        return getConditionExtension();
    }

    public ${className}ConditionExtension getConditionExtension() {

        ${className}ConditionExtension condition = new ${className}ConditionExtension();
        <#if (table.validStatusColumn??)>
        condition.set${table.validStatusColumn.targetName}(${table.validStatusColumn.targetName?uncap_first});
        </#if>
        <#list table.indexes as column>
        <#if (column.validStatus)>
        <#else>
        <#include "/include/column/properties.ftl">
        <#if (canBeEqual)>
        condition.set${propertyName}(${fieldName});
        </#if>
        <#if (canBeList)>
        condition.set${propertyName}List(${fieldName}List);
        </#if>
        <#if (canBeRange)>
        condition.set${propertyName}Min(${fieldName}Min);
        condition.set${propertyName}Max(${fieldName}Max);
        </#if>
        <#if (canBeNull)>
        condition.set${propertyName}IsNull(${fieldName}IsNull);
        </#if>
        <#if (isString)>
        condition.set${propertyName}IsEmpty(${fieldName}IsEmpty);
        condition.set${propertyName}StartWith(${fieldName}StartWith);
        condition.set${propertyName}Contains(${fieldName}Contains);
        </#if>
        </#if>
        </#list>
        return condition;
    }
}
