package ${basePackage}.models.common;

import ${baseCommonPackage}.validation.*;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.util.Date;
import java.io.Serializable;

<#include "/include/java_copyright.ftl">
@Getter
@Setter
@ToString
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
}
