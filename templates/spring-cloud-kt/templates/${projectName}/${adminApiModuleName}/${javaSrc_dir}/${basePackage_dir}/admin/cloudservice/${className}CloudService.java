<#include "/include/table/properties.ftl">
package ${basePackage}.admin.cloudservice;

import ${baseCommonPackage}.model.OrderSearch;
import ${baseCommonPackage}.model.OrderSearchPage;
import ${baseCommonPackage}.model.PageInfoVo;
import ${baseCommonPackage}.model.ResultVo;
import ${basePackage}.models.condition.${className}Condition;
import ${basePackage}.models.condition.order.${className}OrderCondition;
import ${basePackage}.models.entity.${className}Entity;
import ${basePackage}.models.condition.extension.${className}ConditionExtension;
import ${basePackage}.models.entity.extension.${className}EntityExtension;
import ${basePackage}.admin.cloudservice.hystric.${className}ServiceHystric;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

<#include "/include/java_copyright.ftl">
@FeignClient(value = "${providerModuleName}", fallback = ${className}ServiceHystric.class)
public interface ${className}CloudService {

    /**
     * 添加${tableComment}
     *
     * @param entity
     * @param sessionId
     * @return
     <#include "/include/author_info1.ftl">
     */
    @PostMapping("/${tablePath}/add")
    ResultVo<${className}Entity> add(
            @RequestBody ${className}Entity entity,
            @RequestParam(value = "sessionId", required = false) String sessionId);

    /**
     * 添加${tableComment}列表
     *
     * @param list
     * @param sessionId
     * @return
     <#include "/include/author_info1.ftl">
     */
    @PostMapping("/${tablePath}/add-list")
    ResultVo<Integer> addList(
            @RequestBody List<${className}Entity> list,
            @RequestParam(value = "sessionId", required = false) String sessionId);

    /**
     * 根据条件删除${tableComment}
     *
     * @param condition
     * @param sessionId
     * @return
     <#include "/include/author_info1.ftl">
     */
    @PostMapping("/${tablePath}/delete")
    ResultVo<Integer> delete(
            @RequestBody ${className}Condition condition,
            @RequestParam(value = "sessionId", required = false) String sessionId);
    <#if table.validStatusColumn??>

    /**
     * 根据条件禁用${tableComment}
     *
     * @param condition
     * @param sessionId
     * @return
     <#include "/include/author_info1.ftl">
     */
    @PostMapping("/${tablePath}/disable")
    ResultVo<Integer> disable(
            @RequestBody ${className}Condition condition,
            @RequestParam(value = "sessionId", required = false) String sessionId);

    /**
     * 根据条件启用${tableComment}
     *
     * @param condition
     * @param sessionId
     * @return
     <#include "/include/author_info1.ftl">
     */
    @PostMapping("/${tablePath}/enable")
    ResultVo<Integer> enable(
            @RequestBody ${className}Condition condition,
            @RequestParam(value = "sessionId", required = false) String sessionId);
    </#if>

    /**
     * 根据条件获取${tableComment}实体
     *
     * @param condition
     * @param sessionId
     * @return
     <#include "/include/author_info1.ftl">
     */
    @PostMapping("/${tablePath}/get")
    ResultVo<${className}Entity> get(
            @RequestBody ${className}Condition condition,
            @RequestParam(value = "sessionId", required = false) String sessionId);
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
    @PostMapping("/${tablePath}/update")
    ResultVo<Integer> update(
            @RequestBody ${className}Entity entity,
            <#if !table.hasAutoIncUniPk>
            <#list pks as column>
            <#include "/include/column/properties.ftl">
            @RequestParam(value = "${fieldName}") ${fieldType} ${fieldName},
            </#list>
            </#if>
            @RequestParam(value = "sessionId", required = false) String sessionId);

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
    @GetMapping("/${tablePath}/detail")
    ResultVo<${className}EntityExtension> getDetail(
            <#list pks as column>
            <#include "/include/column/properties.ftl">
            @RequestParam(value = "${fieldName}") ${fieldType} ${fieldName},
            </#list>
            @RequestParam(value = "sessionId", required = false) String sessionId);
    </#if>

    /**
     * 获取${tableComment}列表
     *
     * @param orderSearch
     * @param sessionId
     * @return
     <#include "/include/author_info1.ftl">
     */
    @PostMapping("/${tablePath}/list")
    ResultVo<List<${className}EntityExtension>> getList(
            @RequestBody OrderSearch<${className}ConditionExtension, ${className}OrderCondition> orderSearch,
            @RequestParam(value = "sessionId", required = false) String sessionId);

    /**
     * 分页查询${tableComment}
     *
     * @param searchPage
     * @param sessionId
     * @return
     <#include "/include/author_info1.ftl">
     */
    @PostMapping("/${tablePath}/page-list")
    ResultVo<PageInfoVo<${className}EntityExtension>> getPageInfo(
            @RequestBody OrderSearchPage<${className}ConditionExtension, ${className}OrderCondition> searchPage,
            @RequestParam(value = "sessionId", required = false) String sessionId);
}
