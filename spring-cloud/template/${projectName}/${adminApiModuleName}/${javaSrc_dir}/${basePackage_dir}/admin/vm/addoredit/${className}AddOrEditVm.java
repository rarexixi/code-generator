<#include "/include/table/properties.ftl">
package ${basePackage}.admin.vm.addoredit;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import ${basePackage}.admin.databind.DateJsonDeserializer;
import ${basePackage}.entity.${className}Entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.*;

<#include "/include/java_copyright.ftl">
public class ${className}AddOrEditVm implements Serializable {

    <#list table.columns as column>
    <#if column.notRequired>
    <#else>
    /**
     * ${column.columnComment}
     */
    <#if (column.columnFieldType == "Date")>
    @JsonDeserialize(using = DateJsonDeserializer.class)
    </#if>
    private ${column.columnFieldType} ${column.columnFieldNameFirstLower};

    </#if>
    </#list>

    <#list table.columns as column>
    <#if column.notRequired>
    <#else>
    /**
    * 获取${column.columnComment}
    */
    public ${column.columnFieldType} get${column.columnFieldName}() {
        return ${column.columnFieldNameFirstLower};
    }

    /**
    * 设置${column.columnComment}
    */
    public void set${column.columnFieldName}(${column.columnFieldType} ${column.columnFieldNameFirstLower}) {
        this.${column.columnFieldNameFirstLower} = ${column.columnFieldNameFirstLower};
    }

    </#if>
    </#list>

    public ${className}Entity get${className}Entity() {

        ${className}Entity entity = new ${className}Entity();
        <#list table.columns as column>
        <#if column.notRequired>
        <#else>
        entity.set${column.columnFieldName}(${column.columnFieldNameFirstLower});
        </#if>
        </#list>

        return entity;
    }

    public void set${className}Entity(${className}Entity entity) {

        <#list table.columns as column>
        <#if column.notRequired>
        <#else>
        this.set${column.columnFieldName}(entity.get${column.columnFieldName}());
        </#if>
        </#list>
    }
}
