<#include "/include/table/properties.ftl">
package ${basePackage}.service;

import ${basePackage}.condition.${className}Condition;
import ${basePackage}.entity.${className}Entity;
import ${basePackage}.condition.extension.${className}ConditionExtension;
import ${basePackage}.entity.extension.${className}EntityExtension;

import com.github.pagehelper.PageInfo;

import java.util.List;

<#include "/include/java_copyright.ftl">
public interface ${className}Service extends BaseService<${className}Entity, ${className}Condition> {

    <#if table.hasPrimaryKey>

    /**
     * 根据主键获取
     *
     <#list pks as column>
     * @param ${fieldName}
     </#list>
     * @return
     <#include "/include/author_info1.ftl">
     */
    ${className}EntityExtension getByPk(<#include "/include/table/primary_parameters.ftl">);
    </#if>

    /**
     * 分页查询
     *
     * @param parameter
     * @return
     <#include "/include/author_info1.ftl">
     */
    PageInfo<${className}EntityExtension> getPageList(${className}ConditionExtension parameter);

}
