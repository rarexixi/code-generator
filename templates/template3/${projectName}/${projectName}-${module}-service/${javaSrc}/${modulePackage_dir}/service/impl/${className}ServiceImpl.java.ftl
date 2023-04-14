<#include "/include/table/properties.ftl">
package ${modulePackage}.service.impl;

import ${commonPackage}.exception.DmpDataNotFoundException;
import ${commonPackage}.models.PageList;
import ${commonPackage}.utils.ObjectUtils;
import ${modulePackage}.constant.DeletedConstant;
import ${modulePackage}.persistence.condition.${className}SelectCondition;
import ${modulePackage}.persistence.condition.${className}UpdateCondition;
import ${modulePackage}.persistence.entity.${className}Entity;
import ${modulePackage}.persistence.entity.${className}EntityExt;
import ${modulePackage}.persistence.mapper.${className}Mapper;
import ${modulePackage}.model.request.${className}AddRequest;
import ${modulePackage}.model.request.${className}PatchRequest;
import ${modulePackage}.model.request.${className}QueryRequest;
import ${modulePackage}.model.request.${className}SaveRequest;
import ${modulePackage}.model.response.${className}DetailResponse;
import ${modulePackage}.model.response.${className}ListItemResponse;
import ${modulePackage}.service.${className}Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;

/**
 * ${tableComment}业务逻辑
 *
 * @author ${author}
 */
@Service("${classNameFirstLower}Service")
@Transactional
public class ${className}ServiceImpl implements ${className}Service {

    final ${className}Mapper ${classNameFirstLower}Mapper;

    @Autowired
    public ${className}ServiceImpl(${className}Mapper ${classNameFirstLower}Mapper) {
        this.${classNameFirstLower}Mapper = ${classNameFirstLower}Mapper;
    }

    /**
     * 添加${tableComment}
     *
     * @param addRequest ${tableComment}
     * @return 受影响的行数
     * @author ${author}
     */
    @Override
    public ${className}DetailResponse add(${className}AddRequest addRequest) {
        ${className}Entity entity = ObjectUtils.copy(addRequest, ${className}Entity.class);
        ${classNameFirstLower}Mapper.insert(entity);
        return getBy<#include "/include/table/pk_fun_names.ftl">(<#list pks as column><#include "/include/column/properties.ftl">entity.get${propertyName}()<#if (column?has_next)>, </#if></#list>);
    }

    /**
     * 批量添加${tableComment}
     *
     * @param list ${tableComment}列表
     * @return 受影响的行数
     * @author ${author}
     */
    @Override
    public int batchAdd(Collection<${className}AddRequest> list) {
        List<${className}Entity> entityList = ObjectUtils.copy(list, ${className}Entity.class);
        return ${classNameFirstLower}Mapper.batchInsert(entityList);
    }

    <#-- region 删除/启用/禁用 -->
    /**
     * 删除${tableComment}
     *
     * @param patchRequest 更新条件请求
     * @return 受影响的行数
     * @author ${author}
     */
    @Override
    public int delete(${className}PatchRequest patchRequest) {
        ${className}UpdateCondition condition = ObjectUtils.copy(patchRequest, ${className}UpdateCondition.class);
        return ${classNameFirstLower}Mapper.delete(condition);
    }
    <#if table.validStatusColumn??>

    /**
     * 禁用${tableComment}
     *
     * @param patchRequest 更新条件请求
     * @return 受影响的行数
     * @author ${author}
     */
    @Override
    public int disable(${className}PatchRequest patchRequest) {
        ${className}UpdateCondition condition = ObjectUtils.copy(patchRequest, ${className}UpdateCondition.class);
        ${className}Entity entity = ObjectUtils.copy(patchRequest, ${className}Entity.class<#list pks as column><#include "/include/column/properties.ftl">, "${fieldName}"</#list>);
        entity.setDeleted(DeletedConstant.INVALID);
        return ${classNameFirstLower}Mapper.update(entity, condition);
    }

    /**
     * 启用${tableComment}
     *
     * @param patchRequest 更新条件请求
     * @return 受影响的行数
     * @author ${author}
     */
    @Override
    public int enable(${className}PatchRequest patchRequest) {
        ${className}UpdateCondition condition = ObjectUtils.copy(patchRequest, ${className}UpdateCondition.class);
        ${className}Entity entity = ObjectUtils.copy(patchRequest, ${className}Entity.class<#list pks as column><#include "/include/column/properties.ftl">, "${fieldName}"</#list>);
        entity.setDeleted(DeletedConstant.VALID);
        return ${classNameFirstLower}Mapper.update(entity, condition);
    }
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
    @Override
    public ${className}DetailResponse updateBy<#include "/include/table/pk_fun_names.ftl">(${className}SaveRequest saveRequest) {
        ${className}UpdateCondition condition = new ${className}UpdateCondition();
        <#list pks as column>
        <#include "/include/column/properties.ftl">
        condition.set${propertyName}(saveRequest.get${propertyName}());
        </#list>
        ${className}Entity entity = ObjectUtils.copy(saveRequest, ${className}Entity.class);
        ${classNameFirstLower}Mapper.update(entity, condition);
        return getBy<#include "/include/table/pk_fun_names.ftl">(<#list pks as column><#include "/include/column/properties.ftl">saveRequest.get${propertyName}()<#if (column?has_next)>, </#if></#list>);
    }
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
    @Override
    public ${className}DetailResponse updateBy<#include "/include/table/pk_fun_names.ftl">(${className}SaveRequest saveRequest, <#include "/include/table/pk_params.ftl">) {
        ${className}UpdateCondition condition = new ${className}UpdateCondition();
        <#list pks as column>
        <#include "/include/column/properties.ftl">
        condition.set${propertyName}(${fieldName});
        </#list>
        ${className}Entity entity = ObjectUtils.copy(saveRequest, ${className}Entity.class);
        ${classNameFirstLower}Mapper.update(entity, condition);
        ${className}DetailResponse result;
        if (<#list pks as column><#include "/include/column/properties.ftl">saveRequest.get${propertyName}() == null<#if (column?has_next)> || </#if></#list>) {
            result = getBy<#include "/include/table/pk_fun_names.ftl">(<#include "/include/table/pk_values.ftl">);
        } else {
            result = getBy<#include "/include/table/pk_fun_names.ftl">(<#list pks as column><#include "/include/column/properties.ftl">saveRequest.get${propertyName}()<#if (column?has_next)>, </#if></#list>);
        }
        return result;
    }
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
    @Override
    @Transactional(readOnly = true)
    public ${className}DetailResponse getBy<#include "/include/table/pk_fun_names.ftl">(<#include "/include/table/pk_params.ftl">) {
        ${className}EntityExt entity = ${classNameFirstLower}Mapper.detail(<#include "/include/table/pk_values.ftl">);
        if (entity == null) {
            throw new DmpDataNotFoundException("${tableComment}不存在");
        }
        return ObjectUtils.copy(entity, ${className}DetailResponse.class);
    }
    <#-- endregion 详情 -->

    /**
     * 获取${tableComment}列表
     *
     * @param queryRequest 搜索条件
     * @return 符合条件的${tableComment}列表
     */
    @Override
    @Transactional(readOnly = true)
    public List<${className}ListItemResponse> getList(${className}QueryRequest queryRequest) {
        ${className}SelectCondition condition = ObjectUtils.copy(queryRequest, ${className}SelectCondition.class);
        List<${className}EntityExt> list = ${classNameFirstLower}Mapper.select(condition);
        return ObjectUtils.copy(list, ${className}ListItemResponse.class);
    }

    /**
     * 分页获取${tableComment}列表
     *
     * @param queryRequest 搜索条件
     * @param pageNum      页码
     * @param pageSize     分页大小
     * @return 符合条件的${tableComment}分页列表
     */
    @Override
    @Transactional(readOnly = true)
    public PageList<${className}ListItemResponse> getPageList(${className}QueryRequest queryRequest, Integer pageNum, Integer pageSize) {

        ${className}SelectCondition condition = ObjectUtils.copy(queryRequest, ${className}SelectCondition.class);
        PageInfo<${className}EntityExt> pageInfo = PageHelper.startPage(pageNum, pageSize).doSelectPageInfo(() -> ${classNameFirstLower}Mapper.select(condition));

        List<${className}ListItemResponse> list = ObjectUtils.copy(pageInfo.getList(), ${className}ListItemResponse.class);
        return new PageList<>(pageInfo.getPageNum(), pageInfo.getPageSize(), pageInfo.getTotal(), list);
    }
}
