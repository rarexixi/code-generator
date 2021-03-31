<#include "/include/table/properties.ftl">
package ${modulePackage}.model.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.math.BigDecimal;
import java.util.Collection;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ApiModel(description = "${tableComment}详情响应实体")
@Getter
@Setter
@ToString
public class ${className}DetailResponse extends ${className}ListItemResponse {
    <#list table.fkSelectColumns as column>
    <#include "/include/column/properties.ftl">

    @ApiModelProperty(value = "${columnComment}")
    private String ${fieldNameExceptKey}Text;
    </#list>
    <#list table.fkSelectColumns as column>
    <#include "/include/column/properties.ftl">

    private void set${propertyExceptKey}Text (String ${fieldNameExceptKey}Text) {
        this.${fieldNameExceptKey}Text = ${fieldNameExceptKey}Text;
    }

    private String get${propertyExceptKey}Text() {
        return ${fieldNameExceptKey}Text;
    }
    </#list>
}
