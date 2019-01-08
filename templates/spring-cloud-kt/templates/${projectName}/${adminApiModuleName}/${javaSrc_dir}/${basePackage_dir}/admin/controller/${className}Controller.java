<#include "/include/table/properties.ftl">
package ${basePackage}.admin.controller;

import ${baseCommonPackage}.model.ResponseVo;
import ${baseCommonPackage}.model.ResultVo;
import ${baseCommonPackage}.utils.LogUtils;
import ${baseCommonPackage}.validation.*;
import ${basePackage}.admin.service.${className}Service;
import ${basePackage}.admin.vm.addoredit.${className}AddOrEditVm;
import ${basePackage}.admin.vm.detail.${className}DetailVm;
import ${basePackage}.admin.vm.search.${className}SearchVm;
import ${basePackage}.entity.${className}Entity;
import ${basePackage}.condition.extension.${className}ConditionExtension;
import ${basePackage}.entity.extension.${className}EntityExtension;

import com.github.pagehelper.PageInfo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.*;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

<#include "/include/java_copyright.ftl">
@CrossOrigin
@RequestMapping("/${className?lower_case}")
@RestController
@Validated
public class ${className}Controller {

    @Autowired
    private ${className}Service ${classNameFirstLower}Service;

    /**
     * 添加
     *
     * @param vm
     * @return
     <#include "/include/author_info1.ftl">
     */
    @RequestMapping(value = { "/add" }, method = RequestMethod.POST)
    public ResponseVo<${className}AddOrEditVm> add(@Validated({DataAdd.class}) ${className}AddOrEditVm vm) {

        ResponseVo<${className}AddOrEditVm> result = ${classNameFirstLower}Service.add(vm);
        return result;
    }

    /**
     * 添加列表
     *
     * @param list
     * @return
    <#include "/include/author_info1.ftl">
     */
    @RequestMapping(value = { "/addList" }, method = RequestMethod.POST)
    public ResponseVo<Integer> addList(@RequestBody List<${className}AddOrEditVm> list) {

        ResponseVo<${className}AddOrEditVm> result = ${classNameFirstLower}Service.add(vm);
        return result;
    }

    /**
     * 根据主键物理删除
     *
     <#list pks as column>
     <#include "/include/column/properties.ftl">
     * @param ${fieldName}
     </#list>
     * @return
     <#include "/include/author_info1.ftl">
     */
     @RequestMapping(value = { "/delete" }, method = RequestMethod.GET)
     public ResponseVo<Integer> delete(<#include "/include/table/pk_params_validate.ftl">) {

        ResponseVo<${className}AddOrEditVm> result = ${classNameFirstLower}Service.add(vm);
        return result;
    }
    <#if table.validStatusColumn??>

    /**
     * 根据主键禁用
     *
     <#list pks as column>
     <#include "/include/column/properties.ftl">
     * @param ${fieldName}
     </#list>
     * @return
     <#include "/include/author_info1.ftl">
     */
    @RequestMapping(value = "/disable", method = RequestMethod.GET)
    public ResponseVo<Integer> disable(<#include "/include/table/pk_params_validate.ftl">) {

        ResponseVo<${className}AddOrEditVm> result = ${classNameFirstLower}Service.add(vm);
        return result;
    }

    /**
     * 根据主键启用
     *
     <#list pks as column>
     <#include "/include/column/properties.ftl">
     * @param ${fieldName}
     </#list>
     * @return
     <#include "/include/author_info1.ftl">
     */
    @RequestMapping(value = "/enable", method = RequestMethod.GET)
    public ResponseVo<Integer> enable(<#include "/include/table/pk_params_validate.ftl">) {

        ResponseVo<${className}AddOrEditVm> result = ${classNameFirstLower}Service.add(vm);
        return result;
    }
    </#if>
    <#if table.hasPk>

    /**
     * 根据主键获取
     *
     <#list pks as column>
     <#include "/include/column/properties.ftl">
     * @param ${fieldName}
     </#list>
     * @return
     <#include "/include/author_info1.ftl">
     */
    @RequestMapping(value = { "/detail" }, method = RequestMethod.GET)
    public ResponseVo<${className}DetailVm> detail(<#include "/include/table/pk_params_validate.ftl">) {

        ResponseVo<${className}AddOrEditVm> result = ${classNameFirstLower}Service.add(vm);
        return result;
    }

    /**
     * 编辑
     *
     * @param vm
     * @return
     <#include "/include/author_info1.ftl">
     */
    @RequestMapping(value = { "/update" }, method = RequestMethod.POST)
    public ResponseVo<Integer> update(@Validated({DataEdit.class}) ${className}AddOrEditVm vm<#if !table.hasAutoIncUniPk>, <#include "/include/table/pk_params.ftl"></#if>) {

        ResponseVo<${className}AddOrEditVm> result = ${classNameFirstLower}Service.add(vm);
        return result;
    }
    </#if>


    /**
     * 分页查询${tableComment}
     *
     * @param searchPage
     * @param sessionId
     * @return
    <#include "/include/author_info1.ftl">
     */
    @PostMapping("/getPageInfo")
    public ResponseVo<PageInfoVo<${className}DetailVm>> getPageInfo(@RequestBody ${className}SearchVm searchVm) {

        ResponseVo<${className}AddOrEditVm> result = ${classNameFirstLower}Service.add(vm);
        return result;
    }

}
