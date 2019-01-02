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
        ${column.targetColumnNameFirstLower} = vo.get${column.targetColumnName}();
        </#list>
    }

    <#list table.columns as column>
    /**
     * ${column.columnComment}
     */
    private ${column.targetDataType} ${column.targetColumnNameFirstLower};

    </#list>

    <#list table.columns as column>
    /**
    * 获取${column.columnComment}
    */
    public ${column.targetDataType} get${column.targetColumnName}() {
        return ${column.targetColumnNameFirstLower};
    }

    /**
    * 设置${column.columnComment}
    */
    public void set${column.targetColumnName}(${column.targetDataType} ${column.targetColumnNameFirstLower}) {
        this.${column.targetColumnNameFirstLower} = ${column.targetColumnNameFirstLower};
    }

    </#list>
}
