<#include "/include/table/properties.ftl">
package ${modulePackage}.presentation.condition;

import ${commonPackage}.models.UpdateCondition;

import java.math.BigDecimal;
import java.util.Collection;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * ${table.comment}更新条件
 *
 * @author ${author}
 */
@Getter
@Setter
@ToString
public class ${className}UpdateCondition extends UpdateCondition {
    <#list pks as column>
    <#include "/include/column/properties.ftl">

    /**
     * ${columnFullComment}
     */
    private ${fieldType} ${fieldName};
    </#list>
    <#if (table.hasUniPk)>

    /**
     * ${columnFullComment}列表
     */
    private Collection<${fieldType}> ${fieldName}List;
    </#if>
}
