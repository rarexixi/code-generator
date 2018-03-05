<#assign className = table.tableClassName>
<#assign classNameLower = table.tableClassName?uncap_first>
<#assign primaryKey = table.primaryKey>
<#assign primaryKeyParameters = table.primaryKeyParameters>
<#assign primaryKeyParameterValues = table.primaryKeyParameterValues>
package ${basePackage}.api.service.impl;

import ${baseCommonPackage}.constant.OperationConstants;
import ${baseCommonPackage}.model.Result;
import ${baseCommonPackage}.model.SearchPage;
import ${baseCommonPackage}.utils.LogUtil;
import ${baseCommonPackage}.utils.StringUtil;
import ${baseCommonPackage}.utils.database.OperationCheckUtil;
import ${basePackage}.api.service.${className}Api;
import ${basePackage}.entity.${className}Entity;
import ${basePackage}.parameter.${className}SelectParameter;
import ${basePackage}.service.${className}Service;
import ${basePackage}.vo.${className}Vo;

import com.github.pagehelper.PageInfo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

<#include "/include/java_copyright.ftl">
@Service("${classNameLower}Api")
public class ${className}ApiImpl implements ${className}Api {

    private static LogUtil logger = LogUtil.build(${className}ApiImpl.class);

    @Autowired
    private ${className}Service ${classNameLower}Service;

    /**
     * 添加
     *
     * @param entity
     * @param sessionId
     * @return
     <#include "/include/author_info1.ftl">
     */
    public Result<Integer> add(${className}Entity entity, String sessionId) {

        String fieldName = "";
        if (entity == null || !StringUtil.isNullOrEmpty(fieldName = OperationCheckUtil.checkInsert(entity))) {
            return new Result<>(OperationConstants.NOT_NULL.getCode(), fieldName + OperationConstants.NOT_NULL.getMessage());
        }

        Result<Integer> result;
        try {
            int count = ${classNameLower}Service.add(entity);
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
     * @param list
     * @param sessionId
     * @return
    <#include "/include/author_info1.ftl">
     */
    public Result<Integer> addList(List<${className}Entity> list, String sessionId) {

        if (list == null || list.isEmpty()) {
            return new Result<>(OperationConstants.NOT_NULL);
        }
        String fieldName = "";
        for (${className}Entity entity : list) {
            if (!StringUtil.isNullOrEmpty(fieldName = OperationCheckUtil.checkInsert(entity))) {
                return new Result<>(OperationConstants.NOT_NULL.getCode(), fieldName + OperationConstants.NOT_NULL.getMessage());
            }
        }

        Result<Integer> result;
        try {
            int count = ${classNameLower}Service.addList(list);
            result = new Result<>(count);
        } catch (Exception e) {
            logger.error("addList", sessionId, e);
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
    public Result<Integer> deleteByPk(${primaryKeyParameters}, String sessionId) {

        Result<Integer> result;
        try {
            int count = ${classNameLower}Service.deleteByPk(${primaryKeyParameterValues});
            result = new Result<>(count);
        } catch (Exception e) {
            logger.error("deleteByPk", sessionId, e);
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
    public Result<Integer> disableByPk(${primaryKeyParameters}, String sessionId) {

        Result<Integer> result;
        try {
            int count = ${classNameLower}Service.disableByPk(${primaryKeyParameterValues});
            result = new Result<>(count);
        } catch (Exception e) {
            logger.error("disableByPk", sessionId, e);
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
    public Result<Integer> enableByPk(${primaryKeyParameters}, String sessionId) {

        Result<Integer> result;
        try {
            int count = ${classNameLower}Service.enableByPk(${primaryKeyParameterValues});
            result = new Result<>(count);
        } catch (Exception e) {
            logger.error("enableByPk", sessionId, e);
            result = new Result<>(OperationConstants.SYSTEM_ERROR);
        }

        return result;
    }
    </#if>

    /**
     * 根据主键更新
     *
     * @param entity
     * @param sessionId
     * @return
     <#include "/include/author_info1.ftl">
     */
    public Result<Integer> updateByPk(${className}Entity entity<#if !table.hasAutoIncrementUniquePrimaryKey><#list primaryKey as column>, ${column.columnFieldType} old${column.columnFieldName}</#list></#if>, String sessionId) {

        String fieldName = "";
        if (entity == null || !StringUtil.isNullOrEmpty(fieldName = OperationCheckUtil.checkUpdate(entity))) {
            return new Result<>(OperationConstants.NOT_NULL.getCode(), fieldName + OperationConstants.NOT_NULL.getMessage());
        }

        Result<Integer> result;
        try {
            int count = ${classNameLower}Service.updateByPk(entity<#if !table.hasAutoIncrementUniquePrimaryKey>, ${table.primaryKeyOldParameterValues}</#if>);
            result = new Result<>(count);
        } catch (Exception e) {
            logger.error("updateByPk", sessionId, e);
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
    public Result<${className}Vo> getByPk(${primaryKeyParameters}, String sessionId) {

        Result<${className}Vo> result;
        try {
            ${className}Vo vo = ${classNameLower}Service.getByPk(${primaryKeyParameterValues});
            result = new Result<>(vo);
        } catch (Exception e) {
            logger.error("getByPk", sessionId, e);
            result = new Result<>(OperationConstants.SYSTEM_ERROR);
        }

        return result;
    }
    </#if>

    /**
     * 分页查询
     *
     * @param parameter
     * @param sessionId
     * @return
     <#include "/include/author_info1.ftl">
     */
    public Result<PageInfo<${className}Vo>> findPageList(${className}SelectParameter parameter, String sessionId) {

        Result<PageInfo<${className}Vo>> result;
        try {
            PageInfo<${className}Vo> paginationVo = ${classNameLower}Service.findPageList(parameter);
            result = new Result<>(paginationVo);
        } catch (Exception e) {
            logger.error("findPageList", sessionId, e);
            result = new Result<>(OperationConstants.SYSTEM_ERROR);
        }

        return result;
    }

}
