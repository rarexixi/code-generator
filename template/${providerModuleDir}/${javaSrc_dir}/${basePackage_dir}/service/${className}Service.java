<#assign className = table.tableClassName>
<#assign classNameLower = table.tableClassNameFirstLower>
<#assign primaryKeyColumn = table.primaryKey>

package ${basePackage}.service;

import java.util.List;

import org.xi.common.model.Pagination;
import ${basePackage}.entity.${className}Entity;
import ${basePackage}.parameter.select.${className}SelectParameter;
import ${basePackage}.vo.${className}Vo;

<#include "/include/java_copyright.ftl">
public interface ${className}Service extends BaseService<${className}Entity> {

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
     * 根据主键更新
     *
     * @param ${classNameLower}
     * @return
     <#include "/include/author_info1.ftl">
     */
    int update${className}(${className}Entity ${classNameLower});
    <#if table.hasIsActive>

    /**
     * 根据主键使之有效
     *
     * @param pk
     * @return
     <#include "/include/author_info1.ftl">
     */
    int enable${className}ByPk(${primaryKeyColumn.columnFieldType} pk);

    /**
     * 根据主键列表使之有效
     *
     * @param pks
     * @return
     <#include "/include/author_info1.ftl">
     */
    int enable${className}ByPkList(List<${primaryKeyColumn.columnFieldType}> pks);

    /**
     * 根据主键使之无效
     *
     * @param pk
     * @return
     <#include "/include/author_info1.ftl">
     */
    int disable${className}ByPk(${primaryKeyColumn.columnFieldType} pk);

    /**
     * 根据主键列表使之无效
     *
     * @param pks
     * @return
     <#include "/include/author_info1.ftl">
     */
    int disable${className}ByPkList(List<${primaryKeyColumn.columnFieldType}> pks);
    </#if>

    /**
     * 根据主键物理删除
     *
     * @param pk
     * @return
     <#include "/include/author_info1.ftl">
     */
    int delete${className}ByPk(${primaryKeyColumn.columnFieldType} pk);

    /**
     * 根据主键获取
     *
     * @param pk
     * @return
     <#include "/include/author_info1.ftl">
     */
    ${className}Vo get${className}ByPk(${primaryKeyColumn.columnFieldType} pk);

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
