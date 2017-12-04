<#assign className = table.tableClassName>
package ${basePackage}.condition.order;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.*;

<#include "/include/java_copyright.ftl">
public class ${className}OrderCondition extends BaseOrderCondition implements Serializable {

    public ${className}OrderCondition() {
        super();
    }
    <#list table.columns as column>

    public void set${column.columnFieldName}Asc() {
        super.orderByMap.put("${column.tableName}.${column.columnName}", "ASC");
    }

    public void set${column.columnFieldName}Desc() {
        super.orderByMap.put("${column.tableName}.${column.columnName}", "DESC");
    }

    //endregion
    </#list>
}
