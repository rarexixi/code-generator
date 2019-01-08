<#include "/include/table/properties.ftl">
package ${basePackage}.admin.vm.search;

import ${basePackage}.models.condition.${className}Condition;
import ${basePackage}.models.condition.extension.${className}ConditionExtension;
import ${basePackage}.models.condition.order.${className}OrderCondition;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.*;

<#include "/include/java_copyright.ftl">
public class ${className}SearchVm implements Serializable {
    <#list table.indexes as column>
    <#include "/include/column/properties.ftl">
    <#if (canBeEqual)>

    /**
     * ${column.columnComment}
     */
    private ${fieldType} ${fieldName};
    </#if>
    <#if (canBeList)>

    /**
     * ${column.columnComment} 列表
     */
    private List<${fieldType}> ${fieldName}List;
    </#if>
    <#if (canBeRange)>

    /**
     * 最小 ${column.columnComment}
     */
    private ${fieldType} ${fieldName}Min;

    /**
     * 最大 ${column.columnComment}
     */
    private ${fieldType} ${fieldName}Max;
    </#if>
    <#if (canBeNull)>

    /**
     * ${column.columnComment}
     */
    private Boolean ${fieldName}IsNull;
    </#if>
    <#if (column.dataType?ends_with("char"))>

    /**
     * ${column.columnComment}
     */
    private Boolean ${fieldName}IsEmpty;

    /**
     * ${column.columnComment}
     */
    private ${fieldType} ${fieldName}StartWith;

    /**
     * ${column.columnComment}
     */
    private ${fieldType} ${fieldName}Contains;
    </#if>
    </#list>
    <#list table.indexes as column>
    <#include "/include/column/properties.ftl">

    /**
     * 以${columnComment}排序 (null不排序，true升序，false降序)
     */
    public Boolean ${fieldName}Sort;
    </#list>
    <#list table.indexes as column>
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
        this.${fieldName}Min = ${fieldName}Min;
        this.${fieldName}Max = ${fieldName}Max;
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
    <#if (column.dataType?ends_with("char"))>

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
    </#list>
    <#list table.indexes as column>
    <#include "/include/column/properties.ftl">

    public void set${propertyName}Sort(Boolean ${fieldName}Sort) {
        this.${fieldName}Sort = ${fieldName}Sort;
    }

    public Boolean get${propertyName}Sort() {
        return ${fieldName}Sort;
    }
    </#list>

    public ${className}Condition getCondition() {

        return getConditionExtension();
    }

    public ${className}ConditionExtension getConditionExtension() {

        ${className}ConditionExtension condition = new ${className}ConditionExtension();
        <#list table.indexes as column>
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
        <#if (column.dataType?ends_with("char"))>
        condition.set${propertyName}IsEmpty(${fieldName}IsEmpty);
        condition.set${propertyName}StartWith(${fieldName}StartWith);
        condition.set${propertyName}Contains(${fieldName}Contains);
        </#if>
        </#list>
        return condition;
    }

    public ${className}OrderCondition getOrderCondition() {

        ${className}OrderCondition condition = new ${className}OrderCondition();
        <#list table.indexes as column>
        <#include "/include/column/properties.ftl">
        condition.set${propertyName}Sort(${fieldName}Sort);
        </#list>

        return condition;
    }
}
