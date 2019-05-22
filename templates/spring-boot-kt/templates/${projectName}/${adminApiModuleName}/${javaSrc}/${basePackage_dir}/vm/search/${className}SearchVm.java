<#include "/include/table/properties.ftl">
package ${basePackage}.vm.search;

import ${basePackage}.models.condition.${className}Condition;
import ${basePackage}.models.condition.extension.${className}ConditionExtension;
import ${basePackage}.vm.SearchVm;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.*;

<#include "/include/java_copyright.ftl">
@Getter
@Setter
@ToString
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
    <#list table.indexes as column>
    <#include "/include/column/properties.ftl">
    <#if (isDate || isTime || isDateTime)>

    public void set${propertyName}Range(Date[] dateRange) {
        if (dateRange == null || dateRange.length != 2) return;
        this.${fieldName}Min = dateRange[0];
        this.${fieldName}Max = dateRange[1];
    }
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
