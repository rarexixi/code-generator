<#include "/include/table/properties.ftl">
package ${basePackage}.admin.vm.addoredit;

import ${basePackage}.entity.${className}Entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.*;

<#include "/include/java_copyright.ftl">
public class ${className}AddOrEditVm implements Serializable {
    <#list table.requiredColumns as column>
    <#include "/include/column/properties.ftl">

    /**
     * ${column.columnComment}
     */
    private ${column.targetDataType} ${fieldName};
    </#list>
    <#list table.requiredColumns as column>
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

    public ${className}Entity get${className}Entity() {

        ${className}Entity entity = new ${className}Entity();
        <#list table.requiredColumns as column>
        <#include "/include/column/properties.ftl">
        entity.set${propertyName}(${fieldName});
        </#list>

        return entity;
    }

    public void set${className}Entity(${className}Entity entity) {

        <#list table.requiredColumns as column>
        <#include "/include/column/properties.ftl">
        this.set${propertyName}(entity.get${propertyName}());
        </#list>
    }
}
