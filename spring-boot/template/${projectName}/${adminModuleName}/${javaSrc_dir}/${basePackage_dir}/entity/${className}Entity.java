<#assign className = table.tableClassName>
package ${basePackage}.entity;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import ${baseCommonPackage}.annotation.InsertNotNull;
import ${baseCommonPackage}.annotation.UpdateNotNull;
import ${basePackage}.databind.DateJsonDeserializer;

import java.math.BigDecimal;
import java.util.Date;
import java.io.Serializable;

<#include "/include/java_copyright.ftl">
public class ${className}Entity implements Serializable {

    <#list table.columns as column>
    /**
     * ${column.columnComment}
     */
<#if (column.primaryKey )>
    <#if column.autoIncrement>
    @UpdateNotNull(name="${column.columnFieldNameFirstLower} (${(column.columnComment?split("[（ ,，(]", "r"))[0]})")
    <#else>
    @InsertNotNull(name="${column.columnFieldNameFirstLower} (${(column.columnComment?split("[（ ,，(]", "r"))[0]})")
    </#if>
<#elseif (!column.notRequired && (!column.nullable && !(column.columnDefault??)))>
    @InsertNotNull(name="${column.columnFieldNameFirstLower} (${(column.columnComment?split("[（ ,，(]", "r"))[0]})")
    @UpdateNotNull(name="${column.columnFieldNameFirstLower} (${(column.columnComment?split("[（ ,，(]", "r"))[0]})")
</#if>
    <#if (column.columnFieldType == "Date")>
    @JsonDeserialize(using = DateJsonDeserializer.class)
    </#if>
    private ${column.columnFieldType} ${column.columnFieldNameFirstLower};

    </#list>

    <#list table.columns as column>
    /**
     * 设置${column.columnComment}
     */
    public void set${column.columnFieldName}(${column.columnFieldType} ${column.columnFieldNameFirstLower}) {
        this.${column.columnFieldNameFirstLower} = ${column.columnFieldNameFirstLower};
    }

    /**
     * 获取${column.columnComment}
     */
    public ${column.columnFieldType} get${column.columnFieldName}() {
        return ${column.columnFieldNameFirstLower};
    }

    </#list>
}
