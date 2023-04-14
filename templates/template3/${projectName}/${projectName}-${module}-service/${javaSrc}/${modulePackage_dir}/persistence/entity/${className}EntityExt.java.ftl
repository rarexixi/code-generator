<#include "/include/table/properties.ftl">
package ${modulePackage}.persistence.entity;

import java.math.BigDecimal;
import java.util.Collection;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * ${table.comment}扩展实体
 *
 * @author ${author}
 */
@Getter
@Setter
@ToString
public class ${className}EntityExt extends ${className}Entity {
    <#list table.fkSelectColumns as column>
    <#include "/include/column/properties.ftl">

    /**
     * ${columnFullComment}
     */
    private String ${fieldNameExceptKey}Text;
    </#list>
    <#list table.fkSelectColumns as column>
    <#include "/include/column/properties.ftl">

    private void set${propertyExceptKey}Text (String ${fieldNameExceptKey}Text) {
        this.${fieldNameExceptKey}Text = ${fieldNameExceptKey}Text;
    }

    private String get${propertyExceptKey}Text() {
        return ${fieldNameExceptKey}Text;
    }
    </#list>
}
