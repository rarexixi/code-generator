<#assign className = table.tableClassName>
<#assign classNameLower = table.tableClassName?uncap_first>
<#assign primaryKey = table.primaryKey>
<#assign primaryKeyParameters = table.primaryKeyParameters>
<#assign primaryKeyParameterValues = table.primaryKeyParameterValues>
package ${basePackage}.controller;

import ${baseCommonPackage}.constant.OperationConstants;
import ${baseCommonPackage}.model.Result;
import ${baseCommonPackage}.model.SearchPage;
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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

<#include "/include/java_copyright.ftl">
@RequestMapping("/${classNameLower}")
@RestController
public class ${className}Controller {

    private static LogUtils logger = LogUtils.build(${className}Controller.class);

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
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public Result<Integer> add(@RequestBody ${className}Entity entity, @RequestParam(value = "sessionId", required = false) String sessionId) {

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
    @RequestMapping(value = "/addList", method = RequestMethod.POST)
    public Result<Integer> addList(@RequestBody List<${className}Entity> list, @RequestParam(value = "sessionId", required = false) String sessionId) {

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
    @RequestMapping(value = "/deleteByPk", method = RequestMethod.GET)
    public Result<Integer> deleteByPk(<#list primaryKey as column><#if (column_index > 0)>, </#if>@RequestParam(value="${column.columnFieldNameFirstLower}") ${column.columnFieldType} ${column.columnFieldNameFirstLower}</#list>, @RequestParam(value = "sessionId", required = false) String sessionId) {

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
    @RequestMapping(value = "/deleteByPkList", method = RequestMethod.POST)
    public Result<Integer> deleteByPkList(@RequestBody List<${table.uniquePrimaryKey.columnFieldType}> ${table.uniquePrimaryKey.columnFieldName?uncap_first}List, @RequestParam(value = "sessionId", required = false) String sessionId) {

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
    @RequestMapping(value = "/disableByPk", method = RequestMethod.GET)
    public Result<Integer> disableByPk(<#list primaryKey as column><#if (column_index > 0)>, </#if>@RequestParam(value="${column.columnFieldNameFirstLower}") ${column.columnFieldType} ${column.columnFieldNameFirstLower}</#list>, @RequestParam(value = "sessionId", required = false) String sessionId) {

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
    @RequestMapping(value = "/enableByPk", method = RequestMethod.GET)
    public Result<Integer> enableByPk(<#list primaryKey as column><#if (column_index > 0)>, </#if>@RequestParam(value="${column.columnFieldNameFirstLower}") ${column.columnFieldType} ${column.columnFieldNameFirstLower}</#list>, @RequestParam(value = "sessionId", required = false) String sessionId) {

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
    @RequestMapping(value = "/disableByPkList", method = RequestMethod.POST)
    public Result<Integer> disableByPkList(@RequestBody List<${table.uniquePrimaryKey.columnFieldType}> ${table.uniquePrimaryKey.columnFieldName?uncap_first}List, @RequestParam(value = "sessionId", required = false) String sessionId) {

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
    @RequestMapping(value = "/enableByPkList", method = RequestMethod.POST)
    public Result<Integer> enableByPkList(@RequestBody List<${table.uniquePrimaryKey.columnFieldType}> ${table.uniquePrimaryKey.columnFieldName?uncap_first}List, @RequestParam(value = "sessionId", required = false) String sessionId) {

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
    @RequestMapping(value = "/updateByPk", method = RequestMethod.POST)
    public Result<Integer> updateByPk(@RequestBody ${className}Entity entity<#if !table.hasAutoIncrementUniquePrimaryKey><#list primaryKey as column>, @RequestParam(value="old${column.columnFieldName}") ${column.columnFieldType} old${column.columnFieldName}</#list></#if>, @RequestParam(value = "sessionId", required = false) String sessionId) {

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
    @RequestMapping(value = "/getByPk", method = RequestMethod.GET)
    public Result<${className}Vo> getByPk(<#list primaryKey as column><#if (column_index > 0)>, </#if>@RequestParam(value="${column.columnFieldNameFirstLower}") ${column.columnFieldType} ${column.columnFieldNameFirstLower}</#list>, @RequestParam(value = "sessionId", required = false) String sessionId) {

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
    @RequestMapping(value = "/findPageList", method = RequestMethod.POST)
    public Result<PageInfo<${className}Vo>> findPageList(@RequestBody ${className}SelectParameter parameter, @RequestParam(value = "sessionId", required = false) String sessionId) {

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
