<#assign className = table.tableClassName>
<#assign classNameLower = table.tableClassName?uncap_first>
<#assign primaryKey = table.primaryKey>
<#assign primaryKeyParameters = table.primaryKeyParameters>
<#assign primaryKeyParameterValues = table.primaryKeyParameterValues>
package ${basePackage}.controller;

import ${baseCommonPackage}.constant.OperationConstants;
import ${baseCommonPackage}.model.Result;
import ${baseCommonPackage}.utils.LogUtil;
import ${baseCommonPackage}.utils.StringUtil;
import ${baseCommonPackage}.utils.database.OperationCheckUtil;
import ${basePackage}.entity.${className}Entity;
import ${basePackage}.parameter.${className}SelectParameter;
import ${basePackage}.service.${className}Service;
import ${basePackage}.vo.${className}Vo;

import com.github.pagehelper.PageInfo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

<#include "/include/java_copyright.ftl">
@RestController
@RequestMapping("/${table.tableClassName?lower_case}")
public class ${className}Controller {

    private static LogUtil logger = LogUtil.build(${className}Controller.class);

    @Autowired
    private ${className}Service ${classNameLower}Service;

    /**
     * 添加列表
     *
     * @param ${classNameLower}List
     * @return
    <#include "/include/author_info1.ftl">
     */
    @RequestMapping(value = "/addList", method = RequestMethod.POST)
    public Result<Integer> addList(@RequestBody List<${className}Entity> ${classNameLower}List) {

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
            int count = ${classNameLower}Service.addList(${classNameLower}List);
            result = new Result<>(count);
        } catch (Exception e) {
            logger.error("addList", e);
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
     * @return
     <#include "/include/author_info1.ftl">
     */
     @RequestMapping(value = { "/delete", "/delete/" }, method = RequestMethod.GET)
     public Result<Integer> delete(<#list primaryKey as column><#if (column_index > 0)>, </#if>@RequestParam("${column.columnFieldNameFirstLower}") ${column.columnFieldType} ${column.columnFieldNameFirstLower}</#list>) {

        Result<Integer> result;
        try {
            int count = ${classNameLower}Service.deleteByPk(${primaryKeyParameterValues});
            result = new Result<>(count);
        } catch (Exception e) {
            logger.error("delete", e);
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
     * @return
     <#include "/include/author_info1.ftl">
     */
    @RequestMapping(value = "/disable", method = RequestMethod.GET)
    public Result<Integer> disable(${primaryKeyParameters}) {

        Result<Integer> result;
        try {
            int count = ${classNameLower}Service.disableByPk(${primaryKeyParameterValues});
            result = new Result<>(count);
        } catch (Exception e) {
            logger.error("disable", e);
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
     * @return
     <#include "/include/author_info1.ftl">
     */
    @RequestMapping(value = "/enable", method = RequestMethod.GET)
    public Result<Integer> enable(${primaryKeyParameters}) {

        Result<Integer> result;
        try {
            int count = ${classNameLower}Service.enableByPk(${primaryKeyParameterValues});
            result = new Result<>(count);
        } catch (Exception e) {
            logger.error("enable", e);
            result = new Result<>(OperationConstants.SYSTEM_ERROR);
        }

        return result;
    }
    </#if>

    /**
     * 保存
     *
     * @param ${classNameLower}
     * @return
     <#include "/include/author_info1.ftl">
     */
    @RequestMapping(value = { "/save", "/save/" }, method = RequestMethod.POST)
    public Result<Integer> save(${className}Entity ${classNameLower}<#if !table.hasAutoIncrementUniquePrimaryKey><#list primaryKey as column>, @RequestParam(value = "old${column.columnFieldName}", required = false) ${column.columnFieldType} old${column.columnFieldName}</#list></#if>) {

        String fieldName = "";
        if (${classNameLower} == null || !StringUtil.isNullOrEmpty(fieldName = OperationCheckUtil.checkUpdate(${classNameLower}))) {
            return new Result<>(OperationConstants.NOT_NULL.getCode(), fieldName + OperationConstants.NOT_NULL.getMessage());
        }

        Result<Integer> result;
        try {
            int count;
            if (<#list primaryKey as column><#if (column_index > 0)> || </#if>${classNameLower}.get${column.columnFieldName}() == null</#list>) {
                count = ${classNameLower}Service.add(${classNameLower});
            } else {
                count = ${classNameLower}Service.updateByPk(${classNameLower}<#if !table.hasAutoIncrementUniquePrimaryKey>, ${table.primaryKeyOldParameterValues}</#if>);
            }

            result = new Result<>(count);
        } catch (Exception e) {
            logger.error("save", e);
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
     * @return
     <#include "/include/author_info1.ftl">
     */
    @RequestMapping(value = { "/getdetail", "/getdetail/" }, method = RequestMethod.GET)
    public Result<${className}Vo> getDetail(<#list primaryKey as column><#if (column_index > 0)>, </#if>@RequestParam("${column.columnFieldNameFirstLower}") ${column.columnFieldType} ${column.columnFieldNameFirstLower}</#list>) {

        Result<${className}Vo> result;
        try {
            ${className}Vo vo = ${classNameLower}Service.getByPk(${primaryKeyParameterValues});
            result = new Result<>(vo);
        } catch (Exception e) {
            logger.error("getDetail", e);
            result = new Result<>(OperationConstants.SYSTEM_ERROR);
        }

        return result;
    }
    </#if>

    /**
     * 分页查询
     *
     * @param parameter
     * @return
     <#include "/include/author_info1.ftl">
     */
    @RequestMapping(value = { "/find", "/find/" }, method = RequestMethod.GET)
    public Result<PageInfo<${className}Vo>> find(${className}SelectParameter parameter) {

        Result<PageInfo<${className}Vo>> result;
        try {
            PageInfo<${className}Vo> paginationVo = ${classNameLower}Service.findPageList(parameter);
            result = new Result<>(paginationVo);
        } catch (Exception e) {
            logger.error("find", e);
            result = new Result<>(OperationConstants.SYSTEM_ERROR);
        }

        return result;
    }

}
