package ${basePackage}.models.entity;

import ${baseCommonPackage}.validation.*;

import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.util.Date;
import java.io.Serializable;

<#include "/include/java_copyright.ftl">
public class BaseEntity implements Serializable {

    <#list baseColumns as column>
    <#include "/include/column/properties.ftl">
    <#assign annotationName = ((fieldType == 'String') ? string('NotBlank', 'NotNull'))>
    /**
     * ${columnFullComment}
     */
    <#if (column.pk)>
    @${annotationName}(groups = {<#if column.autoIncrement>DataEdit.class<#else>DataAdd.class</#if>}, message = "${fieldName} (${columnComment})不能为空")
    <#elseif (!column.notRequired && (!column.nullable && !(column.columnDefault??)))>
    @${annotationName}(groups = {DataAdd.class, DataEdit.class}, message = "${fieldName} (${columnComment})不能为空")
    </#if>
    protected ${fieldType} ${fieldName};

    </#list>

    <#list baseColumns as column>
    <#include "/include/column/properties.ftl">
    /**
     * 设置${column.columnComment}
     */
    public void set${propertyName}(${fieldType} ${fieldName}) {
        this.${fieldName} = ${fieldName};
    }

    /**
     * 获取${column.columnComment}
     */
    public ${fieldType} get${propertyName}() {
        return ${fieldName};
    }

    </#list>
}
