<#include "/include/table/properties.ftl">
package ${basePackage}.condition.order;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.*;

<#include "/include/java_copyright.ftl">
public class ${className}OrderCondition extends OrderCondition {

    public ${className}OrderCondition() {
        super();
    }
    <#list table.columns as column>
    <#include "/include/column/properties.ftl">

    public void set${propertyName}Asc() {
        super.orderByMap.put("${column.tableName}.${column.columnName}", "ASC");
    }

    public void set${propertyName}Desc() {
        super.orderByMap.put("${column.tableName}.${column.columnName}", "DESC");
    }

    </#list>
}
