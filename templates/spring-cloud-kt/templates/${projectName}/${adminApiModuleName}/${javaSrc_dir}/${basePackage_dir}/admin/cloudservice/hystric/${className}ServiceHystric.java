<#include "/include/table/properties.ftl">
package ${basePackage}.admin.cloudservice.hystric;

import ${baseCommonPackage}.constant.OperationConstants;
import ${baseCommonPackage}.model.OrderSearch;
import ${baseCommonPackage}.model.OrderSearchPage;
import ${baseCommonPackage}.model.PageInfoVo;
import ${baseCommonPackage}.model.ResultVo;
import ${basePackage}.models.condition.${className}Condition;
import ${basePackage}.models.condition.order.${className}OrderCondition;
import ${basePackage}.models.entity.${className}Entity;
import ${basePackage}.models.condition.extension.${className}ConditionExtension;
import ${basePackage}.models.entity.extension.${className}EntityExtension;
import ${basePackage}.admin.cloudservice.${className}CloudService;

import org.springframework.stereotype.Service;

import java.util.List;

<#include "/include/java_copyright.ftl">
@Service
public class ${className}ServiceHystric implements ${className}CloudService {

    /**
     * 添加${tableComment}
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
     * 添加${tableComment}列表
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

    /**
     * 根据条件删除${tableComment}
     *
     * @param condition
     * @param sessionId
     * @return
     <#include "/include/author_info1.ftl">
     */
    @Override
    public ResultVo<Integer> delete(${className}Condition condition, String sessionId) {
        return new ResultVo<>(OperationConstants.SERVICE_NOT_AVAILABLE);
    }
    <#if table.validStatusColumn??>

    /**
     * 根据条件禁用${tableComment}
     *
     * @param condition
     * @param sessionId
     * @return
     <#include "/include/author_info1.ftl">
     */
    @Override
    public ResultVo<Integer> disable(${className}Condition condition, String sessionId) {
        return new ResultVo<>(OperationConstants.SERVICE_NOT_AVAILABLE);
    }

    /**
     * 根据条件启用${tableComment}
     *
     * @param condition
     * @param sessionId
     * @return
     <#include "/include/author_info1.ftl">
     */
    @Override
    public ResultVo<Integer> enable(${className}Condition condition, String sessionId) {
        return new ResultVo<>(OperationConstants.SERVICE_NOT_AVAILABLE);
    }
    </#if>

    /**
     * 根据条件获取${tableComment}实体
     *
     * @param condition
     * @param sessionId
     * @return
     <#include "/include/author_info1.ftl">
     */
    @Override
    public ResultVo<${className}Entity> get(${className}Condition condition, String sessionId) {
        return new ResultVo<>(OperationConstants.SERVICE_NOT_AVAILABLE);
    }
    <#if (table.hasPk)>

    /**
     * 根据主键更新${tableComment}
     *
     * @param entity
     <#if !table.hasAutoIncUniPk>
     <#list pks as column>
     <#include "/include/column/properties.ftl">
     * @param ${fieldName}
     </#list>
     </#if>
     * @param sessionId
     * @return
     <#include "/include/author_info1.ftl">
     */
    @Override
    public ResultVo<Integer> update(${className}Entity entity, <#if !table.hasAutoIncUniPk><#include "/include/table/pk_params.ftl">, </#if>String sessionId) {
        return new ResultVo<>(OperationConstants.SERVICE_NOT_AVAILABLE);
    }

    /**
     * 根据主键获取${tableComment}详情
     *
     <#list pks as column>
     <#include "/include/column/properties.ftl">
     * @param ${fieldName}
     </#list>
     * @param sessionId
     * @return
     <#include "/include/author_info1.ftl">
     */
    @Override
    public ResultVo<${className}EntityExtension> getDetail(<#include "/include/table/pk_params.ftl">, String sessionId) {
        return new ResultVo<>(OperationConstants.SERVICE_NOT_AVAILABLE);
    }
    </#if>

    /**
     * 获取${tableComment}列表
     *
     * @param orderSearch
     * @param sessionId
     * @return
     <#include "/include/author_info1.ftl">
     */
    @Override
    public ResultVo<List<${className}EntityExtension>> getList(OrderSearch<${className}ConditionExtension, ${className}OrderCondition> orderSearch, String sessionId) {
        return new ResultVo<>(OperationConstants.SERVICE_NOT_AVAILABLE);
    }

    /**
     * 分页查询${tableComment}
     *
     * @param searchPage
     * @param sessionId
     * @return
     <#include "/include/author_info1.ftl">
     */
    @Override
    public ResultVo<PageInfoVo<${className}EntityExtension>> getPageInfo(OrderSearchPage<${className}ConditionExtension, ${className}OrderCondition> searchPage, String sessionId) {
        return new ResultVo<>(OperationConstants.SERVICE_NOT_AVAILABLE);
    }
}
