<#include "/include/table/properties.ftl">
package ${basePackage}.service;

import ${basePackage}.condition.${className}Condition;
import ${basePackage}.entity.${className}Entity;
import ${basePackage}.parameter.${className}SelectParameter;
import ${basePackage}.vo.${className}Vo;

import com.github.pagehelper.PageInfo;

import java.util.List;

<#include "/include/java_copyright.ftl">
public interface ${className}Service extends BaseService<${className}Entity, ${className}Condition> {

    <#if table.hasPrimaryKey>

    /**
     * 根据主键获取
     *
     <#list primaryKey as column>
     * @param ${column.targetColumnNameFirstLower}
     </#list>
     * @return
     <#include "/include/author_info1.ftl">
     */
    ${className}Vo getByPk(<#include "/include/table/primary_parameters.ftl">);
    </#if>

    /**
     * 分页查询
     *
     * @param parameter
     * @return
     <#include "/include/author_info1.ftl">
     */
    PageInfo<${className}Vo> getPageList(${className}SelectParameter parameter);

}
