<#assign className = table.tableClassName>
package ${basePackage}.condition.order;

import java.math.BigDecimal;

<#include "/include/java_copyright.ftl">
public class ${className}OrderCondition extends BaseOrderCondition {

    public ${className}OrderCondition() {
        super();
    }
    <#list table.columns as column>

    public void set${column.targetColumnName}Asc() {
        super.orderByMap.put("${column.tableName}.${column.columnName}", "ASC");
    }

    public void set${column.targetColumnName}Desc() {
        super.orderByMap.put("${column.tableName}.${column.columnName}", "DESC");
    }
    </#list>
}
