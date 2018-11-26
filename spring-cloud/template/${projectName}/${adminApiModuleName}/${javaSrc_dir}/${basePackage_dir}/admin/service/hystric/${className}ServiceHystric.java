<#include "/include/table/properties.ftl">
package ${basePackage}.admin.service.hystric;

import ${baseCommonPackage}.constant.OperationConstants;
import ${baseCommonPackage}.model.ResultVo;
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
    public ResultVo<${className}Entity> add(${className}Entity entity, String sessionId) {
        return new ResultVo<>(OperationConstants.SERVICE_NOT_AVAILABLE);
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
    public ResultVo<Integer> addList(List<${className}Entity> list, String sessionId) {
        return new ResultVo<>(OperationConstants.SERVICE_NOT_AVAILABLE);
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
    public ResultVo<Integer> delete(<#list primaryKey as column><#if (column_index > 0)>, </#if>${column.columnFieldType} ${column.columnFieldNameFirstLower}</#list>, String sessionId) {
        return new ResultVo<>(OperationConstants.SERVICE_NOT_AVAILABLE);
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
    public ResultVo<Integer> delete(List<${table.uniquePrimaryKey.columnFieldType}> ${table.uniquePrimaryKey.columnFieldName?uncap_first}List, String sessionId) {
        return new ResultVo<>(OperationConstants.SERVICE_NOT_AVAILABLE);
    }
    </#if>
    <#if table.validStatusColumn??>

    /**
     * 根据主键禁用
     *
     <#list primaryKey as column>
     * @param ${column.columnFieldNameFirstLower}
     </#list>
     * @param sessionId
     * @return
     <#include "/include/author_info1.ftl">
     */
    @Override
    public ResultVo<Integer> disable(<#list primaryKey as column><#if (column_index > 0)>, </#if>${column.columnFieldType} ${column.columnFieldNameFirstLower}</#list>, String sessionId) {
        return new ResultVo<>(OperationConstants.SERVICE_NOT_AVAILABLE);
    }

    /**
     * 根据主键启用
     *
     <#list primaryKey as column>
     * @param ${column.columnFieldNameFirstLower}
     </#list>
     * @param sessionId
     * @return
     <#include "/include/author_info1.ftl">
     */
    @Override
    public ResultVo<Integer> enable(<#list primaryKey as column><#if (column_index > 0)>, </#if>${column.columnFieldType} ${column.columnFieldNameFirstLower}</#list>, String sessionId) {
        return new ResultVo<>(OperationConstants.SERVICE_NOT_AVAILABLE);
    }
    <#if (table.uniquePrimaryKey??)>

    /**
     * 根据主键列表禁用
     *
     * @param ${table.uniquePrimaryKey.columnFieldName?uncap_first}List
     * @param sessionId
     * @return
     <#include "/include/author_info1.ftl">
     */
    @Override
    public ResultVo<Integer> disable(List<${table.uniquePrimaryKey.columnFieldType}> ${table.uniquePrimaryKey.columnFieldName?uncap_first}List, String sessionId) {
        return new ResultVo<>(OperationConstants.SERVICE_NOT_AVAILABLE);
    }

    /**
     * 根据主键列表启用
     *
     * @param ${table.uniquePrimaryKey.columnFieldName?uncap_first}List
     * @param sessionId
     * @return
     <#include "/include/author_info1.ftl">
     */
    @Override
    public ResultVo<Integer> enable(List<${table.uniquePrimaryKey.columnFieldType}> ${table.uniquePrimaryKey.columnFieldName?uncap_first}List, String sessionId) {
        return new ResultVo<>(OperationConstants.SERVICE_NOT_AVAILABLE);
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
    public ResultVo<Integer> update(${className}Entity entity<#if !table.hasAutoIncrementUniquePrimaryKey><#list primaryKey as column>, ${column.columnFieldType} old${column.columnFieldName}</#list></#if>, String sessionId) {
        return new ResultVo<>(OperationConstants.SERVICE_NOT_AVAILABLE);
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
    public ResultVo<${className}Vo> get(<#list primaryKey as column><#if (column_index > 0)>, </#if>${column.columnFieldType} ${column.columnFieldNameFirstLower}</#list>, String sessionId) {
        return new ResultVo<>(OperationConstants.SERVICE_NOT_AVAILABLE);
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
    public ResultVo<PageInfo<${className}Vo>> getPageInfo(${className}SelectParameter parameter, String sessionId) {
        return new ResultVo<>(OperationConstants.SERVICE_NOT_AVAILABLE);
    }

}
