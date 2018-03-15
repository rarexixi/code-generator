<#assign className = table.tableClassName>
<#assign classNameLower = table.tableClassName?uncap_first>
<#assign primaryKey = table.primaryKey>
<#assign primaryKeyParameters = table.primaryKeyParameters>
<#assign primaryKeyParameterValues = table.primaryKeyParameterValues>
package ${basePackage}.admin.service;

import ${baseCommonPackage}.model.Result;
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
    Result<Integer> add(@RequestBody ${className}Entity entity, @RequestParam(value = "sessionId", required = false) String sessionId);

    /**
     * 添加列表
     *
     * @param list
     * @param sessionId
     * @return
    <#include "/include/author_info1.ftl">
     */
    @RequestMapping(value = "/${classNameLower}/addList", method = RequestMethod.POST)
    Result<Integer> addList(@RequestBody List<${className}Entity> list, @RequestParam(value = "sessionId", required = false) String sessionId);
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
    @RequestMapping(value = "/${classNameLower}/deleteByPk", method = RequestMethod.GET)
    Result<Integer> deleteByPk(<#list primaryKey as column><#if (column_index > 0)>, </#if>@RequestParam(value="${column.columnFieldNameFirstLower}") ${column.columnFieldType} ${column.columnFieldNameFirstLower}</#list>, @RequestParam(value = "sessionId", required = false) String sessionId);
    <#if (table.uniquePrimaryKey??)>

    /**
     * 根据主键列表物理删除
     *
     * @param ${table.uniquePrimaryKey.columnFieldName?uncap_first}List
     * @param sessionId
     * @return
     <#include "/include/author_info1.ftl">
     */
    @RequestMapping(value = "/${classNameLower}/deleteByPkList", method = RequestMethod.POST)
    Result<Integer> deleteByPkList(@RequestBody List<${table.uniquePrimaryKey.columnFieldType}> ${table.uniquePrimaryKey.columnFieldName?uncap_first}List, @RequestParam(value = "sessionId", required = false) String sessionId);
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
    @RequestMapping(value = "/${classNameLower}/disableByPk", method = RequestMethod.GET)
    Result<Integer> disableByPk(<#list primaryKey as column><#if (column_index > 0)>, </#if>@RequestParam(value="${column.columnFieldNameFirstLower}") ${column.columnFieldType} ${column.columnFieldNameFirstLower}</#list>, @RequestParam(value = "sessionId", required = false) String sessionId);

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
    @RequestMapping(value = "/${classNameLower}/enableByPk", method = RequestMethod.GET)
    Result<Integer> enableByPk(<#list primaryKey as column><#if (column_index > 0)>, </#if>@RequestParam(value="${column.columnFieldNameFirstLower}") ${column.columnFieldType} ${column.columnFieldNameFirstLower}</#list>, @RequestParam(value = "sessionId", required = false) String sessionId);
    <#if (table.uniquePrimaryKey??)>

    /**
     * 根据主键列表冻结
     *
     * @param ${table.uniquePrimaryKey.columnFieldName?uncap_first}List
     * @param sessionId
     * @return
     <#include "/include/author_info1.ftl">
     */
    @RequestMapping(value = "/${classNameLower}/disableByPkList", method = RequestMethod.POST)
    Result<Integer> disableByPkList(@RequestBody List<${table.uniquePrimaryKey.columnFieldType}> ${table.uniquePrimaryKey.columnFieldName?uncap_first}List, @RequestParam(value = "sessionId", required = false) String sessionId);

    /**
     * 根据主键列表激活
     *
     * @param ${table.uniquePrimaryKey.columnFieldName?uncap_first}List
     * @param sessionId
     * @return
     <#include "/include/author_info1.ftl">
     */
    @RequestMapping(value = "/${classNameLower}/enableByPkList", method = RequestMethod.POST)
    Result<Integer> enableByPkList(@RequestBody List<${table.uniquePrimaryKey.columnFieldType}> ${table.uniquePrimaryKey.columnFieldName?uncap_first}List, @RequestParam(value = "sessionId", required = false) String sessionId);
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
    @RequestMapping(value = "/${classNameLower}/updateByPk", method = RequestMethod.POST)
    Result<Integer> updateByPk(@RequestBody ${className}Entity entity<#if !table.hasAutoIncrementUniquePrimaryKey><#list primaryKey as column>, @RequestParam(value="old${column.columnFieldName}") ${column.columnFieldType} old${column.columnFieldName}</#list></#if>, @RequestParam(value = "sessionId", required = false) String sessionId);

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
    @RequestMapping(value = "/${classNameLower}/getByPk", method = RequestMethod.GET)
    Result<${className}Vo> getByPk(<#list primaryKey as column><#if (column_index > 0)>, </#if>@RequestParam(value="${column.columnFieldNameFirstLower}") ${column.columnFieldType} ${column.columnFieldNameFirstLower}</#list>, @RequestParam(value = "sessionId", required = false) String sessionId);
    </#if>

    /**
     * 分页查询
     *
     * @param parameter
     * @param sessionId
     * @return
     <#include "/include/author_info1.ftl">
     */
    @RequestMapping(value = "/${classNameLower}/findPageList", method = RequestMethod.POST)
    Result<PageInfo<${className}Vo>> findPageList(@RequestBody ${className}SelectParameter parameter, @RequestParam(value = "sessionId", required = false) String sessionId);

}
