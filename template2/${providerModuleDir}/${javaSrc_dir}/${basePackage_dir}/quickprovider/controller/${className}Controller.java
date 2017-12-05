<#assign className = table.tableClassName>
<#assign classNameLower = table.tableClassNameFirstLower>
<#assign primaryKey = table.primaryKey>
<#assign primaryKeyParameters = table.primaryKeyParameters>
<#assign primaryKeyParameterValues = table.primaryKeyParameterValues>
package ${basePackage}.quickprovider.controller;

import org.xi.common.constant.OperationConstants;
import org.xi.common.model.Result;
import org.xi.common.utils.LogUtil;
import org.xi.common.utils.StringUtil;
import org.xi.common.utils.database.OperationCheckUtil;
import ${basePackage}.entity.${className}Entity;
import ${basePackage}.parameter.${className}SelectParameter;
import ${basePackage}.quickprovider.service.${className}Service;
import ${basePackage}.vo.${className}Vo;

import com.github.pagehelper.PageInfo;

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
    public Result<Integer> add${className}(${className}Entity ${classNameLower}, String sessionId) {

        String fieldName = "";
        if (${classNameLower} == null || !StringUtil.isNullOrEmpty(fieldName = OperationCheckUtil.checkInsert(${classNameLower}))) {
            return new Result<>(OperationConstants.NOT_NULL.getCode(), fieldName + OperationConstants.NOT_NULL.getMessage());
        }

        Result<Integer> result;
        try {
            int count = ${classNameLower}Service.add${className}(${classNameLower});
            result = new Result<>(count);
        } catch (Exception e) {
            logger.error("add${className}", sessionId, e);
            result = new Result<>(OperationConstants.SYSTEM_ERROR);
        }

        return result;
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
    public Result<Integer> add${className}List(List<${className}Entity> ${classNameLower}List, String sessionId) {

        if (${classNameLower}List == null || ${classNameLower}List.isEmpty()) {
            return new Result<>(OperationConstants.NOT_NULL);
        }
        String fieldName = "";
        for (${className}Entity ${classNameLower} : ${classNameLower}List) {
            if (!StringUtil.isNullOrEmpty(fieldName = OperationCheckUtil.checkInsert(${classNameLower}))) {
                return new Result<>(OperationConstants.NOT_NULL.getCode(), fieldName + OperationConstants.NOT_NULL.getMessage());
            }
        }

        Result<Integer> result;
        try {
            int count = ${classNameLower}Service.add${className}List(${classNameLower}List);
            result = new Result<>(count);
        } catch (Exception e) {
            logger.error("add${className}List", sessionId, e);
            result = new Result<>(OperationConstants.SYSTEM_ERROR);
        }

        return result;
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
    public Result<Integer> delete${className}ByPk(${primaryKeyParameters}, String sessionId) {

        Result<Integer> result;
        try {
            int count = ${classNameLower}Service.delete${className}ByPk(${primaryKeyParameterValues});
            result = new Result<>(count);
        } catch (Exception e) {
            logger.error("delete${className}ByPk", sessionId, e);
            result = new Result<>(OperationConstants.SYSTEM_ERROR);
        }

        return result;
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
    public Result<Integer> disable${className}ByPk(${primaryKeyParameters}, String sessionId) {

        Result<Integer> result;
        try {
            int count = ${classNameLower}Service.disable${className}ByPk(${primaryKeyParameterValues});
            result = new Result<>(count);
        } catch (Exception e) {
            logger.error("disable${className}ByPk", sessionId, e);
            result = new Result<>(OperationConstants.SYSTEM_ERROR);
        }

        return result;
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
    public Result<Integer> enable${className}ByPk(${primaryKeyParameters}, String sessionId) {

        Result<Integer> result;
        try {
            int count = ${classNameLower}Service.enable${className}ByPk(${primaryKeyParameterValues});
            result = new Result<>(count);
        } catch (Exception e) {
            logger.error("enable${className}ByPk", sessionId, e);
            result = new Result<>(OperationConstants.SYSTEM_ERROR);
        }

        return result;
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
    public Result<Integer> update${className}ByPk(${className}Entity ${classNameLower}, String sessionId) {

        String fieldName = "";
        if (${classNameLower} == null || !StringUtil.isNullOrEmpty(fieldName = OperationCheckUtil.checkUpdate(${classNameLower}))) {
            return new Result<>(OperationConstants.NOT_NULL.getCode(), fieldName + OperationConstants.NOT_NULL.getMessage());
        }

        Result<Integer> result;
        try {
            int count = ${classNameLower}Service.update${className}ByPk(${classNameLower});
            result = new Result<>(count);
        } catch (Exception e) {
            logger.error("update${className}ByPk", sessionId, e);
            result = new Result<>(OperationConstants.SYSTEM_ERROR);
        }

        return result;
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
    public Result<${className}Vo> get${className}ByPk(${primaryKeyParameters}, String sessionId) {

        Result<${className}Vo> result;
        try {
            ${className}Vo vo = ${classNameLower}Service.get${className}ByPk(${primaryKeyParameterValues});
            result = new Result<>(vo);
        } catch (Exception e) {
            logger.error("get${className}ByPk", sessionId, e);
            result = new Result<>(OperationConstants.SYSTEM_ERROR);
        }

        return result;
    }
    </#if>

    /**
     * 分页查询
     *
     * @param parameter
     * @param page
     * @param sessionId
     * @return
     <#include "/include/author_info1.ftl">
     */
    @RequestMapping("/find${className}PageList")
    public Result<PageInfo<${className}Vo>> find${className}PageList(${className}SelectParameter parameter, PageInfo page, String sessionId) {

        Result<PageInfo<${className}Vo>> result;
        try {
            PageInfo<${className}Vo> paginationVo = ${classNameLower}Service.find${className}PageList(parameter, page);
            result = new Result<>(paginationVo);
        } catch (Exception e) {
            logger.error("find${className}PageList", sessionId, e);
            result = new Result<>(OperationConstants.SYSTEM_ERROR);
        }

        return result;
    }

}
