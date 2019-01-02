<#assign className = table.tableClassName>
<#assign sortCount = 0>
package ${basePackage}.admin.vm.addoredit;

import ${baseCommonPackage}.validation.DataAdd;
import ${baseCommonPackage}.validation.DataEdit;
import ${basePackage}.admin.databind.DateJsonDeserializer;
import ${basePackage}.entity.${className}Entity;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import javax.validation.constraints.*;
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
    <#if (column.primaryKey)>
    @NotNull(groups = {<#if column.autoIncrement>DataEdit.class<#else>DataAdd.class, DataEdit.class</#if>}, message = "${column.targetColumnNameFirstLower} (${(column.columnComment?split("[（ ,，(]", "r"))[0]})不能为空")
    <#elseif (!column.notRequired && (!column.nullable && !(column.columnDefault??)))>
    @NotNull(groups = {DataAdd.class}, message = "${column.targetColumnNameFirstLower} (${(column.columnComment?split("[（ ,，(]", "r"))[0]})不能为空")
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

    public void set${className}Entity(${className}Entity entity) {

        <#list table.columns as column>
        <#if column.notRequired>
        <#else>
        this.set${column.targetColumnName}(entity.get${column.targetColumnName}());
        </#if>
        </#list>
    }
}
