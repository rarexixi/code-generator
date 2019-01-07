<#include "/include/table/properties.ftl">
package ${basePackage}.service.impl;

import ${baseCommonPackage}.mapper.BaseMapper;
import ${baseCommonPackage}.model.SearchPage;

import ${basePackage}.condition.${className}Condition;
import ${basePackage}.condition.extension.${className}ConditionExtension;
import ${basePackage}.entity.${className}Entity;
import ${basePackage}.entity.extension.${className}EntityExtension;
import ${basePackage}.mapper.${className}Mapper;
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
public class ${className}ServiceImpl extends BaseServiceImpl<${className}Entity, ${className}Condition> implements ${className}Service {

    @Autowired
    public ${className}ServiceImpl(${className}Mapper ${classNameFirstLower}Mapper) {
        this.${classNameFirstLower}Mapper = ${classNameFirstLower}Mapper;
        super.mapper = ${classNameFirstLower}Mapper;
    }

    private ${className}Mapper ${classNameFirstLower}Mapper;

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
    @Transactional(readOnly = true)
    @Override
    public ${className}EntityExtension getByPk(<#include "/include/table/pk_params.ftl">) {
        ${className}EntityExtension vo = ${classNameFirstLower}Mapper.getByPk(<#include "/include/table/pk_values.ftl">);
        return vo;
    }
    </#if>

    /**
     * 分页查询
     *
     * @param searchPage
     * @return
     <#include "/include/author_info1.ftl">
     */
    @Transactional(readOnly = true)
    @Override
    public PageInfo<${className}EntityExtension> getPageList(SearchPage<${className}ConditionExtension> searchPage) {

        int pageIndex = searchPage == null || searchPage.getPageIndex() == null ? SearchPage.DEFAULT_PAGE_INDEX : searchPage.getPageIndex();
        int pageSize = searchPage == null || searchPage.getPageSize() == null ? SearchPage.DEFAULT_PAGE_SIZE : searchPage.getPageSize();
        PageHelper.startPage(pageIndex, pageSize);
        List<${className}EntityExtension> list = ${classNameFirstLower}Mapper.getList(searchPage.getCondition());
        PageInfo<${className}EntityExtension> pageInfo = new PageInfo<>(list);
        return pageInfo;
    }

    /**
     * 获取列表（不分页）
     *
     * @param parameter
     * @return
     <#include "/include/author_info1.ftl">
     */
    @Transactional(readOnly = true)
    @Override
    public List<${className}Vo> getList(${className}ConditionExtension condition) {

        List<${className}Vo> list = ${classNameFirstLower}Mapper.getList(condition);
        return list;
    }
}
