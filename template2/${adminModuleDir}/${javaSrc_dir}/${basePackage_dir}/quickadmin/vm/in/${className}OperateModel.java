<#assign className = table.tableClassName>
<#assign classNameLower = table.tableClassNameFirstLower>
package ${basePackage}.quickadmin.vm.in;

import ${basePackage}.entity.${className}Entity;

import java.math.BigDecimal;
import java.util.Date;
import java.io.Serializable;

public class ${className}OperateModel implements Serializable {

    public ${className}OperateModel() {
    }

    <#list table.columns as column>
    <#if !column.notRequired>
    /**
     * ${column.columnComment}
     */
    private ${column.columnFieldType} ${column.columnFieldNameFirstLower};

    </#if>
    </#list>

    <#list table.columns as column>
    <#if !column.notRequired>
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

    </#if>
    </#list>
}
