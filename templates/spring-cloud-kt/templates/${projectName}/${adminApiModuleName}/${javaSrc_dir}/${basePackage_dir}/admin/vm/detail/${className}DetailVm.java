<#include "/include/table/properties.ftl">
<#assign sortCount = 0>
package ${basePackage}.admin.vm.detail;

import ${basePackage}.models.entity.${className}Entity;
import ${basePackage}.models.entity.extension.${className}EntityExtension;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.*;

<#include "/include/java_copyright.ftl">
public class ${className}DetailVm implements Serializable {

    public ${className}DetailVm() {
    }

    public ${className}DetailVm(${className}EntityExtension entity) {

        <#list table.columns as column>
        <#include "/include/column/properties.ftl">
        ${fieldName} = entity.get${propertyName}();
        </#list>
    }

    public ${className}DetailVm(${className}Entity entity) {

        <#list table.columns as column>
        <#include "/include/column/properties.ftl">
        ${fieldName} = entity.get${propertyName}();
        </#list>
    }
    <#list table.columns as column>
    <#include "/include/column/properties.ftl">

    /**
     * ${columnFullComment}
     */
    private ${fieldType} ${fieldName};
    </#list>
    <#list table.columns as column>
    <#include "/include/column/properties.ftl">

    /**
    * 获取${columnComment}
    */
    public ${fieldType} get${propertyName}() {
        return ${fieldName};
    }

    /**
    * 设置${columnComment}
    */
    public void set${propertyName}(${fieldType} ${fieldName}) {
        this.${fieldName} = ${fieldName};
    }
    </#list>
}
