package ${modulePackage}.model;

import io.swagger.annotations.ApiModelProperty;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

<#include "/include/java_copyright.ftl">
@Getter
@Setter
@ToString
public class BaseEntity implements Serializable {
    <#list baseColumns as column>
    <#include "/include/column/properties.ftl">

    /**
     * ${columnFullComment}
     */
    @ApiModelProperty(hidden = true)
    protected ${fieldType} ${fieldName};
    </#list>
}
