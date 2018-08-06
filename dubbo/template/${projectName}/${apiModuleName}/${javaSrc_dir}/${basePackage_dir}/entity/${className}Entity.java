<#assign className = table.tableClassName>
package ${basePackage}.entity;

import ${baseCommonPackage}.validation.*;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;
import java.io.Serializable;

<#include "/include/java_copyright.ftl">
public class ${className}Entity implements Serializable {

    <#list table.columns as column>
    /**
     * ${column.columnComment}
     */
    <#if (column.primaryKey)>
    @NotNull(groups = {<#if column.autoIncrement>DataEdit.class<#else>DataAdd.class</#if>}, message = "${column.columnFieldNameFirstLower} (${(column.columnComment?split("[（ ,，(]", "r"))[0]})不能为空")
    <#elseif (!column.notRequired && (!column.nullable && !(column.columnDefault??)))>
    @NotNull(groups = {DataAdd.class, DataEdit.class}, message = "${column.columnFieldNameFirstLower} (${(column.columnComment?split("[（ ,，(]", "r"))[0]})不能为空")
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
