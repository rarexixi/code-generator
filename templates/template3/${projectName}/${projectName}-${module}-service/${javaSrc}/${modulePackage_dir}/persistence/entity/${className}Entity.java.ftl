<#include "/include/table/properties.ftl">
package ${modulePackage}.persistence.entity;

import ${modulePackage}.model.BaseEntity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Collection;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * ${table.comment}实体
 *
 * @author ${author}
 */
@Getter
@Setter
@ToString
public class ${className}Entity extends BaseEntity {
    <#list table.columnsExceptBase as column>
    <#include "/include/column/properties.ftl">

    /**
     * ${columnFullComment}
     */
    private ${fieldType} ${fieldName};
    </#list>
}
