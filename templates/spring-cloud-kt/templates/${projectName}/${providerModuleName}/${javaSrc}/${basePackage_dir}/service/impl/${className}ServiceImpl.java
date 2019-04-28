<#include "/include/table/properties.ftl">
package ${basePackage}.service.impl;

import ${baseCommonPackage}.model.OrderSearch;
import ${baseCommonPackage}.model.OrderSearchPage;

import ${basePackage}.common.service.impl.BaseServiceImpl;
import ${basePackage}.mapper.${className}Mapper;
import ${basePackage}.models.condition.${className}Condition;
import ${basePackage}.models.condition.extension.${className}ConditionExtension;
import ${basePackage}.models.condition.order.${className}OrderCondition;
import ${basePackage}.models.entity.${className}Entity;
import ${basePackage}.models.entity.extension.${className}EntityExtension;
import ${basePackage}.service.${className}Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

<#include "/include/java_copyright.ftl">
@Service("${classNameFirstLower}Service")
@Transactional
public class ${className}ServiceImpl extends BaseServiceImpl<${className}Entity, ${className}Condition, ${className}OrderCondition> implements ${className}Service {

    @Autowired
    public ${className}ServiceImpl(${className}Mapper ${classNameFirstLower}Mapper) {
        this.${classNameFirstLower}Mapper = ${classNameFirstLower}Mapper;
        super.mapper = ${classNameFirstLower}Mapper;
    }

    private ${className}Mapper ${classNameFirstLower}Mapper;

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
    @Transactional(readOnly = true)
    @Override
    public ${className}EntityExtension getByPk(<#include "/include/table/pk_params.ftl">) {
        ${className}EntityExtension vo = ${classNameFirstLower}Mapper.getByPk(<#include "/include/table/pk_values.ftl">);
        return vo;
    }
    </#if>

    /**
     * 获取列表（不分页）
     *
     * @param search
     * @return
     */
    @Transactional(readOnly = true)
    @Override
    public List<${className}EntityExtension> getExList(OrderSearch<${className}ConditionExtension, ${className}OrderCondition> search) {

        if (search == null) return null;

        List<${className}EntityExtension> list = ${classNameFirstLower}Mapper.getExList(search.getCondition(), search.getOrder());
        return list;
    }

    /**
     * 分页查询
     *
     * @param searchPage
     * @return
     */
    @Transactional(readOnly = true)
    @Override
    public PageInfo<${className}EntityExtension> getPageList(OrderSearchPage<${className}ConditionExtension, ${className}OrderCondition> searchPage) {

        ${className}ConditionExtension condition;
        if (searchPage == null || (condition = searchPage.getCondition()) == null) return null;

        PageHelper.startPage(searchPage.getPageIndex(), searchPage.getPageSize());
        List<${className}EntityExtension> list = ${classNameFirstLower}Mapper.getExList(condition, searchPage.getOrder());
        PageInfo<${className}EntityExtension> pageInfo = new PageInfo<>(list);
        return pageInfo;
    }
}
