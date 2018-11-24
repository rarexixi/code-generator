<#assign className = table.tableClassName>
<#assign sortCount = 0>
package ${basePackage}.admin.vm.detail;

import ${basePackage}.admin.databind.DateJsonSerializer;
import ${basePackage}.vo.${className}Vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.*;

<#include "/include/java_copyright.ftl">
public class ${className}DetailVm implements Serializable {

    public ${className}DetailVm() {
    }

    public ${className}DetailVm(${className}Vo vo) {

        <#list table.columns as column>
        ${column.columnFieldNameFirstLower} = vo.get${column.columnFieldName}();
        </#list>
    }

    <#list table.columns as column>
    /**
     * ${column.columnComment}
     */
    <#if (column.columnFieldType == "Date")>
    @JsonSerialize(using = DateJsonSerializer.class)
    </#if>
    private ${column.columnFieldType} ${column.columnFieldNameFirstLower};

    </#list>

    <#list table.columns as column>
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

    </#list>
}
