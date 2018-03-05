<#assign className = table.tableClassName>
<#assign classNameLower = table.tableClassName?uncap_first>
<#assign primaryKey = table.primaryKey>
<#assign primaryKeyParameters = table.primaryKeyParameters>
<#assign primaryKeyParameterValues = table.primaryKeyParameterValues>
package ${basePackage}.admin.service;

import ${baseCommonPackage}.model.Result;
import ${baseCommonPackage}.model.SearchPage;
import ${basePackage}.entity.${className}Entity;
import ${basePackage}.parameter.${className}SelectParameter;
import ${basePackage}.vo.${className}Vo;

import com.github.pagehelper.PageInfo;

import java.util.List;

<#include "/include/java_copyright.ftl">
public interface ${className}Service {

    /**
     * 添加
     *
     * @param entity
     * @return
     <#include "/include/author_info1.ftl">
     */
    Result<Integer> add(${className}Entity entity);

    /**
     * 添加列表
     *
     * @param list
     * @return
    <#include "/include/author_info1.ftl">
     */
    Result<Integer> addList(List<${className}Entity> list);
    <#if table.hasPrimaryKey>

    /**
     * 根据主键物理删除
     *
     <#list primaryKey as column>
     * @param ${column.columnFieldNameFirstLower}
     </#list>
     * @return
     <#include "/include/author_info1.ftl">
     */
    Result<Integer> deleteByPk(${primaryKeyParameters});
    <#if table.validStatusColumn??>

    /**
     * 根据主键冻结
     *
     <#list primaryKey as column>
     * @param ${column.columnFieldNameFirstLower}
     </#list>
     * @return
     <#include "/include/author_info1.ftl">
     */
    Result<Integer> disableByPk(${primaryKeyParameters});

    /**
     * 根据主键激活
     *
     <#list primaryKey as column>
     * @param ${column.columnFieldNameFirstLower}
     </#list>
     * @return
     <#include "/include/author_info1.ftl">
     */
    Result<Integer> enableByPk(${primaryKeyParameters});
    </#if>

    /**
     * 根据主键更新
     *
     * @param entity
     * @return
     <#include "/include/author_info1.ftl">
     */
    Result<Integer> updateByPk(${className}Entity entity<#if !table.hasAutoIncrementUniquePrimaryKey><#list primaryKey as column>, ${column.columnFieldType} old${column.columnFieldName}</#list></#if>);

    /**
     * 根据主键获取
     *
     <#list primaryKey as column>
     * @param ${column.columnFieldNameFirstLower}
     </#list>
     * @return
     <#include "/include/author_info1.ftl">
     */
    Result<${className}Vo> getByPk(${primaryKeyParameters});
    </#if>

    /**
     * 分页查询
     *
     * @param parameter
     * @return
     <#include "/include/author_info1.ftl">
     */
    Result<PageInfo<${className}Vo>> findPageList(${className}SelectParameter parameter);
}
