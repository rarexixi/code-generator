<#include "/include/table/properties.ftl">
package ${basePackage}.mapper;

import ${basePackage}.common.mapper.BaseMapper;
import ${basePackage}.common.mapper.BaseMapperExtension;
import ${basePackage}.models.condition.${className}Condition;
import ${basePackage}.models.condition.extension.${className}ConditionExtension;
import ${basePackage}.models.condition.order.${className}OrderCondition;
import ${basePackage}.models.entity.${className}Entity;
import ${basePackage}.models.entity.extension.${className}EntityExtension;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

<#include "/include/java_copyright.ftl">
@Mapper
public interface ${className}Mapper extends BaseMapper<${className}Entity, ${className}Condition>,
        BaseMapperExtension<${className}EntityExtension, ${className}ConditionExtension, ${className}OrderCondition>{
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
}
