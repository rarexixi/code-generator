<#include "/include/table/properties.ftl">
package ${basePackage}.models.condition.order;

import ${baseCommonPackage}.model.OrderCondition;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

<#include "/include/java_copyright.ftl">
@Getter
@Setter
@ToString
public class ${className}OrderCondition implements OrderCondition {
    <#list table.columns as column>
    <#include "/include/column/properties.ftl">
    <#if (column.ignoreSearch || isContent)>
    <#else>

    /**
     * 以${columnComment}排序 (null不排序，true升序，false降序)
     */
    public Boolean ${fieldName}Sort;
    </#if>
    </#list>
}
