<#assign className = table.tableClassName>
<#assign classNameLower = table.tableClassName?uncap_first>
<#assign primaryKey = table.primaryKey>
package ${basePackage}.mapper;

import ${basePackage}.condition.${className}Condition;
import ${basePackage}.entity.${className}Entity;
import ${basePackage}.parameter.${className}SelectParameter;
import ${basePackage}.vo.${className}Vo;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

<#include "/include/java_copyright.ftl">
@Mapper
public interface ${className}Mapper extends BaseMapper<${className}Entity, ${className}Condition> {
    <#if table.hasPrimaryKey>

    /**
     * 根据主键获取
     *
     <#list primaryKey as column>
     * @param ${column.targetColumnNameFirstLower}
     </#list>
     * @return
    <#include "/include/author_info1.ftl">
     */
    ${className}Vo getByPk(<#list primaryKey as column><#if (column_index > 0)>, </#if>@Param("${column.targetColumnNameFirstLower}") ${column.targetDataType} ${column.targetColumnNameFirstLower}</#list>);
    </#if>

    /**
     * 查询
     *
     * @param parameter
     * @return
    <#include "/include/author_info1.ftl">
     */
    List<${className}Vo> findList(${className}SelectParameter parameter);
}
