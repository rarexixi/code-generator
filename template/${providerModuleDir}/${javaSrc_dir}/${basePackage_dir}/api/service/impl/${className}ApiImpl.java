<#assign className = table.tableClassName>
<#assign classNameLower = table.tableClassNameFirstLower>
<#assign primaryKeyColumn = table.primaryKey>
package ${basePackage}.api.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.xi.common.model.Result;
import org.xi.common.model.Pagination;
import org.xi.common.utils.LogUtil;

import ${basePackage}.api.service.${className}Api;
import ${basePackage}.entity.${className}Entity;
import ${basePackage}.parameter.select.${className}SelectParameter;
import ${basePackage}.service.${className}Service;
import ${basePackage}.vo.${className}Vo;

<#include "/include/java_copyright.ftl">
@Service("${className}Api")
public class ${className}ApiImpl implements ${className}Api {

    private static LogUtil logger = LogUtil.build(${className}ApiImpl.class);

    @Autowired
    private ${className}Service ${classNameLower}Service;

    /**
     * 添加
     *
     * @param ${classNameLower}
     * @param sessionId
     * @return
     <#include "/include/author_info1.ftl">
     */
    @Override
    public Result<${className}Entity> add${className}(${className}Entity ${classNameLower}, String sessionId) {
        return null;
    }

    /**
     * 添加列表
     *
     * @param ${classNameLower}List
     * @param sessionId
     * @return
    <#include "/include/author_info1.ftl">
     */
    @Override
    public Result<${className}Entity> add${className}(List<${className}Entity> ${classNameLower}List, String sessionId) {
        return null;
    }

    /**
     * 根据主键更新
     *
     * @param ${classNameLower}
     * @param sessionId
     * @return
     <#include "/include/author_info1.ftl">
     */
    @Override
    public Result<${className}Entity> update${className}(${className}Entity ${classNameLower}, String sessionId) {
        return null;
    }
    <#if table.hasIsActive>

    /**
     * 根据主键使之有效
     *
     * @param id
     * @param sessionId
     * @return
     <#include "/include/author_info1.ftl">
     */
    @Override
    public Result<${className}Entity> enable${className}ById(${primaryKeyColumn.columnFieldType} ${primaryKeyColumn.columnFieldNameFirstLower}, String sessionId) {
        return null;
    }

    /**
     * 根据主键列表使之有效
     *
     * @param ids
     * @param sessionId
     * @return
     <#include "/include/author_info1.ftl">
     */
    @Override
    public Result<${className}Entity> enable${className}ByIdList(List<${primaryKeyColumn.columnFieldType}> ${primaryKeyColumn.columnFieldNameFirstLower}s, String sessionId) {
        return null;
    }

    /**
     * 根据主键使之无效
     *
     * @param id
     * @param sessionId
     * @return
     <#include "/include/author_info1.ftl">
     */
    @Override
    public Result<${className}Entity> disable${className}ById(${primaryKeyColumn.columnFieldType} ${primaryKeyColumn.columnFieldNameFirstLower}, String sessionId) {
        return null;
    }

    /**
     * 根据主键列表使之无效
     *
     * @param ids
     * @param sessionId
     * @return
     <#include "/include/author_info1.ftl">
     */
    @Override
    public Result<${className}Entity> disable${className}ByIdList(List<${primaryKeyColumn.columnFieldType}> ${primaryKeyColumn.columnFieldNameFirstLower}s, String sessionId) {
        return null;
    }
    </#if>

    /**
     * 根据主键物理删除
     *
     * @param id
     * @param sessionId
     * @return
     <#include "/include/author_info1.ftl">
     */
    @Override
    public Result<${className}Entity> delete${className}ById(${primaryKeyColumn.columnFieldType} ${primaryKeyColumn.columnFieldNameFirstLower}, String sessionId) {
        return null;
    }


    /**
     * 根据主键获取
     *
     * @param id
     * @param sessionId
     * @return
     <#include "/include/author_info1.ftl">
     */
    @Override
    public Result<${className}Vo> get${className}ById(${primaryKeyColumn.columnFieldType} ${primaryKeyColumn.columnFieldNameFirstLower}, String sessionId) {
        return null;
    }

    /**
     * 分页查询
     *
     * @param parameter
     * @param pagination
     * @param sessionId
     * @return
     <#include "/include/author_info1.ftl">
     */
    @Override
    public Result<Pagination<${className}Vo>> find${className}PageList(${className}SelectParameter parameter, Pagination pagination, String sessionId) {
        return null;
    }

}
