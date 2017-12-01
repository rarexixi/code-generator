package ${basePackage}.vo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

<#include "/include/java_copyright.ftl">
public class ${table.tableClassName}Vo implements Serializable {

    <#list table.columns as column>
    /**
     * ${column.columnComment}
     */
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
        return this.${column.columnFieldNameFirstLower};
    }

    </#list>
}
