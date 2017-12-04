<#assign className = table.tableClassName>
<#assign classNameLower = table.tableClassNameFirstLower>
<#assign primaryKey = table.primaryKey>
<#assign primaryKeyParameters = table.primaryKeyParameters>
<#assign primaryKeyParameterValues = table.primaryKeyParameterValues>
package ${basePackage}.service.impl;

import ${paginationFullClass};
import ${basePackage}.condition.${className}Condition;
import ${basePackage}.condition.select.${className}SelectCondition;
import ${basePackage}.entity.${className}Entity;
import ${basePackage}.mapper.${className}Mapper;
import ${basePackage}.parameter.${className}SelectParameter;
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
     * @param ${classNameLower}
     * @return
     <#include "/include/author_info1.ftl">
     */
    @Override
    public int add${className}(${className}Entity ${classNameLower}) {
        return super.insert(${classNameLower});
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
        return super.insertList(${classNameLower}List);
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

        ${className}SelectCondition selectCondition = new ${className}SelectCondition();
        <#list primaryKey as column>
        selectCondition.set${column.columnFieldName}(${column.columnFieldNameFirstLower});
        </#list>
        ${className}Condition condition = new ${className}Condition();
        condition.getSelectConditionList().add(selectCondition);

        return ${classNameLower}Mapper.deleteByCondition(condition);
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

        ${className}SelectCondition selectCondition = new ${className}SelectCondition();
        <#list primaryKey as column>
        selectCondition.set${column.columnFieldName}(${column.columnFieldNameFirstLower});
        </#list>
        ${className}Condition condition = new ${className}Condition();
        condition.getSelectConditionList().add(selectCondition);

        ${className}Entity ${classNameLower} = new ${className}Entity();
        ${classNameLower}.set${table.validStatusColumn.columnFieldName}(${table.validStatusField.invalidValue});

        return ${classNameLower}Mapper.updateByCondition(${classNameLower}, condition);
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

        ${className}SelectCondition selectCondition = new ${className}SelectCondition();
        <#list primaryKey as column>
        selectCondition.set${column.columnFieldName}(${column.columnFieldNameFirstLower});
        </#list>
        ${className}Condition condition = new ${className}Condition();
        condition.getSelectConditionList().add(selectCondition);

        ${className}Entity ${classNameLower} = new ${className}Entity();
        ${classNameLower}.set${table.validStatusColumn.columnFieldName}(${table.validStatusField.validValue});

        return ${classNameLower}Mapper.updateByCondition(${classNameLower}, condition);
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

        ${className}SelectCondition selectCondition = new ${className}SelectCondition();
        <#list primaryKey as column>
        selectCondition.set${column.columnFieldName}(${classNameLower}.get${column.columnFieldName}());
        </#list>
        ${className}Condition condition = new ${className}Condition();
        condition.getSelectConditionList().add(selectCondition);

        return ${classNameLower}Mapper.updateByCondition(${classNameLower}, condition);
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
     * @param pagination
     * @return
     <#include "/include/author_info1.ftl">
     */
    @Transactional(readOnly = true)
    @Override
    public ${paginationClass}<${className}Vo> find${className}PageList(${className}SelectParameter parameter, ${paginationClass} pagination) {

        //先查询总数量
        PageHelper.startPage(pagination.getPage(), pagination.getPageSize());
        //分页查询数据
        List<${className}Vo> list = ${classNameLower}Mapper.find${className}List(parameter);
        PageInfo page = new PageInfo(list);
        pagination.setContent(list);
        pagination.setDataNumber(page.getTotal());

        return pagination;
    }

}
