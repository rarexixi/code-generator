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
    @RequestMapping(value = "/${classNameLower}/add", method = RequestMethod.POST)
    ResultVo<${className}Entity> add(@RequestBody ${className}Entity entity, @RequestParam(value = "sessionId", required = false) String sessionId);

    /**
     * 添加列表
     *
     * @param list
     * @param sessionId
     * @return
    <#include "/include/author_info1.ftl">
     */
    @RequestMapping(value = "/${classNameLower}/addList", method = RequestMethod.POST)
    ResultVo<Integer> addList(@RequestBody List<${className}Entity> list, @RequestParam(value = "sessionId", required = false) String sessionId);
    <#if table.hasPrimaryKey>

    /**
     * 根据主键物理删除
     *
     <#list pks as column>
     * @param ${fieldName}
     </#list>
     * @param sessionId
     * @return
     <#include "/include/author_info1.ftl">
     */
    @RequestMapping(value = "/${classNameLower}/delete", method = RequestMethod.GET)
    ResultVo<Integer> delete(<#list pks as column><#if (column_index > 0)>, </#if>@RequestParam(value="${fieldName}") ${column.targetDataType} ${fieldName}</#list>, @RequestParam(value = "sessionId", required = false) String sessionId);
    <#if (table.uniquePrimaryKey??)>

    /**
     * 根据主键列表物理删除
     *
     * @param ${table.uniquePrimaryKey.targetColumnName?uncap_first}List
     * @param sessionId
     * @return
     <#include "/include/author_info1.ftl">
     */
    @RequestMapping(value = "/${classNameLower}/delete", method = RequestMethod.POST)
    ResultVo<Integer> delete(@RequestBody List<${table.uniquePrimaryKey.targetDataType}> ${table.uniquePrimaryKey.targetColumnName?uncap_first}List, @RequestParam(value = "sessionId", required = false) String sessionId);
    </#if>
    <#if table.validStatusColumn??>

    /**
     * 根据主键禁用
     *
     <#list pks as column>
     * @param ${fieldName}
     </#list>
     * @param sessionId
     * @return
     <#include "/include/author_info1.ftl">
     */
    @RequestMapping(value = "/${classNameLower}/disable", method = RequestMethod.GET)
    ResultVo<Integer> disable(<#list pks as column><#if (column_index > 0)>, </#if>@RequestParam(value="${fieldName}") ${column.targetDataType} ${fieldName}</#list>, @RequestParam(value = "sessionId", required = false) String sessionId);

    /**
     * 根据主键启用
     *
     <#list pks as column>
     * @param ${fieldName}
     </#list>
     * @param sessionId
     * @return
     <#include "/include/author_info1.ftl">
     */
    @RequestMapping(value = "/${classNameLower}/enable", method = RequestMethod.GET)
    ResultVo<Integer> enable(<#list pks as column><#if (column_index > 0)>, </#if>@RequestParam(value="${fieldName}") ${column.targetDataType} ${fieldName}</#list>, @RequestParam(value = "sessionId", required = false) String sessionId);
    <#if (table.uniquePrimaryKey??)>

    /**
     * 根据主键列表禁用
     *
     * @param ${table.uniquePrimaryKey.targetColumnName?uncap_first}List
     * @param sessionId
     * @return
     <#include "/include/author_info1.ftl">
     */
    @RequestMapping(value = "/${classNameLower}/disable", method = RequestMethod.POST)
    ResultVo<Integer> disable(@RequestBody List<${table.uniquePrimaryKey.targetDataType}> ${table.uniquePrimaryKey.targetColumnName?uncap_first}List, @RequestParam(value = "sessionId", required = false) String sessionId);

    /**
     * 根据主键列表启用
     *
     * @param ${table.uniquePrimaryKey.targetColumnName?uncap_first}List
     * @param sessionId
     * @return
     <#include "/include/author_info1.ftl">
     */
    @RequestMapping(value = "/${classNameLower}/enable", method = RequestMethod.POST)
    ResultVo<Integer> enable(@RequestBody List<${table.uniquePrimaryKey.targetDataType}> ${table.uniquePrimaryKey.targetColumnName?uncap_first}List, @RequestParam(value = "sessionId", required = false) String sessionId);
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
    @RequestMapping(value = "/${classNameLower}/update", method = RequestMethod.POST)
    ResultVo<Integer> update(@RequestBody ${className}Entity entity<#if !table.hasAutoIncrementUniquePrimaryKey><#list pks as column>, @RequestParam(value="old${propertyName}") ${column.targetDataType} old${propertyName}</#list></#if>, @RequestParam(value = "sessionId", required = false) String sessionId);

    /**
     * 根据主键获取
     *
     <#list pks as column>
     * @param ${fieldName}
     </#list>
     * @param sessionId
     * @return
     <#include "/include/author_info1.ftl">
     */
    @RequestMapping(value = "/${classNameLower}/get", method = RequestMethod.GET)
    ResultVo<${className}EntityExtension> get(<#list pks as column><#if (column_index > 0)>, </#if>@RequestParam(value="${fieldName}") ${column.targetDataType} ${fieldName}</#list>, @RequestParam(value = "sessionId", required = false) String sessionId);
    </#if>

    /**
     * 分页查询
     *
     * @param parameter
     * @param sessionId
     * @return
     <#include "/include/author_info1.ftl">
     */
    @RequestMapping(value = "/${classNameLower}/getPageInfo", method = RequestMethod.POST)
    ResultVo<PageInfo<${className}EntityExtension>> getPageInfo(@RequestBody ${className}ConditionExtension parameter, @RequestParam(value = "sessionId", required = false) String sessionId);

}
