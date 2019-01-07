<#include "/include/table/properties.ftl">
package ${basePackage}.admin.service;

import ${baseCommonPackage}.model.ResultVo;
import ${basePackage}.entity.${className}Entity;
import ${basePackage}.condition.extension.${className}ConditionExtension;
import ${basePackage}.entity.extension.${className}EntityExtension;
import ${basePackage}.admin.service.hystric.${className}ServiceHystric;

import com.github.pagehelper.PageInfo;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

<#include "/include/java_copyright.ftl">
@FeignClient(value = "${providerModuleName}", fallback = ${className}ServiceHystric.class)
public interface ${className}Service {

    /**
     * 添加
     *
     * @param entity
     * @param sessionId
     * @return
     <#include "/include/author_info1.ftl">
     */
    @RequestMapping(value = "/${classNameFirstLower}/add", method = RequestMethod.POST)
    ResultVo<${className}Entity> add(@RequestBody ${className}Entity entity, @RequestParam(value = "sessionId", required = false) String sessionId);

    /**
     * 添加列表
     *
     * @param list
     * @param sessionId
     * @return
    <#include "/include/author_info1.ftl">
     */
    @RequestMapping(value = "/${classNameFirstLower}/addList", method = RequestMethod.POST)
    ResultVo<Integer> addList(@RequestBody List<${className}Entity> list, @RequestParam(value = "sessionId", required = false) String sessionId);
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
    @RequestMapping(value = "/${classNameFirstLower}/delete", method = RequestMethod.GET)
    ResultVo<Integer> delete(<#include "/include/table/pk_request_params.ftl">, @RequestParam(value = "sessionId", required = false) String sessionId);
    <#if (table.hasUniPk)>

    /**
     * 根据主键列表物理删除
     *
     * @param ${table.uniPk.targetName?uncap_first}List
     * @param sessionId
     * @return
     <#include "/include/author_info1.ftl">
     */
    @RequestMapping(value = "/${classNameFirstLower}/delete", method = RequestMethod.POST)
    ResultVo<Integer> delete(@RequestBody List<${table.uniPk.targetDataType}> ${table.uniPk.targetName?uncap_first}List, @RequestParam(value = "sessionId", required = false) String sessionId);
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
    @RequestMapping(value = "/${classNameFirstLower}/disable", method = RequestMethod.GET)
    ResultVo<Integer> disable(<#include "/include/table/pk_request_params.ftl">, @RequestParam(value = "sessionId", required = false) String sessionId);

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
    @RequestMapping(value = "/${classNameFirstLower}/enable", method = RequestMethod.GET)
    ResultVo<Integer> enable(<#include "/include/table/pk_request_params.ftl">, @RequestParam(value = "sessionId", required = false) String sessionId);
    <#if (table.hasUniPk)>

    /**
     * 根据主键列表禁用
     *
     * @param ${table.uniPk.targetName?uncap_first}List
     * @param sessionId
     * @return
     <#include "/include/author_info1.ftl">
     */
    @RequestMapping(value = "/${classNameFirstLower}/disable", method = RequestMethod.POST)
    ResultVo<Integer> disable(@RequestBody List<${table.uniPk.targetDataType}> ${table.uniPk.targetName?uncap_first}List, @RequestParam(value = "sessionId", required = false) String sessionId);

    /**
     * 根据主键列表启用
     *
     * @param ${table.uniPk.targetName?uncap_first}List
     * @param sessionId
     * @return
     <#include "/include/author_info1.ftl">
     */
    @RequestMapping(value = "/${classNameFirstLower}/enable", method = RequestMethod.POST)
    ResultVo<Integer> enable(@RequestBody List<${table.uniPk.targetDataType}> ${table.uniPk.targetName?uncap_first}List, @RequestParam(value = "sessionId", required = false) String sessionId);
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
    @RequestMapping(value = "/${classNameFirstLower}/update", method = RequestMethod.POST)
    ResultVo<Integer> update(@RequestBody ${className}Entity entity<#if !table.hasAutoIncUniPk>, <#include "/include/table/pk_request_params.ftl"></#if>, @RequestParam(value = "sessionId", required = false) String sessionId);

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
    @RequestMapping(value = "/${classNameFirstLower}/get", method = RequestMethod.GET)
    ResultVo<${className}EntityExtension> get(<#include "/include/table/pk_request_params.ftl">, @RequestParam(value = "sessionId", required = false) String sessionId);
    </#if>

    /**
     * 分页查询
     *
     * @param parameter
     * @param sessionId
     * @return
     <#include "/include/author_info1.ftl">
     */
    @RequestMapping(value = "/${classNameFirstLower}/getPageInfo", method = RequestMethod.POST)
    ResultVo<PageInfo<${className}EntityExtension>> getPageInfo(@RequestBody ${className}ConditionExtension parameter, @RequestParam(value = "sessionId", required = false) String sessionId);

}
