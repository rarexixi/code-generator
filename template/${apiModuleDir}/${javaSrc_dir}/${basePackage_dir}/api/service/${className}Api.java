<#assign className = table.tableClassName>
<#assign classNameLower = table.tableClassNameFirstLower>
<#assign primaryKeyColumn = table.primaryKey>

package ${basePackage}.api.service;

import java.util.List;

import org.xi.common.model.Result;
import org.xi.common.model.Pagination;
import ${basePackage}.entity.${className}Entity;
import ${basePackage}.parameter.select.${className}SelectParameter;
import ${basePackage}.vo.${className}Vo;

<#include "/include/java_copyright.ftl">
public interface ${className}Api {

    /**
     * 添加
     *
     * @param ${classNameLower}
     * @param sessionId
     * @return
     <#include "/include/author_info1.ftl">
     */
    Result<${className}Entity> add${className}(${className}Entity ${classNameLower}, String sessionId);

    /**
     * 添加列表
     *
     * @param ${classNameLower}List
     * @param sessionId
     * @return
    <#include "/include/author_info1.ftl">
     */
    Result<${className}Entity> add${className}(List<${className}Entity> ${classNameLower}List, String sessionId);

    /**
     * 根据主键更新
     *
     * @param ${classNameLower}
     * @param sessionId
     * @return
     <#include "/include/author_info1.ftl">
     */
    Result<${className}Entity> update${className}(${className}Entity ${classNameLower}, String sessionId);
    <#if table.hasIsActive>

    /**
     * 根据主键使之有效
     *
     * @param id
     * @param sessionId
     * @return
     <#include "/include/author_info1.ftl">
     */
    Result<${className}Entity> enable${className}ById(${primaryKeyColumn.columnFieldType} ${primaryKeyColumn.columnFieldNameFirstLower}, String sessionId);

    /**
     * 根据主键列表使之有效
     *
     * @param ids
     * @param sessionId
     * @return
     <#include "/include/author_info1.ftl">
     */
    Result<${className}Entity> enable${className}ByIdList(List<${primaryKeyColumn.columnFieldType}> ${primaryKeyColumn.columnFieldNameFirstLower}s, String sessionId);

    /**
     * 根据主键使之无效
     *
     * @param id
     * @param sessionId
     * @return
     <#include "/include/author_info1.ftl">
     */
    Result<${className}Entity> disable${className}ById(${primaryKeyColumn.columnFieldType} ${primaryKeyColumn.columnFieldNameFirstLower}, String sessionId);

    /**
     * 根据主键列表使之无效
     *
     * @param ids
     * @param sessionId
     * @return
     <#include "/include/author_info1.ftl">
     */
    Result<${className}Entity> disable${className}ByIdList(List<${primaryKeyColumn.columnFieldType}> ${primaryKeyColumn.columnFieldNameFirstLower}s, String sessionId);
    </#if>

    /**
     * 根据主键物理删除
     *
     * @param id
     * @param sessionId
     * @return
     <#include "/include/author_info1.ftl">
     */
    Result<${className}Entity> delete${className}ById(${primaryKeyColumn.columnFieldType} ${primaryKeyColumn.columnFieldNameFirstLower}, String sessionId);


    /**
     * 根据主键获取
     *
     * @param id
     * @param sessionId
     * @return
     <#include "/include/author_info1.ftl">
     */
    Result<${className}Vo> get${className}ById(${primaryKeyColumn.columnFieldType} ${primaryKeyColumn.columnFieldNameFirstLower}, String sessionId);

    /**
     * 分页查询
     *
     * @param parameter
     * @param pagination
     * @param sessionId
     * @return
     <#include "/include/author_info1.ftl">
     */
    Result<Pagination<${className}Vo>> find${className}PageList(${className}SelectParameter parameter, Pagination pagination, String sessionId);

}
