<#include "/include/table/properties.ftl">
package ${basePackage}.admin.service;

import ${baseCommonPackage}.model.ResultVo;
import ${basePackage}.entity.${className}Entity;
import ${basePackage}.parameter.${className}SelectParameter;
import ${basePackage}.vo.${className}Vo;
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
     <#list primaryKey as column>
     * @param ${column.columnFieldNameFirstLower}
     </#list>
     * @param sessionId
     * @return
     <#include "/include/author_info1.ftl">
     */
    @RequestMapping(value = "/${classNameLower}/delete", method = RequestMethod.GET)
    ResultVo<Integer> delete(<#list primaryKey as column><#if (column_index > 0)>, </#if>@RequestParam(value="${column.columnFieldNameFirstLower}") ${column.columnFieldType} ${column.columnFieldNameFirstLower}</#list>, @RequestParam(value = "sessionId", required = false) String sessionId);
    <#if (table.uniquePrimaryKey??)>

    /**
     * 根据主键列表物理删除
     *
     * @param ${table.uniquePrimaryKey.columnFieldName?uncap_first}List
     * @param sessionId
     * @return
     <#include "/include/author_info1.ftl">
     */
    @RequestMapping(value = "/${classNameLower}/delete", method = RequestMethod.POST)
    ResultVo<Integer> delete(@RequestBody List<${table.uniquePrimaryKey.columnFieldType}> ${table.uniquePrimaryKey.columnFieldName?uncap_first}List, @RequestParam(value = "sessionId", required = false) String sessionId);
    </#if>
    <#if table.validStatusColumn??>

    /**
     * 根据主键禁用
     *
     <#list primaryKey as column>
     * @param ${column.columnFieldNameFirstLower}
     </#list>
     * @param sessionId
     * @return
     <#include "/include/author_info1.ftl">
     */
    @RequestMapping(value = "/${classNameLower}/disable", method = RequestMethod.GET)
    ResultVo<Integer> disable(<#list primaryKey as column><#if (column_index > 0)>, </#if>@RequestParam(value="${column.columnFieldNameFirstLower}") ${column.columnFieldType} ${column.columnFieldNameFirstLower}</#list>, @RequestParam(value = "sessionId", required = false) String sessionId);

    /**
     * 根据主键启用
     *
     <#list primaryKey as column>
     * @param ${column.columnFieldNameFirstLower}
     </#list>
     * @param sessionId
     * @return
     <#include "/include/author_info1.ftl">
     */
    @RequestMapping(value = "/${classNameLower}/enable", method = RequestMethod.GET)
    ResultVo<Integer> enable(<#list primaryKey as column><#if (column_index > 0)>, </#if>@RequestParam(value="${column.columnFieldNameFirstLower}") ${column.columnFieldType} ${column.columnFieldNameFirstLower}</#list>, @RequestParam(value = "sessionId", required = false) String sessionId);
    <#if (table.uniquePrimaryKey??)>

    /**
     * 根据主键列表禁用
     *
     * @param ${table.uniquePrimaryKey.columnFieldName?uncap_first}List
     * @param sessionId
     * @return
     <#include "/include/author_info1.ftl">
     */
    @RequestMapping(value = "/${classNameLower}/disable", method = RequestMethod.POST)
    ResultVo<Integer> disable(@RequestBody List<${table.uniquePrimaryKey.columnFieldType}> ${table.uniquePrimaryKey.columnFieldName?uncap_first}List, @RequestParam(value = "sessionId", required = false) String sessionId);

    /**
     * 根据主键列表启用
     *
     * @param ${table.uniquePrimaryKey.columnFieldName?uncap_first}List
     * @param sessionId
     * @return
     <#include "/include/author_info1.ftl">
     */
    @RequestMapping(value = "/${classNameLower}/enable", method = RequestMethod.POST)
    ResultVo<Integer> enable(@RequestBody List<${table.uniquePrimaryKey.columnFieldType}> ${table.uniquePrimaryKey.columnFieldName?uncap_first}List, @RequestParam(value = "sessionId", required = false) String sessionId);
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
    ResultVo<Integer> update(@RequestBody ${className}Entity entity<#if !table.hasAutoIncrementUniquePrimaryKey><#list primaryKey as column>, @RequestParam(value="old${column.columnFieldName}") ${column.columnFieldType} old${column.columnFieldName}</#list></#if>, @RequestParam(value = "sessionId", required = false) String sessionId);

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
    @RequestMapping(value = "/${classNameLower}/get", method = RequestMethod.GET)
    ResultVo<${className}Vo> get(<#list primaryKey as column><#if (column_index > 0)>, </#if>@RequestParam(value="${column.columnFieldNameFirstLower}") ${column.columnFieldType} ${column.columnFieldNameFirstLower}</#list>, @RequestParam(value = "sessionId", required = false) String sessionId);
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
    ResultVo<PageInfo<${className}Vo>> getPageInfo(@RequestBody ${className}SelectParameter parameter, @RequestParam(value = "sessionId", required = false) String sessionId);

}
