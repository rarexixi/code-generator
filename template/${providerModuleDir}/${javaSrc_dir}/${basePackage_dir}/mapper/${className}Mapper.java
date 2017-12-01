<#assign className = table.tableClassName>
<#assign classNameLower = table.tableClassNameFirstLower>
<#assign primaryKey = table.primaryKey>
package ${basePackage}.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import ${basePackage}.entity.${className}Entity;
import ${basePackage}.parameter.select.${className}SelectParameter;
import ${basePackage}.vo.${className}Vo;

<#include "/include/java_copyright.ftl">
@Mapper
public interface ${className}Mapper extends BaseMapper<${className}Entity> {

    /**
     * 根据主键获取
     *
     * @param pk
     * @return
    <#include "/include/author_info1.ftl">
     */
    ${className}Vo get${className}ByPk(@Param("pk") ${primaryKey.columnFieldType} pk);

    /**
     * 查询
     *
     * @param parameter
     * @return
    <#include "/include/author_info1.ftl">
     */
    List<${className}Vo> find${className}List(${className}SelectParameter parameter);
}
