<#assign className = table.tableClassName>
<#assign classNameLower = table.tableClassName?uncap_first>
<#assign primaryKey = table.primaryKey>
<#assign primaryKeyParameters = table.primaryKeyParameters>
<#assign primaryKeyParameterValues = table.primaryKeyParameterValues>
package ${basePackage}.service.impl;

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
    private ${className}Mapper ${classNameLower}Mapper;

    /**
     * 添加
     *
     * @param entity
     * @return
     <#include "/include/author_info1.ftl">
     */
    @Override
    public int add(${className}Entity entity) {
        int result = super.insert(entity);
        return result;
    }

    /**
     * 添加列表
     *
     * @param list
     * @return
    <#include "/include/author_info1.ftl">
     */
    @Override
    public int addList(List<${className}Entity> list) {
        int result = super.insertList(list);
        return result;
    }
    <#if table.hasPrimaryKey>

    /**
     * 根据主键物理删除
     *
     <#list primaryKey as column>
     * @param ${column.columnFieldNameFirstLower}
     </#list>
     * @return
     <#include "/include/author_info1.ftl">
     */
    @Override
    public int deleteByPk(${primaryKeyParameters}) {

        ${className}Condition condition = getPkCondition(${primaryKeyParameterValues});
        int result = ${classNameLower}Mapper.deleteByCondition(condition);
        return result;
    }
    <#if table.validStatusColumn??>

    /**
     * 根据主键冻结
     *
     <#list primaryKey as column>
     * @param ${column.columnFieldNameFirstLower}
     </#list>
     * @return
     <#include "/include/author_info1.ftl">
     */
    public int disableByPk(${primaryKeyParameters}) {

        ${className}Condition condition = getPkCondition(${primaryKeyParameterValues});
        ${className}Entity entity = new ${className}Entity();
        entity.set${table.validStatusColumn.columnFieldName}(${table.validStatusField.invalidValue});
        int result = ${classNameLower}Mapper.updateByCondition(entity, condition);
        return result;
    }

    /**
     * 根据主键激活
     *
     <#list primaryKey as column>
     * @param ${column.columnFieldNameFirstLower}
     </#list>
     * @return
     <#include "/include/author_info1.ftl">
     */
    public int enableByPk(${primaryKeyParameters}) {

        ${className}Condition condition = getPkCondition(${primaryKeyParameterValues});
        ${className}Entity entity = new ${className}Entity();
        entity.set${table.validStatusColumn.columnFieldName}(${table.validStatusField.validValue});
        int result = ${classNameLower}Mapper.updateByCondition(entity, condition);
        return result;
    }
    </#if>

    /**
     * 根据主键更新
     *
     * @param entity
     * @return
     <#include "/include/author_info1.ftl">
     */
    @Override
    public int updateByPk(${className}Entity entity<#if !table.hasAutoIncrementUniquePrimaryKey>, ${primaryKeyParameters}</#if>) {

        <#if !table.hasAutoIncrementUniquePrimaryKey>
        ${className}Condition condition = getPkCondition(${primaryKeyParameterValues});
        <#else>
        ${className}Condition condition = getPkCondition(<#list primaryKey as column><#if (column_index > 0)>, </#if>entity.get${column.columnFieldName}()</#list>);
        </#if>
        int result = ${classNameLower}Mapper.updateByCondition(entity, condition);
        return result;
    }

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
    public ${className}Vo getByPk(${primaryKeyParameters}) {
        ${className}Vo vo = ${classNameLower}Mapper.getByPk(${primaryKeyParameterValues});
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
    public PageInfo<${className}Vo> findPageList(${className}SelectParameter parameter) {

        if (parameter == null) {
            PageHelper.startPage(SearchPage.DEFAULT_PAGE_INDEX, SearchPage.DEFAULT_PAGE_SIZE);
        } else {
            PageHelper.startPage(parameter.getPageIndex(), parameter.getPageSize());
        }
        List<${className}Vo> list = ${classNameLower}Mapper.findList(parameter);
        PageInfo<${className}Vo> pageInfo = new PageInfo<>(list);
        return pageInfo;
    }

    private ${className}Condition getPkCondition(${primaryKeyParameters}) {

        ${className}Condition condition = new ${className}Condition();
        <#list primaryKey as column>
        condition.set${column.columnFieldName}(${column.columnFieldNameFirstLower});
        </#list>
        return condition;
    }
}
