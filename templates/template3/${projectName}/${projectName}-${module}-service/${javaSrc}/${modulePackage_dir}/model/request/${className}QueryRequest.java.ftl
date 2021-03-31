<#include "/include/table/properties.ftl">
package ${modulePackage}.model.request;

import ${commonPackage}.constant.SortConstants;
import ${commonPackage}.models.QueryRequest;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Collection;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ApiModel(description = "${tableComment}查询条件请求实体")
@Getter
@Setter
@ToString
public class ${className}QueryRequest extends QueryRequest {
    <#list table.indexes as column>
    <#include "/include/column/properties.ftl">
    <#if (canBeEqual)>

    @ApiModelProperty(value = "${columnFullComment}")
    private ${fieldType} ${fieldName};
    </#if>
    <#if (canBeList)>

    @ApiModelProperty(value = "${columnComment}列表")
    private Collection<${fieldType}> ${fieldName}In;
    </#if>
    <#if (canBeRange)>

    @ApiModelProperty(value = "最小${columnComment}")
    private ${fieldType} ${fieldName}Min;

    @ApiModelProperty(value = "最大${columnComment}")
    private ${fieldType} ${fieldName}Max;
    </#if>
    <#if (isString)>

    @ApiModelProperty(value = "${columnComment}包含")
    private ${fieldType} ${fieldName}Contains;
    </#if>
    </#list>
    <#if (table.validStatusColumn??)>

    @ApiModelProperty(value = "${table.validStatusColumn.columnComment}")
    private ${table.validStatusColumn.targetDataType} ${table.validStatusColumn.targetName?uncap_first};
    </#if>
    <#list table.indexes as column>
    <#include "/include/column/properties.ftl">
    <#if (canBeRange)>

    public void set${propertyName}Range(${fieldType}[] ${fieldName}Range)  {
        if (${fieldName}Range == null || ${fieldName}Range.length != 2) {
            return;
        }
        this.${fieldName}Min = ${fieldName}Range[0];
        this.${fieldName}Max = ${fieldName}Range[1];
    }
    </#if>

    public void set${propertyName}Sort(SortConstants sortConstants)  {
        super.orderBy("${column.columnName}", sortConstants);
    }

    public void get${propertyName}Sort()  {
        super.getOrderBy().getOrDefault("${column.columnName}", null);
    }
    </#list>
}
