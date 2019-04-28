<#include "/include/table/properties.ftl">
package ${basePackage}.controller;

import ${baseCommonPackage}.model.PageInfoVo;
import ${baseCommonPackage}.model.ResultVo;
import ${baseCommonPackage}.model.OrderSearch;
import ${baseCommonPackage}.model.OrderSearchPage;
import ${baseCommonPackage}.validation.*;

import ${basePackage}.models.condition.${className}Condition;
import ${basePackage}.models.condition.extension.${className}ConditionExtension;
import ${basePackage}.models.condition.order.${className}OrderCondition;
import ${basePackage}.models.entity.${className}Entity;
import ${basePackage}.models.entity.extension.${className}EntityExtension;
import ${basePackage}.service.${className}Service;
import ${basePackage}.utils.VoUtils;

import com.github.pagehelper.PageInfo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.*;
import java.util.List;

<#include "/include/java_copyright.ftl">
@RequestMapping("/${tablePath}")
@RestController
@Validated
public class ${className}Controller {

    @Autowired
    public ${className}Controller(${className}Service ${classNameFirstLower}Service) {
        this.${classNameFirstLower}Service = ${classNameFirstLower}Service;
    }

    private ${className}Service ${classNameFirstLower}Service;

    /**
     * 添加${tableComment}
     *
     * @param entity
     * @param sessionId
     * @return
     <#include "/include/author_info1.ftl">
     */
    @PostMapping("/add")
    public ResultVo<${className}Entity> add(
            @Validated({DataAdd.class}) @RequestBody ${className}Entity entity, Errors errors,
            @RequestParam(value = "sessionId", required = false) String sessionId) {

        int count = ${classNameFirstLower}Service.insert(entity);
        ResultVo<${className}Entity> result = new ResultVo<>(entity);
        return result;
    }

    /**
     * 添加${tableComment}列表
     *
     * @param list
     * @param sessionId
     * @return
     <#include "/include/author_info1.ftl">
     */
    @PostMapping("/add-list")
    public ResultVo<Integer> addList(
            @Validated({DataAdd.class}) @RequestBody List<${className}Entity> list, Errors errors,
            @RequestParam(value = "sessionId", required = false) String sessionId) {

        int count = ${classNameFirstLower}Service.insert(list);
        ResultVo<Integer> result = new ResultVo<>(count);
        return result;
    }

    /**
     * 根据条件删除${tableComment}
     *
     * @param condition
     * @param sessionId
     * @return
     <#include "/include/author_info1.ftl">
     */
    @PostMapping("/delete")
    public ResultVo<Integer> delete(
            @RequestBody ${className}Condition condition,
            @RequestParam(value = "sessionId", required = false) String sessionId) {

        int count = ${classNameFirstLower}Service.delete(condition);
        ResultVo<Integer> result = new ResultVo<>(count);
        return result;
    }
    <#if table.validStatusColumn??>

    /**
     * 根据条件禁用${tableComment}
     *
     * @param condition
     * @param sessionId
     * @return
     <#include "/include/author_info1.ftl">
     */
    @PostMapping("/disable")
    public ResultVo<Integer> disable(
            @RequestBody ${className}Condition condition,
            @RequestParam(value = "sessionId", required = false) String sessionId) {

        ${className}Entity entity = new ${className}Entity();
        entity.set${table.validStatusColumn.targetName}(${table.validStatusColumn.validStatusOption.invalid});
        int count = ${classNameFirstLower}Service.update(entity, condition);
        ResultVo<Integer> result = new ResultVo<>(count);
        return result;
    }

    /**
     * 根据条件启用${tableComment}
     *
     * @param condition
     * @param sessionId
     * @return
     <#include "/include/author_info1.ftl">
     */
    @PostMapping("/enable")
    public ResultVo<Integer> enable(
            @RequestBody ${className}Condition condition,
            @RequestParam(value = "sessionId", required = false) String sessionId) {

        ${className}Entity entity = new ${className}Entity();
        entity.set${table.validStatusColumn.targetName}(${table.validStatusColumn.validStatusOption.valid});
        int count = ${classNameFirstLower}Service.update(entity, condition);
        ResultVo<Integer> result = new ResultVo<>(count);
        return result;
    }
    </#if>

    /**
     * 根据条件获取${tableComment}实体
     *
     * @param condition
     * @param sessionId
     * @return
     <#include "/include/author_info1.ftl">
     */
    @PostMapping("/get")
    public ResultVo<${className}Entity> get(
            @RequestBody ${className}Condition condition,
            @RequestParam(value = "sessionId", required = false) String sessionId) {

        ${className}Entity entity = ${classNameFirstLower}Service.get(condition);
        ResultVo<${className}Entity> result = new ResultVo<>(entity);
        return result;
    }
    <#if (table.hasPk)>

    /**
     * 根据主键更新${tableComment}
     *
     * @param entity
     <#if !table.hasAutoIncUniPk>
     <#list pks as column>
     <#include "/include/column/properties.ftl">
     * @param ${fieldName}
     </#list>
     </#if>
     * @param sessionId
     * @return
     <#include "/include/author_info1.ftl">
     */
    @PostMapping("/update")
    public ResultVo<Integer> update(
            @Validated({DataEdit.class}) @RequestBody ${className}Entity entity, Errors errors,
            <#if !table.hasAutoIncUniPk>
            <#list pks as column>
            <#include "/include/column/properties.ftl">
            <#assign annotationName = (isString ? string('NotBlank', 'NotNull'))>
            @${annotationName}(message = "${fieldName} (${columnComment})不能为空") @RequestParam(value = "${fieldName}") ${fieldType} ${fieldName},
            </#list>
            </#if>
            @RequestParam(value = "sessionId", required = false) String sessionId) {

        ${className}Condition condition = new ${className}Condition();
        <#list pks as column>
        <#include "/include/column/properties.ftl">
        condition.set${propertyName}(<#if table.hasAutoIncUniPk>entity.get${propertyName}()<#else>${fieldName}</#if>);
        </#list>

        int count = ${classNameFirstLower}Service.update(entity, condition);
        ResultVo<Integer> result = new ResultVo<>(count);
        return result;
    }

    /**
     * 根据主键获取${tableComment}详情
     *
     <#list pks as column>
     <#include "/include/column/properties.ftl">
     * @param ${fieldName}
     </#list>
     * @param sessionId
     * @return
     <#include "/include/author_info1.ftl">
     */
    @GetMapping("/detail")
    public ResultVo<${className}EntityExtension> getDetail(
            <#list pks as column>
            <#include "/include/column/properties.ftl">
            <#assign annotationName = (isString ? string('NotBlank', 'NotNull'))>
            @${annotationName}(message = "${fieldName} (${columnComment})不能为空") @RequestParam(value = "${fieldName}") ${fieldType} ${fieldName},
            </#list>
            @RequestParam(value = "sessionId", required = false) String sessionId) {

        ${className}EntityExtension entity = ${classNameFirstLower}Service.getByPk(<#include "/include/table/pk_values.ftl">);
        ResultVo<${className}EntityExtension> result = new ResultVo<>(entity);
        return result;
    }
    </#if>

    /**
     * 获取${tableComment}列表
     *
     * @param search
     * @param sessionId
     * @return
     <#include "/include/author_info1.ftl">
     */
    @PostMapping("/list")
    public ResultVo<List<${className}EntityExtension>> getList(
            @RequestBody OrderSearch<${className}ConditionExtension, ${className}OrderCondition> search,
            @RequestParam(value = "sessionId", required = false) String sessionId) {

        List<${className}EntityExtension> list = ${classNameFirstLower}Service.getExList(search);
        ResultVo<List<${className}EntityExtension>> result = new ResultVo<>(list);
        return result;
    }

    /**
     * 分页查询${tableComment}
     *
     * @param searchPage
     * @param sessionId
     * @return
    <#include "/include/author_info1.ftl">
     */
    @PostMapping("/page-list")
    public ResultVo<PageInfoVo<${className}EntityExtension>> getPageInfo(
            @RequestBody OrderSearchPage<${className}ConditionExtension, ${className}OrderCondition> searchPage,
            @RequestParam(value = "sessionId", required = false) String sessionId) {

        PageInfo<${className}EntityExtension> pageInfo = ${classNameFirstLower}Service.getPageList(searchPage);
        PageInfoVo<${className}EntityExtension> pageInfoVo = VoUtils.getPageInfoVo(pageInfo);
        ResultVo<PageInfoVo<${className}EntityExtension>> result = new ResultVo<>(pageInfoVo);
        return result;
    }
}
