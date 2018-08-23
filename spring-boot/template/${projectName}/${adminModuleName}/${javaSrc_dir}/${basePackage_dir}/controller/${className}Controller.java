<#assign className = table.tableClassName>
<#assign classNameLower = table.tableClassName?uncap_first>
<#assign primaryKey = table.primaryKey>
<#assign primaryKeyParameters = table.primaryKeyParameters>
<#assign primaryKeyParameterValues = table.primaryKeyParameterValues>
package ${basePackage}.controller;

import ${baseCommonPackage}.constant.OperationConstants;
import ${baseCommonPackage}.model.ResultVo;
import ${baseCommonPackage}.utils.LogUtils;
import ${baseCommonPackage}.utils.database.OperationCheckUtils;
import ${basePackage}.condition.${className}Condition;
import ${basePackage}.entity.${className}Entity;
import ${basePackage}.parameter.${className}SelectParameter;
import ${basePackage}.service.${className}Service;
import ${basePackage}.vo.${className}Vo;

import com.github.pagehelper.PageInfo;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

<#include "/include/java_copyright.ftl">
@RestController
@RequestMapping("/${table.tableClassName?lower_case}")
public class ${className}Controller {

    private static LogUtils logger = LogUtils.build(${className}Controller.class);

    @Autowired
    private ${className}Service ${classNameLower}Service;

    /**
     * 添加列表
     *
     * @param list
     * @return
    <#include "/include/author_info1.ftl">
     */
    @RequestMapping(value = { "/addList" }, method = RequestMethod.POST)
    public ResultVo<Integer> addList(@RequestBody List<${className}Entity> list) {

        if (list == null || list.isEmpty()) {
            return new ResultVo<>(OperationConstants.NOT_NULL);
        }
        String fieldName = "";
        for (${className}Entity ${classNameLower} : list) {
            if (!StringUtils.isEmpty(fieldName = OperationCheckUtils.checkInsert(${classNameLower}))) {
                return new ResultVo<>(OperationConstants.NOT_NULL.getCode(), fieldName + OperationConstants.NOT_NULL.getMessage());
            }
        }

        ResultVo<Integer> result;
        try {
            int count = ${classNameLower}Service.insertList(list);
            result = new ResultVo<>(count);
        } catch (Exception e) {
            logger.error("addList", e);
            result = new ResultVo<>(OperationConstants.SYSTEM_ERROR);
        }

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
     @RequestMapping(value = { "/delete" }, method = RequestMethod.GET)
     public ResultVo<Integer> delete(<#list primaryKey as column><#if (column_index > 0)>, </#if>@RequestParam("${column.columnFieldNameFirstLower}") ${column.columnFieldType} ${column.columnFieldNameFirstLower}</#list>) {

         if (OperationCheckUtils.isNullOrEmpty(${primaryKeyParameterValues})) {
             return new ResultVo<>(OperationConstants.NOT_NULL);
         }

        ResultVo<Integer> result;
        try {
            ${className}Condition condition = getPkCondition(${primaryKeyParameterValues});
            int count = ${classNameLower}Service.deleteByCondition(condition);
            result = new ResultVo<>(count);
        } catch (Exception e) {
            logger.error("delete", e);
            result = new ResultVo<>(OperationConstants.SYSTEM_ERROR);
        }

        return result;
    }
    <#if (table.uniquePrimaryKey??)>

    /**
     * 根据主键列表物理删除
     *
     * @param ${table.uniquePrimaryKey.columnFieldName?uncap_first}List
     * @return
     <#include "/include/author_info1.ftl">
     */
     @RequestMapping(value = { "/deleteList" }, method = RequestMethod.POST)
     public ResultVo<Integer> deleteList(@RequestBody List<${table.uniquePrimaryKey.columnFieldType}> ${table.uniquePrimaryKey.columnFieldName?uncap_first}List) {

         ResultVo<Integer> result;
         try {
             ${className}Condition condition = getPkListCondition(${table.uniquePrimaryKey.columnFieldName?uncap_first}List);
             int count = ${classNameLower}Service.deleteByCondition(condition);
             result = new ResultVo<>(count);
         } catch (Exception e) {
             logger.error("delete", e);
             result = new ResultVo<>(OperationConstants.SYSTEM_ERROR);
         }

         return result;
    }
    </#if>
    <#if table.validStatusColumn??>

    /**
     * 根据主键禁用
     *
     <#list primaryKey as column>
     * @param ${column.columnFieldNameFirstLower}
     </#list>
     * @return
     <#include "/include/author_info1.ftl">
     */
    @RequestMapping(value = "/disable", method = RequestMethod.GET)
    public ResultVo<Integer> disable(${primaryKeyParameters}) {

        if (OperationCheckUtils.isNullOrEmpty(${primaryKeyParameterValues})) {
            return new ResultVo<>(OperationConstants.NOT_NULL);
        }

        ResultVo<Integer> result;
        try {
            ${className}Condition condition = getPkCondition(${primaryKeyParameterValues});
            ${className}Entity entity = new ${className}Entity();
            entity.set${table.validStatusColumn.columnFieldName}(${table.validStatusField.invalidValue});
            int count = ${classNameLower}Service.updateByCondition(entity, condition);
            result = new ResultVo<>(count);
        } catch (Exception e) {
            logger.error("disable", e);
            result = new ResultVo<>(OperationConstants.SYSTEM_ERROR);
        }

        return result;
    }

    /**
     * 根据主键启用
     *
     <#list primaryKey as column>
     * @param ${column.columnFieldNameFirstLower}
     </#list>
     * @return
     <#include "/include/author_info1.ftl">
     */
    @RequestMapping(value = "/enable", method = RequestMethod.GET)
    public ResultVo<Integer> enable(${primaryKeyParameters}) {

        if (OperationCheckUtils.isNullOrEmpty(${primaryKeyParameterValues})) {
            return new ResultVo<>(OperationConstants.NOT_NULL);
        }

        ResultVo<Integer> result;
        try {
            ${className}Condition condition = getPkCondition(${primaryKeyParameterValues});
            ${className}Entity entity = new ${className}Entity();
            entity.set${table.validStatusColumn.columnFieldName}(${table.validStatusField.validValue});
            int count = ${classNameLower}Service.updateByCondition(entity, condition);
            result = new ResultVo<>(count);
        } catch (Exception e) {
            logger.error("enable", e);
            result = new ResultVo<>(OperationConstants.SYSTEM_ERROR);
        }

        return result;
    }
    <#if (table.uniquePrimaryKey??)>

    /**
     * 根据主键列表禁用
     *
     * @param ${table.uniquePrimaryKey.columnFieldName?uncap_first}List
     * @return
     <#include "/include/author_info1.ftl">
     */
    @RequestMapping(value = "/disableList", method = RequestMethod.POST)
    public ResultVo<Integer> disableList(@RequestBody List<${table.uniquePrimaryKey.columnFieldType}> ${table.uniquePrimaryKey.columnFieldName?uncap_first}List) {

        if (OperationCheckUtils.isNullOrEmpty(${table.uniquePrimaryKey.columnFieldName?uncap_first}List)) {
            return new ResultVo<>(OperationConstants.NOT_NULL);
        }

        ResultVo<Integer> result;
        try {
            ${className}Condition condition = getPkListCondition(${table.uniquePrimaryKey.columnFieldName?uncap_first}List);
            ${className}Entity entity = new ${className}Entity();
            entity.set${table.validStatusColumn.columnFieldName}(${table.validStatusField.invalidValue});
            int count = ${classNameLower}Service.updateByCondition(entity, condition);
            result = new ResultVo<>(count);
        } catch (Exception e) {
            logger.error("disable", e);
            result = new ResultVo<>(OperationConstants.SYSTEM_ERROR);
        }
        return result;
    }

    /**
     * 根据主键列表启用
     *
     * @param ${table.uniquePrimaryKey.columnFieldName?uncap_first}List
     * @return
     <#include "/include/author_info1.ftl">
     */
    @RequestMapping(value = "/enableList", method = RequestMethod.POST)
    public ResultVo<Integer> enableList(@RequestBody List<${table.uniquePrimaryKey.columnFieldType}> ${table.uniquePrimaryKey.columnFieldName?uncap_first}List) {

        if (OperationCheckUtils.isNullOrEmpty(${table.uniquePrimaryKey.columnFieldName?uncap_first}List)) {
            return new ResultVo<>(OperationConstants.NOT_NULL);
        }

        ResultVo<Integer> result;
        try {
            ${className}Condition condition = getPkListCondition(${table.uniquePrimaryKey.columnFieldName?uncap_first}List);
            ${className}Entity entity = new ${className}Entity();
            entity.set${table.validStatusColumn.columnFieldName}(${table.validStatusField.validValue});
            int count = ${classNameLower}Service.updateByCondition(entity, condition);
            result = new ResultVo<>(count);
        } catch (Exception e) {
            logger.error("enable", e);
            result = new ResultVo<>(OperationConstants.SYSTEM_ERROR);
        }

        return result;
    }
    </#if>
    </#if>

    /**
     * 保存
     *
     * @param entity
     * @return
     <#include "/include/author_info1.ftl">
     */
    @RequestMapping(value = { "/save" }, method = RequestMethod.POST)
    public ResultVo<Integer> save(@RequestBody ${className}Entity entity<#if !table.hasAutoIncrementUniquePrimaryKey><#list primaryKey as column>, @RequestParam(value = "old${column.columnFieldName}", required = false) ${column.columnFieldType} old${column.columnFieldName}</#list></#if>) {

        String fieldName = "";

        ResultVo<Integer> result;
        try {
            int count;
            if (<#list primaryKey as column><#if (column_index > 0)> || </#if><#if !table.hasAutoIncrementUniquePrimaryKey>old${column.columnFieldName}<#else>entity.get${column.columnFieldName}()</#if> == null</#list>) {
                if (entity == null || !StringUtils.isEmpty(fieldName = OperationCheckUtils.checkInsert(entity))) {
                    return new ResultVo<>(OperationConstants.NOT_NULL.getCode(), fieldName + OperationConstants.NOT_NULL.getMessage());
                }
                count = ${classNameLower}Service.insert(entity);
            } else {
                if (entity == null || !StringUtils.isEmpty(fieldName = OperationCheckUtils.checkUpdate(entity))) {
                    return new ResultVo<>(OperationConstants.NOT_NULL.getCode(), fieldName + OperationConstants.NOT_NULL.getMessage());
                }
                <#if !table.hasAutoIncrementUniquePrimaryKey>
                ${className}Condition condition = getPkCondition(<#if !table.hasAutoIncrementUniquePrimaryKey><#list primaryKey as column><#if (column_index > 0)>, </#if>old${column.columnFieldName}</#list></#if>);
                <#else>
                ${className}Condition condition = getPkCondition(<#list primaryKey as column><#if (column_index > 0)>, </#if>entity.get${column.columnFieldName}()</#list>);
                </#if>
                count = ${classNameLower}Service.updateByCondition(entity, condition);
            }
            result = new ResultVo<>(count);
        } catch (Exception e) {
            logger.error("save", e);
            result = new ResultVo<>(OperationConstants.SYSTEM_ERROR);
        }

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
    @RequestMapping(value = { "/getDetail" }, method = RequestMethod.GET)
    public ResultVo<${className}Vo> getDetail(<#list primaryKey as column><#if (column_index > 0)>, </#if>@RequestParam("${column.columnFieldNameFirstLower}") ${column.columnFieldType} ${column.columnFieldNameFirstLower}</#list>) {

        if (OperationCheckUtils.isNullOrEmpty(${primaryKeyParameterValues})) {
            return new ResultVo<>(OperationConstants.NOT_NULL);
        }

        ResultVo<${className}Vo> result;
        try {
            ${className}Vo vo = ${classNameLower}Service.getByPk(${primaryKeyParameterValues});
            result = new ResultVo<>(vo);
        } catch (Exception e) {
            logger.error("getDetail", e);
            result = new ResultVo<>(OperationConstants.SYSTEM_ERROR);
        }

        return result;
    }
    </#if>

    /**
     * 分页查询
     *
     * @param parameter
     * @return
     <#include "/include/author_info1.ftl">
     */
    @RequestMapping(value = { "/find" }, method = RequestMethod.POST)
    public ResultVo<PageInfo<${className}Vo>> find(@RequestBody ${className}SelectParameter parameter) {

        ResultVo<PageInfo<${className}Vo>> result;
        try {
            PageInfo<${className}Vo> paginationVo = ${classNameLower}Service.findPageList(parameter);
            result = new ResultVo<>(paginationVo);
        } catch (Exception e) {
            logger.error("find", e);
            result = new ResultVo<>(OperationConstants.SYSTEM_ERROR);
        }

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
