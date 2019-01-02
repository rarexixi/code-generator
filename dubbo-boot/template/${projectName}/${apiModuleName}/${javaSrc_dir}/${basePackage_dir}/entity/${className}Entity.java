<#assign className = table.tableClassName>
package ${basePackage}.entity;

import ${baseCommonPackage}.annotation.InsertNotNull;
import ${baseCommonPackage}.annotation.UpdateNotNull;

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
    @UpdateNotNull(name="${column.targetColumnNameFirstLower} (${(column.columnComment?split("[（ ,，(]", "r"))[0]})")
    <#else>
    @InsertNotNull(name="${column.targetColumnNameFirstLower} (${(column.columnComment?split("[（ ,，(]", "r"))[0]})")
    </#if>
<#elseif (!column.notRequired && (!column.nullable && !(column.columnDefault??)))>
    @InsertNotNull(name="${column.targetColumnNameFirstLower} (${(column.columnComment?split("[（ ,，(]", "r"))[0]})")
    @UpdateNotNull(name="${column.targetColumnNameFirstLower} (${(column.columnComment?split("[（ ,，(]", "r"))[0]})")
</#if>
    private ${column.targetDataType} ${column.targetColumnNameFirstLower};

    </#list>

    <#list table.columns as column>
    /**
     * 设置${column.columnComment}
     */
    public void set${column.targetColumnName}(${column.targetDataType} ${column.targetColumnNameFirstLower}) {
        this.${column.targetColumnNameFirstLower} = ${column.targetColumnNameFirstLower};
    }

    /**
     * 获取${column.columnComment}
     */
    public ${column.targetDataType} get${column.targetColumnName}() {
        return ${column.targetColumnNameFirstLower};
    }

    </#list>
}
