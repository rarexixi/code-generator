<#include "/include/table/properties.ftl">
package ${basePackage}.service;

import ${baseCommonPackage}.model.SearchPage;

import ${basePackage}.condition.${className}Condition;
import ${basePackage}.condition.extension.${className}ConditionExtension;
import ${basePackage}.entity.${className}Entity;
import ${basePackage}.entity.extension.${className}EntityExtension;

import com.github.pagehelper.PageInfo;

import java.util.List;

<#include "/include/java_copyright.ftl">
public interface ${className}Service extends BaseService<${className}Entity, ${className}Condition> {

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
    ${className}EntityExtension getByPk(<#include "/include/table/pk_params.ftl">);
    </#if>

    /**
     * 分页查询
     *
     * @param searchPage
     * @return
     <#include "/include/author_info1.ftl">
     */
    PageInfo<${className}EntityExtension> getPageList(SearchPage<${className}ConditionExtension> searchPage);

    /**
     * 获取列表（不分页）
     *
     * @param condition
     * @return
     <#include "/include/author_info1.ftl">
     */
    List<${className}EntityExtension> getList(${className}ConditionExtension condition);
}
