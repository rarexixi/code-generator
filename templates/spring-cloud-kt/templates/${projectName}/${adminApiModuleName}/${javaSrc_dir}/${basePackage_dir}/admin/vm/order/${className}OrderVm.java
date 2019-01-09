<#include "/include/table/properties.ftl">
package ${basePackage}.admin.vm.order;

import ${baseCommonPackage}.model.OrderCondition;

import ${basePackage}.models.condition.${className}Condition;
import ${basePackage}.models.condition.extension.${className}ConditionExtension;
import ${basePackage}.models.condition.order.${className}OrderCondition;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.*;

<#include "/include/java_copyright.ftl">
public class ${className}OrderVm extends OrderCondition {
    <#list table.indexes as column>
    <#include "/include/column/properties.ftl">

    /**
     * 以${columnComment}排序 (null不排序，true升序，false降序)
     */
    public Boolean ${fieldName}Sort;
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

    public ${className}OrderCondition getOrderCondition() {

        ${className}OrderCondition condition = new ${className}OrderCondition();
        <#list table.indexes as column>
        <#include "/include/column/properties.ftl">
        condition.set${propertyName}Sort(${fieldName}Sort);
        </#list>

        return condition;
    }
}
