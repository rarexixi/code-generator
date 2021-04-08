<#include "/include/table/properties.ftl">
package ${modulePackage}.model.request;

import ${modulePackage}.model.BaseEntity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Collection;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ApiModel(description = "${tableComment}更新条件请求实体")
@Getter
@Setter
@ToString
public class ${className}PatchRequest extends BaseEntity {
    <#list pks as column>
    <#include "/include/column/properties.ftl">

    @ApiModelProperty(value = "${columnComment}")
    private ${fieldType} ${fieldName};
    </#list>
    <#if (table.hasUniPk)>

    @ApiModelProperty(value = "${columnComment}列表，多个用英文逗号分开")
    private Collection<${fieldType}> ${fieldName}s;
    </#if>
}
