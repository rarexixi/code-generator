<#assign className = table.tableClassName>
<#assign sortCount = 0>
package ${basePackage}.admin.vm.detail;

import ${basePackage}.vo.${className}Vo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.*;

<#include "/include/java_copyright.ftl">
public class ${className}DetailVm implements Serializable {

    public ${className}DetailVm() {
    }

    public ${className}DetailVm(${className}Vo vo) {

        <#list table.columns as column>
        <#if column.notRequired>
        <#else>
        ${column.columnFieldNameFirstLower} = vo.get${column.columnFieldName}();
        </#if>
        </#list>
    }

    <#list table.columns as column>
    <#if column.notRequired>
    <#else>
    /**
     * ${column.columnComment}
     */
    private ${column.columnFieldType} ${column.columnFieldNameFirstLower};

    </#if>
    </#list>

    <#list table.columns as column>
    <#if column.notRequired>
    <#else>
    /**
    * 获取${column.columnComment}
    */
    public ${column.columnFieldType} get${column.columnFieldName}() {
        return ${column.columnFieldNameFirstLower};
    }

    /**
    * 设置${column.columnComment}
    */
    public void set${column.columnFieldName}(${column.columnFieldType} ${column.columnFieldNameFirstLower}) {
        this.${column.columnFieldNameFirstLower} = ${column.columnFieldNameFirstLower};
    }

    </#if>
    </#list>
}
