<#include "/include/table/properties.ftl">
package ${basePackage}.vm.addoredit;

import ${baseCommonPackage}.validation.*;
import ${basePackage}.models.entity.${className}Entity;

import lombok.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.*;

<#include "/include/java_copyright.ftl">
@NoArgsConstructor
@Getter
@Setter
@ToString
public class ${className}AddOrEditVm implements Serializable {
    <#list table.requiredColumns as column>
    <#include "/include/column/properties.ftl">

    /**
     * ${columnFullComment}
     */
    <#assign annotationName = ((fieldType == 'String') ? string('NotBlank', 'NotNull'))>
    <#if (column.pk)>
    @${annotationName}(groups = {<#if column.autoIncrement>DataEdit.class<#else>DataAdd.class</#if>}, message = "${fieldName} (${columnComment})不能为空")
    <#elseif (!column.notRequired && !column.nullable && !(column.columnDefault??))>
    @${annotationName}(groups = {DataAdd.class, DataEdit.class}, message = "${fieldName} (${columnComment})不能为空")
    </#if>
    private ${fieldType} ${fieldName};
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

        if (entity == null) return;

        <#list table.requiredColumns as column>
        <#include "/include/column/properties.ftl">
        this.set${propertyName}(entity.get${propertyName}());
        </#list>
    }
}
