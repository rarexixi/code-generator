<#include "/include/table/properties.ftl">
package ${basePackage}.models.condition.order;

import ${baseCommonPackage}.model.OrderCondition;

<#include "/include/java_copyright.ftl">
public class ${className}OrderCondition extends OrderCondition {
    <#list table.columns as column>
    <#include "/include/column/properties.ftl">
    <#if (column.ignoreSearch || column.dataType?ends_with("text"))>
    <#else>

    /**
     * 以${columnComment}排序 (null不排序，true升序，false降序)
     */
    public Boolean ${fieldName}Sort;
    </#if>
    </#list>
    <#list table.columns as column>
    <#include "/include/column/properties.ftl">
    <#if (column.ignoreSearch || column.dataType?ends_with("text"))>
    <#else>

    public void set${propertyName}Sort(Boolean ${fieldName}Sort) {
        this.${fieldName}Sort = ${fieldName}Sort;
    }

    public Boolean get${propertyName}Sort() {
        return ${fieldName}Sort;
    }
    </#if>
    </#list>
}
