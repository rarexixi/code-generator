<#include "/include/table/properties.ftl">
package ${basePackage}.service.impl;

import ${baseCommonPackage}.mapper.BaseMapper;
import ${baseCommonPackage}.model.SearchPage;
import ${basePackage}.condition.${className}Condition;
import ${basePackage}.entity.${className}Entity;
import ${basePackage}.parameter.${className}SelectParameter;
import ${basePackage}.mapper.${className}Mapper;
import ${basePackage}.service.${className}Service;
import ${basePackage}.vo.${className}Vo;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

<#include "/include/java_copyright.ftl">
@Service("${classNameLower}Service")
@Transactional
public class ${className}ServiceImpl extends BaseServiceImpl<${className}Entity, ${className}Condition> implements ${className}Service {

    @Autowired
    public ${className}ServiceImpl(${className}Mapper ${classNameLower}Mapper) {
        this.${classNameLower}Mapper = ${classNameLower}Mapper;
        super.mapper = ${classNameLower}Mapper;
    }

    private ${className}Mapper ${classNameLower}Mapper;

    <#if table.hasPrimaryKey>

    /**
     * 根据主键获取
     *
     <#list primaryKey as column>
     * @param ${column.columnFieldNameFirstLower}
     </#list>
     * @return
     <#include "/include/author_info1.ftl">
     */
    @Transactional(readOnly = true)
    @Override
    public ${className}Vo getByPk(<#include "/include/table/primary_parameters.ftl">) {
        ${className}Vo vo = ${classNameLower}Mapper.getByPk(<#include "/include/table/primary_values.ftl">);
        return vo;
    }
    </#if>

    /**
     * 分页查询
     *
     * @param parameter
     * @return
     <#include "/include/author_info1.ftl">
     */
    @Transactional(readOnly = true)
    @Override
    public PageInfo<${className}Vo> getPageList(${className}SelectParameter parameter) {

        if (parameter == null) {
            PageHelper.startPage(SearchPage.DEFAULT_PAGE_INDEX, SearchPage.DEFAULT_PAGE_SIZE);
        } else {
            PageHelper.startPage(parameter.getPageIndex(), parameter.getPageSize());
        }
        List<${className}Vo> list = ${classNameLower}Mapper.getList(parameter);
        PageInfo<${className}Vo> pageInfo = new PageInfo<>(list);
        return pageInfo;
    }

}
