<#include "/include/table/properties.ftl">
package ${modulePackage}.model.response;

import com.alibaba.excel.annotation.ExcelProperty;
import ${commonPackage}.excel.*;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ApiModel(description = "${tableComment}列表元素响应实体")
@Getter
@Setter
@ToString
public class ${className}ListItemResponse implements Serializable {
    <#assign columnIndex = 0>
    <#list table.columns as column>
    <#include "/include/column/properties.ftl">

    <#if isDate>
    @ExcelProperty(value = "${columnFullComment}", index = ${columnIndex}, converter = LocalDateConverter.class)
    @LocalDateFormat
    <#elseif isTime>
    @ExcelProperty(value = "${columnFullComment}", index = ${columnIndex}, converter = LocalTimeConverter.class)
    @LocalTimeFormat
    <#elseif isDateTime>
    @ExcelProperty(value = "${columnFullComment}", index = ${columnIndex}, converter = LocalDateTimeConverter.class)
    @LocalDateTimeFormat
    <#else>
    @ExcelProperty(value = "${columnFullComment}", index = ${columnIndex})
    </#if>
    @ApiModelProperty(value = "${columnFullComment}", position = ${columnIndex})
    <#assign columnIndex = columnIndex+1>
    private ${fieldType} ${fieldName};
    </#list>
    <#list table.columns as column>
    <#include "/include/column/properties.ftl">

    public ${fieldType} get${propertyName}() {
        return ${fieldName};
    }

    public void set${propertyName}(${fieldType} ${fieldName}) {
        this.${fieldName} = ${fieldName};
    }
    </#list>
}
