<#assign className = table.tableClassName>
<#assign classNameLower = table.tableClassNameFirstLower>
<#assign primaryKey = table.primaryKey>
<#assign primaryKeyParameters = table.primaryKeyParameters>
<#assign primaryKeyParameterValues = table.primaryKeyParameterValues>
package ${basePackage}.service.impl;

import ${paginationFullClass};
import ${basePackage}.condition.${className}Condition;
import ${basePackage}.condition.select.${className}SelectCondition;
import ${basePackage}.entity.${className}Entity;
import ${basePackage}.mapper.${className}Mapper;
import ${basePackage}.parameter.${className}SelectParameter;
import ${basePackage}.service.${className}Service;
import ${basePackage}.vo.${className}Vo;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

<#include "/include/java_copyright.ftl">
@Service("${classNameLower}Service")
@Transactional
public class ${className}ServiceHystric implements ${className}Service {

    /**
     * 添加
     *
     * @param ${classNameLower}
     * @param sessionId
     * @return
     <#include "/include/author_info1.ftl">
     */
    @Override
    @RequestMapping("/add${className}")
    public ${resultClass}<${className}Entity> add${className}(${className}Entity ${classNameLower}, String sessionId) {

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
    @RequestMapping("/add${className}List")
    public ${resultClass}<${className}Entity> add${className}List(List<${className}Entity> ${classNameLower}List, String sessionId) {

    }
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
    @Override
    @RequestMapping("/delete${className}ByPk")
    public ${resultClass}<${className}Entity> delete${className}ByPk(${primaryKeyParameters}, String sessionId) {

    }
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
    @Override
    @RequestMapping("/disable${className}ByPk")
    public ${resultClass}<${className}Entity> disable${className}ByPk(${primaryKeyParameters}, String sessionId) {

    }

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
    @Override
    @RequestMapping("/enable${className}ByPk")
    public ${resultClass}<${className}Entity> enable${className}ByPk(${primaryKeyParameters}, String sessionId) {

    }
    </#if>

    /**
     * 根据主键更新
     *
     * @param ${classNameLower}
     * @param sessionId
     * @return
     <#include "/include/author_info1.ftl">
     */
    @Override
    @RequestMapping("/update${className}ByPk")
    public ${resultClass}<${className}Entity> update${className}ByPk(${className}Entity ${classNameLower}, String sessionId) {

    }

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
    @Override
    @RequestMapping("/get${className}ByPk")
    public ${resultClass}<${className}Vo> get${className}ByPk(${primaryKeyParameters}, String sessionId) {

    }
    </#if>

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
    @RequestMapping("/find${className}PageList")
    public ${resultClass}<${paginationClass}<${className}Vo>> find${className}PageList(${className}SelectParameter parameter, ${paginationClass} pagination, String sessionId) {

    }

}
