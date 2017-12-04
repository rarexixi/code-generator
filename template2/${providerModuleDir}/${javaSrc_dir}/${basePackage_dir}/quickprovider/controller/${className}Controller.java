<#assign className = table.tableClassName>
<#assign classNameLower = table.tableClassNameFirstLower>
<#assign primaryKey = table.primaryKey>
<#assign primaryKeyParameters = table.primaryKeyParameters>
<#assign primaryKeyParameterValues = table.primaryKeyParameterValues>
package ${basePackage}.quickprovider.controller;

import ${resultFullClass};
import ${paginationFullClass};
import org.xi.common.utils.LogUtil;
import ${basePackage}.entity.${className}Entity;
import ${basePackage}.parameter.${className}SelectParameter;
import ${basePackage}.quickprovider.service.${className}Service;
import ${basePackage}.vo.${className}Vo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

<#include "/include/java_copyright.ftl">
@RestController
@RequestMapping("/${classNameLower}")
public class ${className}Controller {

    private static LogUtil logger = LogUtil.build(${className}Controller.class);

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
    @RequestMapping("/add${className}")
    public ${resultClass}<${className}Entity> add${className}(${className}Entity ${classNameLower}, String sessionId) {
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
    @RequestMapping("/add${className}List")
    public ${resultClass}<${className}Entity> add${className}List(List<${className}Entity> ${classNameLower}List, String sessionId) {
        return null;
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
    @RequestMapping("/find${className}PageList")
    public ${resultClass}<${paginationClass}<${className}Vo>> find${className}PageList(${className}SelectParameter parameter, ${paginationClass} pagination, String sessionId) {
        return null;
    }

}
