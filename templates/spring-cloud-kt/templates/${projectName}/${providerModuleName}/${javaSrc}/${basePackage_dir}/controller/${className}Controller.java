<#include "/include/table/properties.ftl">
package ${basePackage}.controller;

import ${baseCommonPackage}.model.ResultVo;
import ${baseCommonPackage}.validation.*;
import ${basePackage}.condition.${className}Condition;
import ${basePackage}.condition.extension.${className}ConditionExtension;
import ${basePackage}.entity.${className}Entity;
import ${basePackage}.entity.extension.${className}EntityExtension;
import ${basePackage}.service.${className}Service;

import com.github.pagehelper.PageInfo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.*;
import java.util.List;

<#include "/include/java_copyright.ftl">
@RequestMapping("/${classNameFirstLower}")
@RestController
public class ${className}Controller {

    @Autowired
    private ${className}Service ${classNameFirstLower}Service;

    /**
     * 添加
     *
     * @param entity
     * @param sessionId
     * @return
     <#include "/include/author_info1.ftl">
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ResultVo<${className}Entity> add(@Validated({DataAdd.class}) @RequestBody ${className}Entity entity, Errors errors, @RequestParam(value = "sessionId", required = false) String sessionId) {

        int count = ${classNameFirstLower}Service.insert(entity);
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
    @RequestMapping(value = "/addList", method = RequestMethod.POST)
    public ResultVo<Integer> addList(@Validated({DataAdd.class}) @RequestBody List<${className}Entity> list, Errors errors, @RequestParam(value = "sessionId", required = false) String sessionId) {

        int count = ${classNameFirstLower}Service.insert(list);
        ResultVo<Integer> result = new ResultVo<>(count);
        return result;
    }
    <#if table.hasPk>

    /**
     * 根据主键物理删除
     *
     <#list pks as column>
     <#include "/include/column/properties.ftl">
     * @param ${fieldName}
     </#list>
     * @param sessionId
     * @return
     <#include "/include/author_info1.ftl">
     */
    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    public ResultVo<Integer> delete(<#include "/include/table/pk_request_params_validate.ftl">, @RequestParam(value = "sessionId", required = false) String sessionId) {

        ${className}Condition condition = getPkCondition(<#include "/include/table/pk_values.ftl">);
        int count = ${classNameFirstLower}Service.delete(condition);
        ResultVo<Integer> result = new ResultVo<>(count);
        return result;
    }
    <#if (table.hasUniPk)>

    /**
     * 根据主键列表物理删除
     *
     * @param ${table.uniPk.targetName?uncap_first}List
     * @param sessionId
     * @return
     <#include "/include/author_info1.ftl">
     */
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public ResultVo<Integer> delete(@RequestBody List<${table.uniPk.targetDataType}> ${table.uniPk.targetName?uncap_first}List, @RequestParam(value = "sessionId", required = false) String sessionId) {

        ${className}Condition condition = getPkListCondition(${table.uniPk.targetName?uncap_first}List);
        int count = ${classNameFirstLower}Service.delete(condition);
        ResultVo<Integer> result = new ResultVo<>(count);
        return result;
    }
    </#if>
    <#if table.validStatusColumn??>

    /**
     * 根据主键禁用
     *
     <#list pks as column>
     <#include "/include/column/properties.ftl">
     * @param ${fieldName}
     </#list>
     * @param sessionId
     * @return
     <#include "/include/author_info1.ftl">
     */
    @RequestMapping(value = "/disable", method = RequestMethod.GET)
    public ResultVo<Integer> disable(<#include "/include/table/pk_request_params_validate.ftl">, @RequestParam(value = "sessionId", required = false) String sessionId) {

        ${className}Condition condition = getPkCondition(<#include "/include/table/pk_values.ftl">);
        ${className}Entity entity = new ${className}Entity();
        entity.set${table.validStatusColumn.targetName}(${table.validStatusColumn.validStatusOption.invalid});
        int count = ${classNameFirstLower}Service.update(entity, condition);
        ResultVo<Integer> result = new ResultVo<>(count);
        return result;
    }

    /**
     * 根据主键启用
     *
     <#list pks as column>
     <#include "/include/column/properties.ftl">
     * @param ${fieldName}
     </#list>
     * @param sessionId
     * @return
     <#include "/include/author_info1.ftl">
     */
    @RequestMapping(value = "/enable", method = RequestMethod.GET)
    public ResultVo<Integer> enable(<#include "/include/table/pk_request_params_validate.ftl">, @RequestParam(value = "sessionId", required = false) String sessionId) {

        ${className}Condition condition = getPkCondition(<#include "/include/table/pk_values.ftl">);
        ${className}Entity entity = new ${className}Entity();
        entity.set${table.validStatusColumn.targetName}(${table.validStatusColumn.validStatusOption.valid});
        int count = ${classNameFirstLower}Service.update(entity, condition);
        ResultVo<Integer> result = new ResultVo<>(count);
        return result;
    }
    <#if (table.hasUniPk)>

    /**
     * 根据主键列表禁用
     *
     * @param ${table.uniPk.targetName?uncap_first}List
     * @param sessionId
     * @return
     <#include "/include/author_info1.ftl">
     */
    @RequestMapping(value = "/disable", method = RequestMethod.POST)
    public ResultVo<Integer> disable(@RequestBody List<${table.uniPk.targetDataType}> ${table.uniPk.targetName?uncap_first}List, @RequestParam(value = "sessionId", required = false) String sessionId) {

        ${className}Condition condition = getPkListCondition(${table.uniPk.targetName?uncap_first}List);
        ${className}Entity entity = new ${className}Entity();
        entity.set${table.validStatusColumn.targetName}(${table.validStatusColumn.validStatusOption.invalid});
        int count = ${classNameFirstLower}Service.update(entity, condition);
        ResultVo<Integer> result = new ResultVo<>(count);
        return result;
    }

    /**
     * 根据主键列表启用
     *
     * @param ${table.uniPk.targetName?uncap_first}List
     * @param sessionId
     * @return
     <#include "/include/author_info1.ftl">
     */
    @RequestMapping(value = "/enable", method = RequestMethod.POST)
    public ResultVo<Integer> enable(@RequestBody List<${table.uniPk.targetDataType}> ${table.uniPk.targetName?uncap_first}List, @RequestParam(value = "sessionId", required = false) String sessionId) {

        ${className}Condition condition = getPkListCondition(${table.uniPk.targetName?uncap_first}List);
        ${className}Entity entity = new ${className}Entity();
        entity.set${table.validStatusColumn.targetName}(${table.validStatusColumn.validStatusOption.valid});
        int count = ${classNameFirstLower}Service.update(entity, condition);
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
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public ResultVo<Integer> update(@Validated({DataEdit.class}) @RequestBody ${className}Entity entity, Errors errors<#if !table.hasAutoIncUniPk>, <#include "/include/table/pk_request_params_validate.ftl"></#if>, @RequestParam(value = "sessionId", required = false) String sessionId) {

        <#if !table.hasAutoIncUniPk>
        ${className}Condition condition = getPkCondition(<#include "/include/table/pk_values.ftl">);
        <#else>
        ${className}Condition condition = getPkCondition(<#list pks as column><#if (column_index > 0)>, </#if>entity.get${propertyName}()</#list>);
        </#if>
        int count = ${classNameFirstLower}Service.update(entity, condition);
        ResultVo<Integer> result = new ResultVo<>(count);
        return result;
    }

    /**
     * 根据主键获取
     *
     <#list pks as column>
     <#include "/include/column/properties.ftl">
     * @param ${fieldName}
     </#list>
     * @param sessionId
     * @return
     <#include "/include/author_info1.ftl">
     */
    @RequestMapping(value = "/get", method = RequestMethod.GET)
    public ResultVo<${className}EntityExtension> get(<#include "/include/table/pk_request_params_validate.ftl">, @RequestParam(value = "sessionId", required = false) String sessionId) {

        ${className}EntityExtension vo = ${classNameFirstLower}Service.getByPk(<#include "/include/table/pk_values.ftl">);
        ResultVo<${className}EntityExtension> result = new ResultVo<>(vo);
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
    @RequestMapping(value = "/getPageInfo", method = RequestMethod.POST)
    public ResultVo<PageInfo<${className}EntityExtension>> getPageInfo(@RequestBody ${className}ConditionExtension parameter, @RequestParam(value = "sessionId", required = false) String sessionId) {

        PageInfo<${className}EntityExtension> paginationVo = ${classNameFirstLower}Service.getPageList(parameter);
        ResultVo<PageInfo<${className}EntityExtension>> result = new ResultVo<>(paginationVo);
        return result;
    }

    private ${className}Condition getPkCondition(<#include "/include/table/pk_params.ftl">) {

        ${className}Condition condition = new ${className}Condition();
        <#list pks as column>
        <#include "/include/column/properties.ftl">
        condition.set${propertyName}(${fieldName});
        </#list>
        return condition;
    }
    <#if (table.hasUniPk)>

    private ${className}Condition getPkListCondition(List<${table.uniPk.targetDataType}> ${table.uniPk.targetName?uncap_first}List) {

        ${className}Condition condition = new ${className}Condition();
        condition.set${table.uniPk.targetName}List(${table.uniPk.targetName?uncap_first}List);
        return condition;
    }
    </#if>

}
