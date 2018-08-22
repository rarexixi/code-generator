<#assign className = table.tableClassName>
<#assign classNameLower = table.tableClassName?uncap_first>
<#assign primaryKey = table.primaryKey>
<#assign primaryKeyParameters = table.primaryKeyParameters>
<#assign primaryKeyParameterValues = table.primaryKeyParameterValues>
package ${basePackage}.api.impl;

import ${baseCommonPackage}.model.ResultVo;
import ${baseCommonPackage}.model.SearchPage;
import ${baseCommonPackage}.utils.LogUtils;
import ${baseCommonPackage}.validation.DataAdd;
import ${baseCommonPackage}.validation.DataEdit;
import ${basePackage}.api.${className}Api;
import ${basePackage}.condition.${className}Condition;
import ${basePackage}.entity.${className}Entity;
import ${basePackage}.parameter.${className}SelectParameter;
import ${basePackage}.service.${className}Service;
import ${basePackage}.vo.${className}Vo;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageInfo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;
import java.util.List;

<#include "/include/java_copyright.ftl">
@Service
public class ${className}ApiImpl implements ${className}Api {

    private static LogUtils logger = LogUtils.build(${className}ApiImpl.class);

    @Autowired
    private ${className}Service ${classNameLower}Service;

    /**
     * 添加
     *
     * @param entity
     * @param sessionId
     * @return
     <#include "/include/author_info1.ftl">
     */
    @Override
    public ResultVo<${className}Entity> add(@Validated({DataAdd.class}) ${className}Entity entity, String sessionId) {

        int count = ${classNameLower}Service.insert(entity);
        ResultVo<${className}Entity> result = new ResultVo<>(entity);
        return result;
    }

    /**
     * 添加列表
     *
     * @param list
     * @param sessionId
     * @return
    <#include "/include/author_info1.ftl">
     */
    @Override
    public ResultVo<Integer> addList(@Validated({DataAdd.class}) List<${className}Entity> list, String sessionId) {

        int count = ${classNameLower}Service.insertList(list);
        ResultVo<Integer> result = new ResultVo<>(count);
        return result;
    }
    <#if table.hasPrimaryKey>

    /**
     * 根据主键物理删除
     *
     <#list primaryKey as column>
     * @param ${column.columnFieldNameFirstLower}
     </#list>
     * @param sessionId
     * @return
     <#include "/include/author_info1.ftl">
     */
    @Override
    public ResultVo<Integer> deleteByPk(<#list primaryKey as column>@NotNull ${column.columnFieldType} ${column.columnFieldName?uncap_first}, </#list>String sessionId) {

        ${className}Condition condition = getPkCondition(${primaryKeyParameterValues});
        int count = ${classNameLower}Service.deleteByCondition(condition);
        ResultVo<Integer> result = new ResultVo<>(count);
        return result;
    }
    <#if (table.uniquePrimaryKey??)>

    /**
     * 根据主键列表物理删除
     *
     * @param ${table.uniquePrimaryKey.columnFieldName?uncap_first}List
     * @param sessionId
     * @return
     <#include "/include/author_info1.ftl">
     */
    @Override
    public ResultVo<Integer> deleteByPkList(@NotNull List<${table.uniquePrimaryKey.columnFieldType}> ${table.uniquePrimaryKey.columnFieldName?uncap_first}List, String sessionId) {

        ${className}Condition condition = getPkListCondition(${table.uniquePrimaryKey.columnFieldName?uncap_first}List);
        int count = ${classNameLower}Service.deleteByCondition(condition);
        ResultVo<Integer> result = new ResultVo<>(count);
        return result;
    }
    </#if>
    <#if table.validStatusColumn??>

    /**
     * 根据主键冻结
     *
     <#list primaryKey as column>
     * @param ${column.columnFieldNameFirstLower}
     </#list>
     * @param sessionId
     * @return
     <#include "/include/author_info1.ftl">
     */
    @Override
    public ResultVo<Integer> disableByPk(<#list primaryKey as column>@NotNull ${column.columnFieldType} ${column.columnFieldName?uncap_first}, </#list>String sessionId) {

        ${className}Condition condition = getPkCondition(${primaryKeyParameterValues});
        ${className}Entity entity = new ${className}Entity();
        entity.set${table.validStatusColumn.columnFieldName}(${table.validStatusField.invalidValue});
        int count = ${classNameLower}Service.updateByCondition(entity, condition);
        ResultVo<Integer> result = new ResultVo<>(count);
        return result;
    }

    /**
     * 根据主键激活
     *
     <#list primaryKey as column>
     * @param ${column.columnFieldNameFirstLower}
     </#list>
     * @param sessionId
     * @return
     <#include "/include/author_info1.ftl">
     */
    @Override
    public ResultVo<Integer> enableByPk(<#list primaryKey as column>@NotNull ${column.columnFieldType} ${column.columnFieldName?uncap_first}, </#list>String sessionId) {

        ${className}Condition condition = getPkCondition(${primaryKeyParameterValues});
        ${className}Entity entity = new ${className}Entity();
        entity.set${table.validStatusColumn.columnFieldName}(${table.validStatusField.validValue});
        int count = ${classNameLower}Service.updateByCondition(entity, condition);
        ResultVo<Integer> result = new ResultVo<>(count);
        return result;
    }
    <#if (table.uniquePrimaryKey??)>

    /**
     * 根据主键列表冻结
     *
     * @param ${table.uniquePrimaryKey.columnFieldName?uncap_first}List
     * @param sessionId
     * @return
     <#include "/include/author_info1.ftl">
     */
    @Override
    public ResultVo<Integer> disableByPkList(@NotNull List<${table.uniquePrimaryKey.columnFieldType}> ${table.uniquePrimaryKey.columnFieldName?uncap_first}List, String sessionId) {

        ${className}Condition condition = getPkListCondition(${table.uniquePrimaryKey.columnFieldName?uncap_first}List);
        ${className}Entity entity = new ${className}Entity();
        entity.set${table.validStatusColumn.columnFieldName}(${table.validStatusField.invalidValue});
        int count = ${classNameLower}Service.updateByCondition(entity, condition);
        ResultVo<Integer> result = new ResultVo<>(count);
        return result;
    }

    /**
     * 根据主键列表激活
     *
     * @param ${table.uniquePrimaryKey.columnFieldName?uncap_first}List
     * @param sessionId
     * @return
     <#include "/include/author_info1.ftl">
     */
    @Override
    public ResultVo<Integer> enableByPkList(@NotNull List<${table.uniquePrimaryKey.columnFieldType}> ${table.uniquePrimaryKey.columnFieldName?uncap_first}List, String sessionId) {

        ${className}Condition condition = getPkListCondition(${table.uniquePrimaryKey.columnFieldName?uncap_first}List);
        ${className}Entity entity = new ${className}Entity();
        entity.set${table.validStatusColumn.columnFieldName}(${table.validStatusField.validValue});
        int count = ${classNameLower}Service.updateByCondition(entity, condition);
        ResultVo<Integer> result = new ResultVo<>(count);
        return result;
    }
    </#if>
    </#if>

    /**
     * 根据主键更新
     *
     * @param entity
     * @param sessionId
     * @return
     <#include "/include/author_info1.ftl">
     */
    @Override
    public ResultVo<Integer> updateByPk(@Validated({DataEdit.class}) ${className}Entity entity<#if !table.hasAutoIncrementUniquePrimaryKey><#list primaryKey as column>, @NotNull ${column.columnFieldType} old${column.columnFieldName}</#list></#if>, String sessionId) {

        <#if !table.hasAutoIncrementUniquePrimaryKey>
        ${className}Condition condition = getPkCondition(<#if !table.hasAutoIncrementUniquePrimaryKey><#list primaryKey as column><#if (column_index > 0)>, </#if>old${column.columnFieldName}</#list></#if>);
        <#else>
        ${className}Condition condition = getPkCondition(<#list primaryKey as column><#if (column_index > 0)>, </#if>entity.get${column.columnFieldName}()</#list>);
        </#if>
        int count = ${classNameLower}Service.updateByCondition(entity, condition);
        ResultVo<Integer> result = new ResultVo<>(count);
        return result;
    }

    /**
     * 根据主键获取
     *
     <#list primaryKey as column>
     * @param ${column.columnFieldNameFirstLower}
     </#list>
     * @param sessionId
     * @return
     <#include "/include/author_info1.ftl">
     */
    @Override
    public ResultVo<${className}Vo> getByPk(<#list primaryKey as column>@NotNull ${column.columnFieldType} ${column.columnFieldName?uncap_first}, </#list>String sessionId) {

        ${className}Vo vo = ${classNameLower}Service.getByPk(${primaryKeyParameterValues});
        ResultVo<${className}Vo> result = new ResultVo<>(vo);
        return result;
    }
    </#if>

    /**
     * 分页查询
     *
     * @param parameter
     * @param sessionId
     * @return
     <#include "/include/author_info1.ftl">
     */
    @Override
    public ResultVo<PageInfo<${className}Vo>> findPageList(${className}SelectParameter parameter, String sessionId) {

        PageInfo<${className}Vo> paginationVo = ${classNameLower}Service.findPageList(parameter);
        ResultVo<PageInfo<${className}Vo>> result = new ResultVo<>(paginationVo);
        return result;
    }

    private ${className}Condition getPkCondition(${primaryKeyParameters}) {

        ${className}Condition condition = new ${className}Condition();
        <#list primaryKey as column>
        condition.set${column.columnFieldName}(${column.columnFieldNameFirstLower});
        </#list>
        return condition;
    }
    <#if (table.uniquePrimaryKey??)>

    private ${className}Condition getPkListCondition(List<${table.uniquePrimaryKey.columnFieldType}> ${table.uniquePrimaryKey.columnFieldName?uncap_first}List) {

        ${className}Condition condition = new ${className}Condition();
        condition.set${table.uniquePrimaryKey.columnFieldName}List(${table.uniquePrimaryKey.columnFieldName?uncap_first}List);
        return condition;
    }
    </#if>

}
