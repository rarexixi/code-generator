<#assign className = table.tableClassName>
<#assign classNameLower = table.tableClassNameFirstLower>
<#assign primaryKey = table.primaryKey>
<#assign primaryKeyParameters = table.primaryKeyParameters>
<#assign primaryKeyParameterValues = table.primaryKeyParameterValues>
package ${basePackage}.quickprovider.service.impl;

import org.xi.common.model.SearchPage;
import ${basePackage}.condition.${className}Condition;
import ${basePackage}.condition.select.${className}SelectCondition;
import ${basePackage}.entity.${className}Entity;
import ${basePackage}.parameter.${className}SelectParameter;
import ${basePackage}.quickprovider.mapper.${className}Mapper;
import ${basePackage}.quickprovider.service.${className}Service;
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
     * @param ${classNameLower}
     * @return
     <#include "/include/author_info1.ftl">
     */
    @Override
    public int add${className}(${className}Entity ${classNameLower}) {
        int result = super.insert(${classNameLower});
        return result;
    }

    /**
     * 添加列表
     *
     * @param ${classNameLower}List
     * @return
    <#include "/include/author_info1.ftl">
     */
    @Override
    public int add${className}List(List<${className}Entity> ${classNameLower}List) {
        int result = super.insertList(${classNameLower}List);
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
    public int delete${className}ByPk(${primaryKeyParameters}) {

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
    public int disable${className}ByPk(${primaryKeyParameters}) {

        ${className}Condition condition = getPkCondition(${primaryKeyParameterValues});
        ${className}Entity ${classNameLower} = new ${className}Entity();
        ${classNameLower}.set${table.validStatusColumn.columnFieldName}(${table.validStatusField.invalidValue});
        int result = ${classNameLower}Mapper.updateByCondition(${classNameLower}, condition);
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
    public int enable${className}ByPk(${primaryKeyParameters}) {

        ${className}Condition condition = getPkCondition(${primaryKeyParameterValues});
        ${className}Entity ${classNameLower} = new ${className}Entity();
        ${classNameLower}.set${table.validStatusColumn.columnFieldName}(${table.validStatusField.validValue});
        int result = ${classNameLower}Mapper.updateByCondition(${classNameLower}, condition);
        return result;
    }
    </#if>

    /**
     * 根据主键更新
     *
     * @param ${classNameLower}
     * @return
     <#include "/include/author_info1.ftl">
     */
    @Override
    public int update${className}ByPk(${className}Entity ${classNameLower}) {

        ${className}Condition condition = getPkCondition(<#list primaryKey as column><#if (column_index > 0)>, </#if>${classNameLower}.get${column.columnFieldName}()</#list>);
        int result = ${classNameLower}Mapper.updateByCondition(${classNameLower}, condition);
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
    public ${className}Vo get${className}ByPk(${primaryKeyParameters}) {
        ${className}Vo vo = ${classNameLower}Mapper.get${className}ByPk(${primaryKeyParameterValues});
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
    public PageInfo<${className}Vo> find${className}PageList(${className}SelectParameter parameter) {

        if (parameter == null) {
            PageHelper.startPage(SearchPage.DEFAULT_PAGE_INDEX, SearchPage.DEFAULT_PAGE_SIZE);
        } else {
            PageHelper.startPage(parameter.getPageIndex(), parameter.getPageSize());
        }
        List<${className}Vo> list = ${classNameLower}Mapper.find${className}List(parameter);
        PageInfo<${className}Vo> pageInfo = new PageInfo<>(list);
        return pageInfo;
    }

    private ${className}Condition getPkCondition(${primaryKeyParameters}) {

        ${className}SelectCondition selectCondition = new ${className}SelectCondition();
        <#list primaryKey as column>
        selectCondition.set${column.columnFieldName}(${column.columnFieldNameFirstLower});
        </#list>
        ${className}Condition condition = new ${className}Condition();
        condition.getSelectConditionList().add(selectCondition);
        return condition;
    }
}
