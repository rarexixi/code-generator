<#assign className = table.tableClassName>
<#assign classNameLower = table.tableClassNameFirstLower>
<#assign primaryKey = table.primaryKey>
<#assign primaryKeyParameters = table.primaryKeyParameters>
<#assign primaryKeyParameterValues = table.primaryKeyParameterValues>
package ${basePackage}.quickapi.service;

import org.xi.common.model.Result;
import ${basePackage}.entity.${className}Entity;
import ${basePackage}.parameter.${className}SelectParameter;
import ${basePackage}.vo.${className}Vo;
import ${basePackage}.quickapi.service.hystric.${className}ServiceHystric;

import com.github.pagehelper.PageInfo;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

<#include "/include/java_copyright.ftl">
@FeignClient(value = "${serviceProvider}", fallback = ${className}ServiceHystric.class)
public interface ${className}Service {

    /**
     * 添加
     *
     * @param ${classNameLower}
     * @param sessionId
     * @return
     <#include "/include/author_info1.ftl">
     */
    @RequestMapping(value = "/${classNameLower}/add${className}", method = RequestMethod.POST)
    Result<${className}Entity> add${className}(${className}Entity ${classNameLower}, String sessionId);

    /**
     * 添加列表
     *
     * @param ${classNameLower}List
     * @param sessionId
     * @return
    <#include "/include/author_info1.ftl">
     */
    @RequestMapping(value = "/${classNameLower}/add${className}List", method = RequestMethod.POST)
    Result<${className}Entity> add${className}List(List<${className}Entity> ${classNameLower}List, String sessionId);
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
    @RequestMapping(value = "/${classNameLower}/delete${className}ByPk", method = RequestMethod.GET)
    Result<${className}Entity> delete${className}ByPk(${primaryKeyParameters}, String sessionId);
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
    @RequestMapping(value = "/${classNameLower}/disable${className}ByPk", method = RequestMethod.GET)
    Result<${className}Entity> disable${className}ByPk(${primaryKeyParameters}, String sessionId);

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
    @RequestMapping(value = "/${classNameLower}/enable${className}ByPk", method = RequestMethod.GET)
    Result<${className}Entity> enable${className}ByPk(${primaryKeyParameters}, String sessionId);
    </#if>

    /**
     * 根据主键更新
     *
     * @param ${classNameLower}
     * @param sessionId
     * @return
     <#include "/include/author_info1.ftl">
     */
    @RequestMapping(value = "/${classNameLower}/update${className}ByPk", method = RequestMethod.POST)
    Result<${className}Entity> update${className}ByPk(${className}Entity ${classNameLower}, String sessionId);

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
    @RequestMapping(value = "/${classNameLower}/get${className}ByPk", method = RequestMethod.GET)
    Result<${className}Vo> get${className}ByPk(${primaryKeyParameters}, String sessionId);
    </#if>

    /**
     * 分页查询
     *
     * @param parameter
     * @param page
     * @param sessionId
     * @return
     <#include "/include/author_info1.ftl">
     */
    @RequestMapping(value = "/${classNameLower}/find${className}PageList", method = RequestMethod.GET)
    Result<PageInfo<${className}Vo>> find${className}PageList(${className}SelectParameter parameter, PageInfo page, String sessionId);
}
