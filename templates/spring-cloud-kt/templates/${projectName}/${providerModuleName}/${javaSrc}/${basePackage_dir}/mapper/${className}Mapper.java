<#include "/include/table/properties.ftl">
package ${basePackage}.mapper;

import ${baseCommonPackage}.mapper.BaseMapper;
import ${basePackage}.condition.${className}Condition;
import ${basePackage}.entity.${className}Entity;
import ${basePackage}.condition.extension.${className}ConditionExtension;
import ${basePackage}.entity.extension.${className}EntityExtension;

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
    ${className}EntityExtension getByPk(<#include "/include/table/properties.ftl">);
    </#if>

    /**
     * 查询
     *
     * @param parameter
     * @return
    <#include "/include/author_info1.ftl">
     */
    List<${className}EntityExtension> getList(@Param("condition") ${className}ConditionExtension condition);
}
