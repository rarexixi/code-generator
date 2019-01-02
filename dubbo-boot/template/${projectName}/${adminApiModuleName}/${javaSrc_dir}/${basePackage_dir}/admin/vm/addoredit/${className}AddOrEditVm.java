<#assign className = table.tableClassName>
<#assign sortCount = 0>
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
    <#if (column.targetDataType == "Date")>
    @JsonDeserialize(using = DateJsonDeserializer.class)
    </#if>
    private ${column.targetDataType} ${column.targetColumnNameFirstLower};

    </#if>
    </#list>

    <#list table.columns as column>
    <#if column.notRequired>
    <#else>
    /**
    * 获取${column.columnComment}
    */
    public ${column.targetDataType} get${column.targetColumnName}() {
        return ${column.targetColumnNameFirstLower};
    }

    /**
    * 设置${column.columnComment}
    */
    public void set${column.targetColumnName}(${column.targetDataType} ${column.targetColumnNameFirstLower}) {
        this.${column.targetColumnNameFirstLower} = ${column.targetColumnNameFirstLower};
    }

    </#if>
    </#list>

    public ${className}Entity get${className}Entity() {

        ${className}Entity entity = new ${className}Entity();
        <#list table.columns as column>
        <#if column.notRequired>
        <#else>
        entity.set${column.targetColumnName}(${column.targetColumnNameFirstLower});
        </#if>
        </#list>

        return entity;
    }
}
