<#include "/include/table/properties.ftl">
package ${basePackage}.models.entity.extension;

import ${basePackage}.models.entity.${className}Entity;

<#include "/include/java_copyright.ftl">
public class ${className}EntityExtension extends ${className}Entity {
    <#list table.fkSelectColumns as column>
    <#include "/include/column/properties.ftl">

    /**
     * ${columnFullComment}
     */
    private String ${fieldNameExceptKey}Text;
    </#list>
    <#list table.fkSelectColumns as column>
    <#include "/include/column/properties.ftl">

    /**
     * 获取${columnComment}
     */
    public String get${propertyExceptKey}Text() {
        return ${fieldNameExceptKey}Text;
    }

    /**
     * 设置${columnComment}
     */
    public void set${propertyExceptKey}Text(String ${fieldNameExceptKey}Text) {
        this.${fieldNameExceptKey}Text = ${fieldNameExceptKey}Text;
    }
    </#list>
}
