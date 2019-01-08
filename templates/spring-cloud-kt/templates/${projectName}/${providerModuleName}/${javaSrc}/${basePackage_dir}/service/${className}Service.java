<#include "/include/table/properties.ftl">
package ${basePackage}.service;

import ${baseCommonPackage}.model.OrderSearchPage;

import ${basePackage}.common.service.BaseService;
import ${basePackage}.models.condition.${className}Condition;
import ${basePackage}.models.condition.extension.${className}ConditionExtension;
import ${basePackage}.models.condition.order.${className}OrderCondition;
import ${basePackage}.models.entity.${className}Entity;
import ${basePackage}.models.entity.extension.${className}EntityExtension;

import com.github.pagehelper.PageInfo;

import java.util.List;

<#include "/include/java_copyright.ftl">
public interface ${className}Service extends BaseService<${className}Entity, ${className}Condition> {

    <#if table.hasPk>

    /**
     * 根据主键获取${tableComment}详情
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
     * 获取${tableComment}列表（不分页）
     *
     * @param condition
     * @return
     <#include "/include/author_info1.ftl">
     */
    List<${className}EntityExtension> getList(${className}ConditionExtension condition);

    /**
     * 分页查询${tableComment}
     *
     * @param searchPage
     * @return
     <#include "/include/author_info1.ftl">
     */
    PageInfo<${className}EntityExtension> getPageList(OrderSearchPage<${className}ConditionExtension, ${className}OrderCondition> searchPage);
}
