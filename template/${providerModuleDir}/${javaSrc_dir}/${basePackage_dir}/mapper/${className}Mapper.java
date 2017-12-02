<#assign className = table.tableClassName>
<#assign classNameLower = table.tableClassNameFirstLower>
<#assign primaryKey = table.primaryKey>
<#assign primaryKeyParameters = table.primaryKeyParameters>
<#assign primaryKeyParameterValues = table.primaryKeyParameterValues>
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
     * @param ${column.columnFieldNameFirstLower}
     </#list>
     * @return
    <#include "/include/author_info1.ftl">
     */
    ${className}Vo get${className}ByPk(<#list primaryKey as column><#if (column_index > 0)>, </#if>@Param("${column.columnFieldNameFirstLower}") ${column.columnFieldType} ${column.columnFieldNameFirstLower}</#list>);
    </#if>

    /**
     * 查询
     *
     * @param parameter
     * @return
    <#include "/include/author_info1.ftl">
     */
    List<${className}Vo> find${className}List(${className}SelectParameter parameter);
}
