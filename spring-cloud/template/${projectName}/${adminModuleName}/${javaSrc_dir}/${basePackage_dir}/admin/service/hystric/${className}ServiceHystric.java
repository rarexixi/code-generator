<#assign className = table.tableClassName>
<#assign classNameLower = table.tableClassName?uncap_first>
<#assign primaryKey = table.primaryKey>
<#assign primaryKeyParameters = table.primaryKeyParameters>
<#assign primaryKeyParameterValues = table.primaryKeyParameterValues>
package ${basePackage}.admin.service.hystric;

import org.xi.common.constant.OperationConstants;
import org.xi.common.model.Result;
import ${basePackage}.entity.${className}Entity;
import ${basePackage}.parameter.${className}SelectParameter;
import ${basePackage}.vo.${className}Vo;
import ${basePackage}.admin.service.${className}Service;

import com.github.pagehelper.PageInfo;

import org.springframework.stereotype.Service;

import java.util.List;

<#include "/include/java_copyright.ftl">
@Service
public class ${className}ServiceHystric implements ${className}Service {

    /**
     * 添加
     *
     * @param entity
     * @param sessionId
     * @return
     <#include "/include/author_info1.ftl">
     */
    @Override
    public Result<Integer> add(${className}Entity entity, String sessionId) {
        return new Result<>(OperationConstants.SERVICE_NOT_AVAILABLE);
    }

    /**
     * 添加列表
     *
     * @param list
     * @param sessionId
     * @return
    <#include "/include/author_info1.ftl">
     */
    @Override
    public Result<Integer> addList(List<${className}Entity> list, String sessionId) {
        return new Result<>(OperationConstants.SERVICE_NOT_AVAILABLE);
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
    public Result<Integer> deleteByPk(<#list primaryKey as column><#if (column_index > 0)>, </#if>${column.columnFieldType} ${column.columnFieldNameFirstLower}</#list>, String sessionId) {
        return new Result<>(OperationConstants.SERVICE_NOT_AVAILABLE);
    }
    <#if (table.uniquePrimaryKey??)>

    /**
     * 根据主键列表物理删除
     *
     * @param ${table.uniquePrimaryKey.columnFieldName?uncap_first}List
     * @param sessionId
     * @return
     <#include "/include/author_info1.ftl">
     */
    @Override
    public Result<Integer> deleteByPkList(List<${table.uniquePrimaryKey.columnFieldType}> ${table.uniquePrimaryKey.columnFieldName?uncap_first}List, String sessionId) {
        return new Result<>(OperationConstants.SERVICE_NOT_AVAILABLE);
    }
    </#if>
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
    public Result<Integer> disableByPk(<#list primaryKey as column><#if (column_index > 0)>, </#if>${column.columnFieldType} ${column.columnFieldNameFirstLower}</#list>, String sessionId) {
        return new Result<>(OperationConstants.SERVICE_NOT_AVAILABLE);
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
    public Result<Integer> enableByPk(<#list primaryKey as column><#if (column_index > 0)>, </#if>${column.columnFieldType} ${column.columnFieldNameFirstLower}</#list>, String sessionId) {
        return new Result<>(OperationConstants.SERVICE_NOT_AVAILABLE);
    }
    <#if (table.uniquePrimaryKey??)>

    /**
     * 根据主键列表冻结
     *
     * @param ${table.uniquePrimaryKey.columnFieldName?uncap_first}List
     * @param sessionId
     * @return
     <#include "/include/author_info1.ftl">
     */
    @Override
    public Result<Integer> disableByPkList(List<${table.uniquePrimaryKey.columnFieldType}> ${table.uniquePrimaryKey.columnFieldName?uncap_first}List, String sessionId) {
        return new Result<>(OperationConstants.SERVICE_NOT_AVAILABLE);
    }

    /**
     * 根据主键列表激活
     *
     * @param ${table.uniquePrimaryKey.columnFieldName?uncap_first}List
     * @param sessionId
     * @return
     <#include "/include/author_info1.ftl">
     */
    @Override
    public Result<Integer> enableByPkList(List<${table.uniquePrimaryKey.columnFieldType}> ${table.uniquePrimaryKey.columnFieldName?uncap_first}List, String sessionId) {
        return new Result<>(OperationConstants.SERVICE_NOT_AVAILABLE);
    }
    </#if>
    </#if>

    /**
     * 根据主键更新
     *
     * @param entity
     * @param sessionId
     * @return
     <#include "/include/author_info1.ftl">
     */
    @Override
    public Result<Integer> updateByPk(${className}Entity entity<#if !table.hasAutoIncrementUniquePrimaryKey><#list primaryKey as column>, ${column.columnFieldType} old${column.columnFieldName}</#list></#if>, String sessionId) {
        return new Result<>(OperationConstants.SERVICE_NOT_AVAILABLE);
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
    public Result<${className}Vo> getByPk(<#list primaryKey as column><#if (column_index > 0)>, </#if>${column.columnFieldType} ${column.columnFieldNameFirstLower}</#list>, String sessionId) {
        return new Result<>(OperationConstants.SERVICE_NOT_AVAILABLE);
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
    @Override
    public Result<PageInfo<${className}Vo>> findPageList(${className}SelectParameter parameter, String sessionId) {
        return new Result<>(OperationConstants.SERVICE_NOT_AVAILABLE);
    }

}
