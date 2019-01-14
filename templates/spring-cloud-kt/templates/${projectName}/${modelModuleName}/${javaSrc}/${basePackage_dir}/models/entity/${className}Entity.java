<#include "/include/table/properties.ftl">
package ${basePackage}.models.entity;

import ${baseCommonPackage}.validation.*;
import ${basePackage}.models.common.BaseEntity;

import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.util.Date;

<#include "/include/java_copyright.ftl">
public class ${className}Entity extends BaseEntity {

    <#list table.columnsExceptBase as column>
    <#include "/include/column/properties.ftl">
    <#assign annotationName = ((fieldType == 'String') ? string('NotBlank', 'NotNull'))>
    /**
     * ${columnFullComment}
     */
    <#if (column.pk)>
    @${annotationName}(groups = {<#if column.autoIncrement>DataEdit.class<#else>DataAdd.class</#if>}, message = "${fieldName} (${columnComment})不能为空")
    <#elseif (!column.notRequired && !column.nullable && (column.columnDefault!"") == "")>
    @${annotationName}(groups = {DataAdd.class, DataEdit.class}, message = "${fieldName} (${columnComment})不能为空")
    </#if>
    private ${fieldType} ${fieldName};

    </#list>

    <#list table.columnsExceptBase as column>
    <#include "/include/column/properties.ftl">
    /**
     * 设置${columnComment}
     */
    public void set${propertyName}(${fieldType} ${fieldName}) {
        this.${fieldName} = ${fieldName};
    }

    /**
     * 获取${columnComment}
     */
    public ${fieldType} get${propertyName}() {
        return ${fieldName};
    }

    </#list>
}
