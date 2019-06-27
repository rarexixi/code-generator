<#include "/include/table/properties.ftl">
package ${basePackage}.admin.vm.order;

import ${baseCommonPackage}.constant.SortConstants;
import ${basePackage}.models.condition.order.${className}OrderCondition;
import ${basePackage}.admin.vm.OrderVm;

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
public class ${className}OrderVm implements OrderVm {
    <#list table.indexes as column>
    <#include "/include/column/properties.ftl">

    /**
     * 以${columnComment}排序
     */
    public SortConstants ${fieldName}Sort;
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
