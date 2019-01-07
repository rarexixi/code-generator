<#include "/include/table/properties.ftl">
package ${basePackage}.admin.vm.addoredit;

import ${basePackage}.entity.${className}Entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.*;

<#include "/include/java_copyright.ftl">
public class ${className}AddOrEditVm implements Serializable {

    <#list table.columns as column>
    <#include "/include/column/properties.ftl">
    <#if column.notRequired>
    <#else>
    /**
     * ${column.columnComment}
     */
    private ${column.targetDataType} ${fieldName};

    </#if>
    </#list>

    <#list table.columns as column>
    <#include "/include/column/properties.ftl">
    <#if column.notRequired>
    <#else>
    /**
    * 获取${column.columnComment}
    */
    public ${column.targetDataType} get${propertyName}() {
        return ${fieldName};
    }

    /**
    * 设置${column.columnComment}
    */
    public void set${propertyName}(${column.targetDataType} ${fieldName}) {
        this.${fieldName} = ${fieldName};
    }

    </#if>
    </#list>

    public ${className}Entity get${className}Entity() {

        ${className}Entity entity = new ${className}Entity();
        <#list table.columns as column>
        <#include "/include/column/properties.ftl">
        <#if column.notRequired>
        <#else>
        entity.set${propertyName}(${fieldName});
        </#if>
        </#list>

        return entity;
    }

    public void set${className}Entity(${className}Entity entity) {

        <#list table.columns as column>
        <#include "/include/column/properties.ftl">
        <#if column.notRequired>
        <#else>
        this.set${propertyName}(entity.get${propertyName}());
        </#if>
        </#list>
    }
}
