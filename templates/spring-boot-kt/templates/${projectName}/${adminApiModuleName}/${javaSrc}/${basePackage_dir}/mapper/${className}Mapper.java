<#include "/include/table/properties.ftl">
package ${basePackage}.mapper;

import ${baseCommonPackage}.mapper.BaseMapper;
import ${basePackage}.models.condition.${className}Condition;
import ${basePackage}.models.condition.extension.${className}ConditionExtension;
import ${basePackage}.models.condition.order.${className}OrderCondition;
import ${basePackage}.models.entity.${className}Entity;
import ${basePackage}.models.entity.extension.${className}EntityExtension;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

<#include "/include/java_copyright.ftl">
@Mapper
public interface ${className}Mapper extends BaseMapper<${className}Entity, ${className}Condition> {
    <#if table.hasPk>

    /**
     * 根据主键获取
     *
     <#list pks as column>
     <#include "/include/column/properties.ftl">
     * @param ${fieldName}
     </#list>
     * @return
    <#include "/include/author_info1.ftl">
     */
    ${className}EntityExtension getByPk(<#include "/include/table/pk_params_mapper.ftl">);
    </#if>

    /**
     * 查询
     *
     * @param condition
     * @param order
     * @return
     */
    List<${className}EntityExtension> getExList(@Param("condition") ${className}ConditionExtension condition, @Param("order") ${className}OrderCondition order);
}
