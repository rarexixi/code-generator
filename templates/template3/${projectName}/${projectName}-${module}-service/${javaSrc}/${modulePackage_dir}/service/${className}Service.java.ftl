<#include "/include/table/properties.ftl">
package ${modulePackage}.service;

import ${commonPackage}.models.PageList;
import ${modulePackage}.model.request.${className}AddRequest;
import ${modulePackage}.model.request.${className}QueryRequest;
import ${modulePackage}.model.request.${className}SaveRequest;
import ${modulePackage}.model.response.${className}DetailResponse;
import ${modulePackage}.model.response.${className}ListItemResponse;

import java.util.Collection;
import java.util.List;

/**
 * ${tableComment}业务逻辑
 *
 * @author ${author}
 */
public interface ${className}Service {

    /**
     * 添加${tableComment}
     *
     * @param addRequest ${tableComment}
     * @return 受影响的行数
     * @author ${author}
     */
    ${className}DetailResponse add(${className}AddRequest addRequest);

    /**
     * 批量添加${tableComment}
     *
     * @param list ${tableComment}列表
     * @return 受影响的行数
     * @author ${author}
     */
    int batchAdd(Collection<${className}AddRequest> list);

    <#-- region 删除/启用/禁用 -->
    /**
     * 根据<#include "/include/table/pk_fun_comment.ftl">删除${tableComment}
     *
     <#list pks as column>
     <#include "/include/column/properties.ftl">
     * @param ${fieldName} ${columnFullComment}
     </#list>
     * @return 受影响的行数
     * @author ${author}
     */
    int deleteBy<#include "/include/table/pk_fun_names.ftl">(<#include "/include/table/pk_params.ftl">);
    <#if (table.hasUniPk)>

    /**
     * 根据${uniPkComment}列表删除${tableComment}
     *
     * @param ${uniPkFieldName}List ${uniPkComment}列表
     * @return 受影响的行数
     * @author ${author}
     */
    int deleteBy${uniPkPropertyName}List(Collection<${uniPk.targetDataType}> ${uniPkFieldName}List);
    </#if>
    <#-- endregion 删除/启用/禁用 -->

    <#-- region 更新 -->
    <#if (table.hasAutoIncUniPk)>

    /**
     * 根据<#include "/include/table/pk_fun_comment.ftl">更新${tableComment}
     *
     * @param saveRequest 保存${tableComment}请求实体
     * @return 更新后的${tableComment}详情
     * @author ${author}
     */
    ${className}DetailResponse updateBy<#include "/include/table/pk_fun_names.ftl">(${className}SaveRequest saveRequest);
    <#else>

    /**
     * 根据<#include "/include/table/pk_fun_comment.ftl">更新${tableComment}
     *
     * @param saveRequest 保存${tableComment}请求实体
     <#list pks as column>
     <#include "/include/column/properties.ftl">
     * @param ${fieldName} ${columnFullComment}
     </#list>
     * @return 更新后的${tableComment}详情
     * @author ${author}
     */
    ${className}DetailResponse updateBy<#include "/include/table/pk_fun_names.ftl">(${className}SaveRequest saveRequest, <#include "/include/table/pk_params.ftl">);
    </#if>
    <#-- endregion 更新 -->

    <#-- region 详情 -->

    /**
     * 根据<#include "/include/table/pk_fun_comment.ftl">获取${tableComment}详情
     *
     <#list pks as column>
     <#include "/include/column/properties.ftl">
     * @param ${fieldName} ${columnFullComment}
     </#list>
     * @return ${tableComment}详情
     * @author ${author}
     */
    ${className}DetailResponse getBy<#include "/include/table/pk_fun_names.ftl">(<#include "/include/table/pk_params.ftl">);
    <#-- endregion 详情 -->

    /**
     * 获取${tableComment}列表
     *
     * @param queryRequest 搜索条件
     * @return 符合条件的${tableComment}列表
     */
    List<${className}ListItemResponse> getList(${className}QueryRequest queryRequest);

    /**
     * 分页获取${tableComment}列表
     *
     * @param queryRequest 搜索条件
     * @param pageNum      页码
     * @param pageSize     分页大小
     * @return 符合条件的${tableComment}分页列表
     */
    PageList<${className}ListItemResponse> getPageList(${className}QueryRequest queryRequest, Integer pageNum, Integer pageSize);
}
