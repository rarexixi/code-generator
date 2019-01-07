<#include "/include/table/properties.ftl">
<#assign sortCount = 0>
package ${basePackage}.admin.vm.detail;

import ${basePackage}.entity.extension.${className}EntityExtension;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.*;

<#include "/include/java_copyright.ftl">
public class ${className}DetailVm implements Serializable {

    public ${className}DetailVm() {
    }

    public ${className}DetailVm(${className}EntityExtension vo) {

        <#list table.columns as column>
        <#include "/include/column/properties.ftl">
        ${fieldName} = vo.get${propertyName}();
        </#list>
    }

    <#list table.columns as column>
    <#include "/include/column/properties.ftl">
    /**
     * ${column.columnComment}
     */
    private ${column.targetDataType} ${fieldName};

    </#list>

    <#list table.columns as column>
    <#include "/include/column/properties.ftl">
    /**
    * 获取${column.columnComment}
    */
    public ${column.targetDataType} get${propertyName}() {
        return ${fieldName};
    }

    /**
    * 设置${column.columnComment}
    */
    public void set${propertyName}(${column.targetDataType} ${fieldName}) {
        this.${fieldName} = ${fieldName};
    }

    </#list>
}
