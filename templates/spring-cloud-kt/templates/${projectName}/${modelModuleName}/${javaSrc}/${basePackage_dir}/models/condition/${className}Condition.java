<#include "/include/table/properties.ftl">
package ${basePackage}.models.condition;

import ${basePackage}.models.common.BaseCondition;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import java.math.BigDecimal;
import java.util.*;

<#include "/include/java_copyright.ftl">
@Getter
@Setter
@ToString
public class ${className}Condition extends BaseCondition {
<#list table.columnsExceptBase as column>
<#include "/include/column/properties.ftl">
<#if (column.ignoreSearch || isContent)>
<#else>
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
}
