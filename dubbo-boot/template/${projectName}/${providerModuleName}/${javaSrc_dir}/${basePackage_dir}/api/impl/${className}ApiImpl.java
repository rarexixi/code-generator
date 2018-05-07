<#assign className = table.tableClassName>
<#assign classNameLower = table.tableClassName?uncap_first>
<#assign primaryKey = table.primaryKey>
<#assign primaryKeyParameters = table.primaryKeyParameters>
<#assign primaryKeyParameterValues = table.primaryKeyParameterValues>
package ${basePackage}.api.impl;

import ${baseCommonPackage}.constant.OperationConstants;
import ${baseCommonPackage}.model.Result;
import ${baseCommonPackage}.model.SearchPage;
import ${baseCommonPackage}.utils.LogUtils;
import ${baseCommonPackage}.utils.database.OperationCheckUtils;
import ${basePackage}.api.${className}Api;
import ${basePackage}.condition.${className}Condition;
import ${basePackage}.entity.${className}Entity;
import ${basePackage}.parameter.${className}SelectParameter;
import ${basePackage}.service.${className}Service;
import ${basePackage}.vo.${className}Vo;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageInfo;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

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
    public Result<Integer> add(${className}Entity entity, String sessionId) {

        String fieldName = "";
        if (entity == null || !StringUtils.isEmpty(fieldName = OperationCheckUtils.checkInsert(entity))) {
            return new Result<>(OperationConstants.NOT_NULL.getCode(), fieldName + OperationConstants.NOT_NULL.getMessage());
        }

        int count = ${classNameLower}Service.insert(entity);
        Result<Integer> result = new Result<>(count);
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
    public Result<Integer> addList(List<${className}Entity> list, String sessionId) {

        if (list == null || list.isEmpty()) {
            return new Result<>(OperationConstants.NOT_NULL);
        }
        String fieldName;
        for (${className}Entity entity : list) {
            if (!StringUtils.isEmpty(fieldName = OperationCheckUtils.checkInsert(entity))) {
                return new Result<>(OperationConstants.NOT_NULL.getCode(), fieldName + OperationConstants.NOT_NULL.getMessage());
            }
        }

        int count = ${classNameLower}Service.insertList(list);
        Result<Integer> result = new Result<>(count);
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
    public Result<Integer> deleteByPk(${primaryKeyParameters}, String sessionId) {

        if (OperationCheckUtils.isNullOrEmpty(${primaryKeyParameterValues})) {
            return new Result<>(OperationConstants.NOT_NULL);
        }

        ${className}Condition condition = getPkCondition(${primaryKeyParameterValues});
        int count = ${classNameLower}Service.deleteByCondition(condition);
        Result<Integer> result = new Result<>(count);
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
    public Result<Integer> deleteByPkList(List<${table.uniquePrimaryKey.columnFieldType}> ${table.uniquePrimaryKey.columnFieldName?uncap_first}List, String sessionId) {

        if (OperationCheckUtils.isNullOrEmpty(${table.uniquePrimaryKey.columnFieldName?uncap_first}List)) {
            return new Result<>(OperationConstants.NOT_NULL);
        }

        ${className}Condition condition = getPkListCondition(${table.uniquePrimaryKey.columnFieldName?uncap_first}List);
        int count = ${classNameLower}Service.deleteByCondition(condition);
        Result<Integer> result = new Result<>(count);
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
    public Result<Integer> disableByPk(${primaryKeyParameters}, String sessionId) {

        if (OperationCheckUtils.isNullOrEmpty(${primaryKeyParameterValues})) {
            return new Result<>(OperationConstants.NOT_NULL);
        }

        ${className}Condition condition = getPkCondition(${primaryKeyParameterValues});
        ${className}Entity entity = new ${className}Entity();
        entity.set${table.validStatusColumn.columnFieldName}(${table.validStatusField.invalidValue});
        int count = ${classNameLower}Service.updateByCondition(entity, condition);
        Result<Integer> result = new Result<>(count);
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
    public Result<Integer> enableByPk(${primaryKeyParameters}, String sessionId) {

        if (OperationCheckUtils.isNullOrEmpty(${primaryKeyParameterValues})) {
            return new Result<>(OperationConstants.NOT_NULL);
        }

        ${className}Condition condition = getPkCondition(${primaryKeyParameterValues});
        ${className}Entity entity = new ${className}Entity();
        entity.set${table.validStatusColumn.columnFieldName}(${table.validStatusField.validValue});
        int count = ${classNameLower}Service.updateByCondition(entity, condition);
        Result<Integer> result = new Result<>(count);
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
    public Result<Integer> disableByPkList(List<${table.uniquePrimaryKey.columnFieldType}> ${table.uniquePrimaryKey.columnFieldName?uncap_first}List, String sessionId) {

        if (OperationCheckUtils.isNullOrEmpty(${table.uniquePrimaryKey.columnFieldName?uncap_first}List)) {
            return new Result<>(OperationConstants.NOT_NULL);
        }

        ${className}Condition condition = getPkListCondition(${table.uniquePrimaryKey.columnFieldName?uncap_first}List);
        ${className}Entity entity = new ${className}Entity();
        entity.set${table.validStatusColumn.columnFieldName}(${table.validStatusField.invalidValue});
        int count = ${classNameLower}Service.updateByCondition(entity, condition);
        Result<Integer> result = new Result<>(count);
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
    public Result<Integer> enableByPkList(List<${table.uniquePrimaryKey.columnFieldType}> ${table.uniquePrimaryKey.columnFieldName?uncap_first}List, String sessionId) {

        if (OperationCheckUtils.isNullOrEmpty(${table.uniquePrimaryKey.columnFieldName?uncap_first}List)) {
            return new Result<>(OperationConstants.NOT_NULL);
        }

        ${className}Condition condition = getPkListCondition(${table.uniquePrimaryKey.columnFieldName?uncap_first}List);
        ${className}Entity entity = new ${className}Entity();
        entity.set${table.validStatusColumn.columnFieldName}(${table.validStatusField.validValue});
        int count = ${classNameLower}Service.updateByCondition(entity, condition);
        Result<Integer> result = new Result<>(count);
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
    public Result<Integer> updateByPk(${className}Entity entity<#if !table.hasAutoIncrementUniquePrimaryKey><#list primaryKey as column>, ${column.columnFieldType} old${column.columnFieldName}</#list></#if>, String sessionId) {

        String fieldName = "";
        if (entity == null<#if !table.hasAutoIncrementUniquePrimaryKey> || OperationCheckUtils.isNullOrEmpty(<#list primaryKey as column><#if (column_index > 0)>, </#if>old${column.columnFieldName}</#list>)</#if> || !StringUtils.isEmpty(fieldName = OperationCheckUtils.checkUpdate(entity))) {
            return new Result<>(OperationConstants.NOT_NULL.getCode(), fieldName + OperationConstants.NOT_NULL.getMessage());
        }

        <#if !table.hasAutoIncrementUniquePrimaryKey>
        ${className}Condition condition = getPkCondition(<#if !table.hasAutoIncrementUniquePrimaryKey><#list primaryKey as column><#if (column_index > 0)>, </#if>old${column.columnFieldName}</#list></#if>);
        <#else>
        ${className}Condition condition = getPkCondition(<#list primaryKey as column><#if (column_index > 0)>, </#if>entity.get${column.columnFieldName}()</#list>);
        </#if>
        int count = ${classNameLower}Service.updateByCondition(entity, condition);
        Result<Integer> result = new Result<>(count);
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
    public Result<${className}Vo> getByPk(${primaryKeyParameters}, String sessionId) {

        if (OperationCheckUtils.isNullOrEmpty(${primaryKeyParameterValues})) {
            return new Result<>(OperationConstants.NOT_NULL);
        }

        ${className}Vo vo = ${classNameLower}Service.getByPk(${primaryKeyParameterValues});
        Result<${className}Vo> result = new Result<>(vo);
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
    public Result<PageInfo<${className}Vo>> findPageList(${className}SelectParameter parameter, String sessionId) {

        PageInfo<${className}Vo> paginationVo = ${classNameLower}Service.findPageList(parameter);
        Result<PageInfo<${className}Vo>> result = new Result<>(paginationVo);
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
