<#assign className = table.tableClassName>
<#assign classNameLower = table.tableClassName?uncap_first>
<#assign primaryKey = table.primaryKey>
<#assign primaryKeyParameters = table.primaryKeyParameters>
<#assign primaryKeyParameterValues = table.primaryKeyParameterValues>
package ${basePackage}.api;

import ${baseCommonPackage}.annotation.ParamName;
import ${baseCommonPackage}.model.ResultVo;
import ${baseCommonPackage}.model.SearchPage;
import ${baseCommonPackage}.validation.DataAdd;
import ${baseCommonPackage}.validation.DataEdit;
import ${basePackage}.entity.${className}Entity;
import ${basePackage}.parameter.${className}SelectParameter;
import ${basePackage}.vo.${className}Vo;

import com.github.pagehelper.PageInfo;

import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;
import java.util.List;

<#include "/include/java_copyright.ftl">
public interface ${className}Api {

    /**
     * 添加
     *
     * @param entity
     * @param sessionId
     * @return
     <#include "/include/author_info1.ftl">
     */
    ResultVo<${className}Entity> add(@Validated({DataAdd.class}) ${className}Entity entity, @ParamName("sessionId") String sessionId);

    /**
     * 添加列表
     *
     * @param list
     * @param sessionId
     * @return
    <#include "/include/author_info1.ftl">
     */
    ResultVo<Integer> addList(@Validated({DataAdd.class}) List<${className}Entity> list, @ParamName("sessionId") String sessionId);
    <#if table.hasPrimaryKey>

    /**
     * 根据主键物理删除
     *
     <#list primaryKey as column>
     * @param ${column.targetColumnNameFirstLower}
     </#list>
     * @param sessionId
     * @return
     <#include "/include/author_info1.ftl">
     */
    ResultVo<Integer> deleteByPk(<#list primaryKey as column>@NotNull ${column.targetDataType} ${column.targetColumnName?uncap_first}, </#list>@ParamName("sessionId") String sessionId);
    <#if (table.uniquePrimaryKey??)>

    /**
     * 根据主键列表物理删除
     *
     * @param ${table.uniquePrimaryKey.targetColumnName?uncap_first}List
     * @param sessionId
     * @return
     <#include "/include/author_info1.ftl">
     */
    ResultVo<Integer> deleteByPkList(@NotNull List<${table.uniquePrimaryKey.targetDataType}> ${table.uniquePrimaryKey.targetColumnName?uncap_first}List, @ParamName("sessionId") String sessionId);
    </#if>
    <#if table.validStatusColumn??>

    /**
     * 根据主键禁用
     *
     <#list primaryKey as column>
     * @param ${column.targetColumnNameFirstLower}
     </#list>
     * @param sessionId
     * @return
     <#include "/include/author_info1.ftl">
     */
    ResultVo<Integer> disableByPk(<#list primaryKey as column>@NotNull ${column.targetDataType} ${column.targetColumnName?uncap_first}, </#list>@ParamName("sessionId") String sessionId);

    /**
     * 根据主键启用
     *
     <#list primaryKey as column>
     * @param ${column.targetColumnNameFirstLower}
     </#list>
     * @param sessionId
     * @return
     <#include "/include/author_info1.ftl">
     */
    ResultVo<Integer> enableByPk(<#list primaryKey as column>@NotNull ${column.targetDataType} ${column.targetColumnName?uncap_first}, </#list>@ParamName("sessionId") String sessionId);
    <#if (table.uniquePrimaryKey??)>

    /**
     * 根据主键列表禁用
     *
     * @param ${table.uniquePrimaryKey.targetColumnName?uncap_first}List
     * @param sessionId
     * @return
     <#include "/include/author_info1.ftl">
     */
    ResultVo<Integer> disableByPkList(@NotNull List<${table.uniquePrimaryKey.targetDataType}> ${table.uniquePrimaryKey.targetColumnName?uncap_first}List, @ParamName("sessionId") String sessionId);

    /**
     * 根据主键列表启用
     *
     * @param ${table.uniquePrimaryKey.targetColumnName?uncap_first}List
     * @param sessionId
     * @return
     <#include "/include/author_info1.ftl">
     */
    ResultVo<Integer> enableByPkList(@NotNull List<${table.uniquePrimaryKey.targetDataType}> ${table.uniquePrimaryKey.targetColumnName?uncap_first}List, @ParamName("sessionId") String sessionId);
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
    ResultVo<Integer> updateByPk(@Validated({DataEdit.class}) ${className}Entity entity<#if !table.hasAutoIncrementUniquePrimaryKey><#list primaryKey as column>, @NotNull ${column.targetDataType} old${column.targetColumnName}</#list></#if>, @ParamName("sessionId") String sessionId);

    /**
     * 根据主键获取
     *
     <#list primaryKey as column>
     * @param ${column.targetColumnNameFirstLower}
     </#list>
     * @param sessionId
     * @return
     <#include "/include/author_info1.ftl">
     */
    ResultVo<${className}Vo> getByPk(<#list primaryKey as column>@NotNull ${column.targetDataType} ${column.targetColumnName?uncap_first}, </#list>@ParamName("sessionId") String sessionId);
    </#if>

    /**
     * 分页查询
     *
     * @param parameter
     * @param sessionId
     * @return
     <#include "/include/author_info1.ftl">
     */
    ResultVo<PageInfo<${className}Vo>> findPageList(${className}SelectParameter parameter, @ParamName("sessionId") String sessionId);
}
