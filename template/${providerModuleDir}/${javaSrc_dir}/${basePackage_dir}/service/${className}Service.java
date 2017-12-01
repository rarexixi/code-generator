<#assign className = table.tableClassName>
<#assign classNameLower = table.tableClassNameFirstLower>
<#assign primaryKey = table.primaryKey>
<#assign primaryKeyParameters = table.primaryKeyParameters>
<#assign primaryKeyParameterValues = table.primaryKeyParameterValues>
<#assign firstPrimaryKey = table.firstPrimaryKey>
package ${basePackage}.service;

import org.xi.common.model.Pagination;
import ${basePackage}.condition.${className}Condition;
import ${basePackage}.entity.${className}Entity;
import ${basePackage}.parameter.${className}SelectParameter;
import ${basePackage}.vo.${className}Vo;

import java.util.List;

<#include "/include/java_copyright.ftl">
public interface ${className}Service extends BaseService<${className}Entity, ${className}Condition> {
    <#if table.hasPrimaryKey>

    /**
     * 根据主键更新
     *
     * @param ${classNameLower}
     * @return
     <#include "/include/author_info1.ftl">
     */
    int update${className}ByPk(${className}Entity ${classNameLower});

    /**
     * 根据主键物理删除
     *
     <#list primaryKey as column>
     * @param ${column.columnFieldNameFirstLower}
     </#list>
     * @return
     <#include "/include/author_info1.ftl">
     */
    int delete${className}ByPk(${primaryKeyParameters});

    /**
     * 根据主键获取
     *
     <#list primaryKey as column>
     * @param ${column.columnFieldNameFirstLower}
     </#list>
     * @return
     <#include "/include/author_info1.ftl">
     */
    ${className}Vo get${className}ByPk(${primaryKeyParameters});
    </#if>

    /**
     * 添加
     *
     * @param ${classNameLower}
     * @return
     <#include "/include/author_info1.ftl">
     */
    int add${className}(${className}Entity ${classNameLower});

    /**
     * 添加列表
     *
     * @param ${classNameLower}List
     * @return
    <#include "/include/author_info1.ftl">
     */
    int add${className}List(List<${className}Entity> ${classNameLower}List);

    /**
     * 分页查询
     *
     * @param parameter
     * @param pagination
     * @return
     <#include "/include/author_info1.ftl">
     */
    Pagination<${className}Vo> find${className}PageList(${className}SelectParameter parameter, Pagination pagination);

}
