<#include "/include/table/properties.ftl">
package ${basePackage}.admin.service.impl;

import ${baseCommonPackage}.model.*;
import ${basePackage}.admin.cloudservice.${className}CloudService;
import ${basePackage}.admin.service.${className}Service;
import ${basePackage}.admin.utils.VoUtils;
import ${basePackage}.admin.vm.addoredit.${className}AddOrEditVm;
import ${basePackage}.admin.vm.detail.${className}DetailVm;
import ${basePackage}.admin.vm.order.${className}OrderVm;
import ${basePackage}.admin.vm.search.${className}SearchVm;
import ${basePackage}.models.condition.${className}Condition;
import ${basePackage}.models.condition.order.${className}OrderCondition;
import ${basePackage}.models.entity.${className}Entity;
import ${basePackage}.models.condition.extension.${className}ConditionExtension;
import ${basePackage}.models.entity.extension.${className}EntityExtension;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

<#include "/include/java_copyright.ftl">
@Service("${classNameFirstLower}Service")
public class ${className}ServiceImpl implements ${className}Service {

    @Value("${r'${spring.application.name}'}")
    private String applicationName;

    private final ${className}CloudService ${classNameFirstLower}CloudService;

    @Autowired
    public ${className}ServiceImpl(${className}CloudService ${classNameFirstLower}CloudService) {
        this.${classNameFirstLower}CloudService = ${classNameFirstLower}CloudService;
    }

    /**
     * 添加${tableComment}
     *
     * @param vm
     * @return
     <#include "/include/author_info1.ftl">
     */
    @Override
    public ResponseVo<${className}AddOrEditVm> add(${className}AddOrEditVm vm) {

        ${className}Entity entity = vm.get${className}Entity();
        ResultVo<${className}Entity> apiResult = ${classNameFirstLower}CloudService.add(entity, getSessionId());

        return VoUtils.getResponseVo(apiResult, result -> {
            vm.set${className}Entity(result);
            return new ResponseVo<>(vm);
        });
    }

    /**
     * 添加${tableComment}列表
     *
     * @param list
     * @return
     <#include "/include/author_info1.ftl">
     */
    @Override
    public ResponseVo<Integer> addList(List<${className}AddOrEditVm> list) {

        List<${className}Entity> entityList = list.stream().map(o -> o.get${className}Entity()).collect(Collectors.toList());
        ResultVo<Integer> apiResult = ${classNameFirstLower}CloudService.addList(entityList, getSessionId());

        return VoUtils.getResponseVo(apiResult);
    }

    /**
     * 根据条件删除${tableComment}
     *
     * @param searchVm
     * @return
     <#include "/include/author_info1.ftl">
     */
    @Override
    public ResponseVo<Integer> delete(${className}SearchVm searchVm) {

        ${className}Condition condition = searchVm.getCondition();
        ResultVo<Integer> apiResult = ${classNameFirstLower}CloudService.delete(condition, getSessionId());

        return VoUtils.getResponseVo(apiResult);
    }
    <#if table.validStatusColumn??>

    /**
     * 根据条件禁用${tableComment}
     *
     * @param searchVm
     * @return
     <#include "/include/author_info1.ftl">
     */
    @Override
    public ResponseVo<Integer> disable(${className}SearchVm searchVm) {

        ${className}Condition condition = searchVm.getCondition();
        ResultVo<Integer> apiResult = ${classNameFirstLower}CloudService.disable(condition, getSessionId());

        return VoUtils.getResponseVo(apiResult);
    }

    /**
     * 根据条件启用${tableComment}
     *
     * @param searchVm
     * @return
     <#include "/include/author_info1.ftl">
     */
    @Override
    public ResponseVo<Integer> enable(${className}SearchVm searchVm) {

        ${className}Condition condition = searchVm.getCondition();
        ResultVo<Integer> apiResult = ${classNameFirstLower}CloudService.enable(condition, getSessionId());

        return VoUtils.getResponseVo(apiResult);
    }
    </#if>

    /**
     * 根据条件获取${tableComment}实体
     *
     * @param searchVm
     * @return
     <#include "/include/author_info1.ftl">
     */
    @Override
    public ResponseVo<${className}DetailVm> get(${className}SearchVm searchVm) {

        ${className}Condition condition = searchVm.getCondition();
        ResultVo<${className}Entity> apiResult = ${classNameFirstLower}CloudService.get(condition, getSessionId());

        return VoUtils.getResponseVo(apiResult, result -> {
            ResponseVo<${className}DetailVm> responseVo = new ResponseVo<>();
            responseVo.setSuccess(true);
            if (result != null) {
                ${className}DetailVm vm = new ${className}DetailVm(result);
                responseVo.setResult(vm);
            }
            return responseVo;
        });
    }
    <#if (table.hasPk)>

    /**
     * 根据主键更新${tableComment}
     *
     * @param vm
     <#if !table.hasAutoIncUniPk>
     <#list pks as column>
     <#include "/include/column/properties.ftl">
     * @param ${fieldName}
     </#list>
     </#if>
     * @return
     <#include "/include/author_info1.ftl">
     */
    @Override
    public ResponseVo<Integer> update(${className}AddOrEditVm vm<#if !table.hasAutoIncUniPk>, <#include "/include/table/pk_params.ftl"></#if>) {

        ResultVo<Integer> apiResult = ${classNameFirstLower}CloudService.update(vm.get${className}Entity()<#if !table.hasAutoIncUniPk>, <#include "/include/table/pk_values.ftl"></#if>, getSessionId());

        return VoUtils.getResponseVo(apiResult);
    }

    /**
     * 根据主键获取${tableComment}详情
     *
     <#list pks as column>
     <#include "/include/column/properties.ftl">
     * @param ${fieldName}
     </#list>
     * @return
     <#include "/include/author_info1.ftl">
     */
    @Override
    public ResponseVo<${className}DetailVm> getDetail(<#include "/include/table/pk_params.ftl">) {

        ResultVo<${className}EntityExtension> apiResult = ${classNameFirstLower}CloudService.getDetail(<#include "/include/table/pk_values.ftl">, getSessionId());

        return VoUtils.getResponseVo(apiResult, result -> {
            ResponseVo<${className}DetailVm> responseVo = new ResponseVo<>();
            responseVo.setSuccess(true);
            ${className}EntityExtension entity;
            if ((entity = apiResult.getResult()) != null) {
                ${className}DetailVm vm = new ${className}DetailVm(entity);
                responseVo.setResult(vm);
            }
            return responseVo;
        });
    }
    </#if>

    /**
     * 获取${tableComment}列表
     *
     * @param search
     * @return
     <#include "/include/author_info1.ftl">
     */
    @Override
    public ResponseVo<List<${className}DetailVm>> getList(OrderSearch<${className}SearchVm, ${className}OrderVm> search) {

        OrderSearch<${className}ConditionExtension, ${className}OrderCondition> orderSearch = VoUtils.getOrderSearch(search);
        ResultVo<List<${className}EntityExtension>> apiResult = ${classNameFirstLower}CloudService.getList(orderSearch, getSessionId());

        return VoUtils.getResponseVo(apiResult, result -> {
            List<${className}DetailVm> vmList = new ArrayList<>();
            if (result != null) {
                result.forEach(item -> vmList.add(new ${className}DetailVm(item)));
            }
            return new ResponseVo<>(vmList);
        });
    }

    /**
     * 分页查询${tableComment}
     *
     * @param searchPage
     * @return
     <#include "/include/author_info1.ftl">
     */
    @Override
    public ResponseVo<PageInfoVo<${className}DetailVm>> getPageInfo(OrderSearchPage<${className}SearchVm, ${className}OrderVm> searchPage) {

        OrderSearch<${className}ConditionExtension, ${className}OrderCondition> orderSearch = VoUtils.getOrderSearch(searchPage);
        OrderSearchPage<${className}ConditionExtension, ${className}OrderCondition> orderSearchPage =
                new OrderSearchPage<>(searchPage.getPageIndex(), searchPage.getPageSize(), orderSearch);
        ResultVo<PageInfoVo<${className}EntityExtension>> apiResult = ${classNameFirstLower}CloudService.getPageInfo(orderSearchPage, getSessionId());

        return VoUtils.getResponseVo(apiResult, result -> {
            List<${className}DetailVm> list = new ArrayList<>();
            if (result != null && result.getList() != null) {
                result.getList().forEach(item -> list.add(new ${className}DetailVm(item)));
            }
            PageInfoVo<${className}DetailVm> pageInfoVo =
                    new PageInfoVo<>(result.getPageIndex(), result.getPageSize(), result.getTotal(), list);
            return new ResponseVo<>(pageInfoVo);
        });
    }

    private String getSessionId() {
        return applicationName + "-" + UUID.randomUUID();
    }
}
