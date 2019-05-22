<#include "/include/table/properties.ftl">
package ${basePackage}.service.impl;

import ${baseCommonPackage}.model.*;
import ${baseCommonPackage}.utils.VoUtils;
import ${basePackage}.mapper.${className}Mapper;
import ${basePackage}.models.condition.${className}Condition;
import ${basePackage}.models.condition.order.${className}OrderCondition;
import ${basePackage}.models.entity.${className}Entity;
import ${basePackage}.models.condition.extension.${className}ConditionExtension;
import ${basePackage}.models.entity.extension.${className}EntityExtension;
import ${basePackage}.service.${className}Service;
import ${basePackage}.vm.addoredit.${className}AddOrEditVm;
import ${basePackage}.vm.detail.${className}DetailVm;
import ${basePackage}.vm.order.${className}OrderVm;
import ${basePackage}.vm.search.${className}SearchVm;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

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

    @Autowired
    public ${className}ServiceImpl(${className}Mapper ${classNameFirstLower}Mapper) {
        this.${classNameFirstLower}Mapper = ${classNameFirstLower}Mapper;
    }

    private ${className}Mapper ${classNameFirstLower}Mapper;

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
        ${classNameFirstLower}Mapper.insert(entity);
        vm.set${className}Entity(entity);

        return new ResponseVo(vm);
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
        int count = ${classNameFirstLower}Mapper.insertList(entityList);

        return new ResponseVo(count);
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
        int count = ${classNameFirstLower}Mapper.deleteByCondition(condition);

        return new ResponseVo(count);
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
        ${className}Entity entity = new ${className}Entity();
        entity.set${table.validStatusColumn.targetName}(${table.validStatusColumn.validStatusOption.invalid});
        int count = ${classNameFirstLower}Mapper.updateByCondition(entity, condition);

        return new ResponseVo(count);
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
        ${className}Entity entity = new ${className}Entity();
        entity.set${table.validStatusColumn.targetName}(${table.validStatusColumn.validStatusOption.valid});
        int count = ${classNameFirstLower}Mapper.updateByCondition(entity, condition);

        return new ResponseVo(count);
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
        ${className}Entity entity = ${classNameFirstLower}Mapper.getByCondition(condition);
        ${className}DetailVm detailVm = new ${className}DetailVm(entity);

        return new ResponseVo(detailVm);
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

        ${className}Condition condition = new ${className}Condition();
        <#list pks as column>
        <#include "/include/column/properties.ftl">
        condition.set${propertyName}(<#if table.hasAutoIncUniPk>vm.get${propertyName}()<#else>${fieldName}</#if>);
        </#list>
        Integer count = ${classNameFirstLower}Mapper.updateByCondition(vm.get${className}Entity(), condition);

        return new ResponseVo(count);
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

        ${className}EntityExtension entity = ${classNameFirstLower}Mapper.getByPk(<#include "/include/table/pk_values.ftl">);

        ${className}DetailVm vm = new ${className}DetailVm(entity);
        return new ResponseVo(vm);
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

        if (search == null) return null;
        OrderSearch<${className}ConditionExtension, ${className}OrderCondition> orderSearch = VoUtils.getOrderSearch(search);
        List<${className}EntityExtension> list = ${classNameFirstLower}Mapper.getExList(orderSearch.getCondition(), orderSearch.getOrder());
        List<${className}DetailVm> vmList = list.stream().map(${className}DetailVm::new).collect(Collectors.toList());
        return new ResponseVo(vmList);
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

        PageHelper.startPage(searchPage.getPageIndex(), searchPage.getPageSize());
        List<${className}EntityExtension> list = ${classNameFirstLower}Mapper.getExList(orderSearch.getCondition(), orderSearch.getOrder());
        PageInfo<${className}EntityExtension> pageInfo = new PageInfo<>(list);

        PageInfoVo<${className}DetailVm> pageInfoVo = VoUtils.getPageInfoVo(pageInfo, ${className}DetailVm::new);
        return new ResponseVo(pageInfoVo);
    }
}
