<#include "/include/table/properties.ftl">
package ${basePackage}.admin.cloudservice;

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
    @PostMapping("/${classNameFirstLower}/add")
    ResultVo<${className}Entity> add(@RequestBody ${className}Entity entity,
                        <#include "/include/table/class_length_whitespace.ftl">@RequestParam(value = "sessionId", required = false) String sessionId);

    /**
     * 添加${tableComment}列表
     *
     * @param list
     * @param sessionId
     * @return
     <#include "/include/author_info1.ftl">
     */
    @PostMapping("/${classNameFirstLower}/addList")
    ResultVo<Integer> addList(@RequestBody List<${className}Entity> list,
                              @RequestParam(value = "sessionId", required = false) String sessionId);

    /**
     * 根据条件删除${tableComment}
     *
     * @param condition
     * @param sessionId
     * @return
     <#include "/include/author_info1.ftl">
     */
    @PostMapping("/${classNameFirstLower}/delete")
    ResultVo<Integer> delete(@RequestBody ${className}Condition condition,
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
    @PostMapping("/${classNameFirstLower}/disable")
    ResultVo<Integer> disable(@RequestBody ${className}Condition condition,
                              @RequestParam(value = "sessionId", required = false) String sessionId);

    /**
     * 根据条件启用${tableComment}
     *
     * @param condition
     * @param sessionId
     * @return
     <#include "/include/author_info1.ftl">
     */
    @PostMapping("/${classNameFirstLower}/enable")
    ResultVo<Integer> enable(@RequestBody ${className}Condition condition,
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
    @PostMapping("/${classNameFirstLower}/get")
    ResultVo<${className}Entity> get(@RequestBody ${className}Condition condition,
                        <#include "/include/table/class_length_whitespace.ftl">@RequestParam(value = "sessionId", required = false) String sessionId);
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
    @PostMapping("/${classNameFirstLower}/update")
    ResultVo<Integer> update(@RequestBody ${className}Entity entity, <#if !table.hasAutoIncUniPk><#include "/include/table/pk_request_params.ftl">,</#if>
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
    @GetMapping("/${classNameFirstLower}/getDetail")
    ResultVo<${className}EntityExtension> getDetail(<#include "/include/table/pk_request_params.ftl">,
                                       <#include "/include/table/class_length_whitespace.ftl">@RequestParam(value = "sessionId", required = false) String sessionId);
    </#if>

    /**
     * 获取${tableComment}列表
     *
     * @param condition
     * @param sessionId
     * @return
     <#include "/include/author_info1.ftl">
     */
    @PostMapping("/${classNameFirstLower}/getList")
    ResultVo<List<${className}EntityExtension>> getList(@RequestBody ${className}ConditionExtension condition,
                                           <#include "/include/table/class_length_whitespace.ftl">@RequestParam(value = "sessionId", required = false) String sessionId);

    /**
     * 分页查询${tableComment}
     *
     * @param searchPage
     * @param sessionId
     * @return
     <#include "/include/author_info1.ftl">
     */
    @PostMapping("/${classNameFirstLower}/getPageInfo")
    ResultVo<PageInfoVo<${className}EntityExtension>> getPageInfo(@RequestBody OrderSearchPage<${className}ConditionExtension, ${className}OrderCondition> searchPage,
                                                     <#include "/include/table/class_length_whitespace.ftl">@RequestParam(value = "sessionId", required = false) String sessionId);
}
