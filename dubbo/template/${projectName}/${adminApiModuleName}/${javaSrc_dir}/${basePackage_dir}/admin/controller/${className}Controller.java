<#assign className = table.tableClassName>
<#assign classNameLower = table.tableClassName?uncap_first>
<#assign primaryKey = table.primaryKey>
<#assign primaryKeyParameters = table.primaryKeyParameters>
<#assign primaryKeyParameterValues = table.primaryKeyParameterValues>
package ${basePackage}.admin.controller;

import ${baseCommonPackage}.model.ResponseVo;
import ${baseCommonPackage}.utils.LogUtils;
import ${baseCommonPackage}.validation.DataAdd;
import ${baseCommonPackage}.validation.DataEdit;
import ${basePackage}.admin.service.${className}Service;
import ${basePackage}.admin.vm.addoredit.${className}AddOrEditVm;
import ${basePackage}.admin.vm.detail.${className}DetailVm;
import ${basePackage}.admin.vm.search.${className}SearchVm;
import ${basePackage}.vo.${className}Vo;

import com.github.pagehelper.PageInfo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.List;

<#include "/include/java_copyright.ftl">
@RestController
@RequestMapping("/${table.tableClassName?lower_case}")
@Validated
public class ${className}Controller {

    private static LogUtils logger = LogUtils.build(${className}Controller.class);

    @Autowired
    private ${className}Service ${classNameLower}Service;

    /**
     * 添加
     *
     * @param vm
     * @return
     <#include "/include/author_info1.ftl">
     */
    @RequestMapping(value = { "/add" }, method = RequestMethod.POST)
    public ResponseVo<${className}AddOrEditVm> add(@Validated({DataAdd.class}) @RequestBody ${className}AddOrEditVm vm, Errors errors) {

        ResponseVo<${className}AddOrEditVm> result = ${classNameLower}Service.add(vm);
        return result;
    }

    /**
     * 编辑
     *
     * @param vm
     * @return
     <#include "/include/author_info1.ftl">
     */
    @RequestMapping(value = { "/edit" }, method = RequestMethod.POST)
    public ResponseVo<Integer> edit(@Validated({DataEdit.class}) @RequestBody ${className}AddOrEditVm vm, Errors errors<#if !table.hasAutoIncrementUniquePrimaryKey><#list primaryKey as column>, @NotNull @RequestParam(value = "old${column.targetColumnName}") ${column.targetDataType} old${column.targetColumnName}</#list></#if>) {

        ResponseVo<Integer> result = ${classNameLower}Service.updateByPk(vm<#if !table.hasAutoIncrementUniquePrimaryKey>, ${table.primaryKeyOldParameterValues}</#if>);
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
    public ResponseVo<Integer> addList(@Validated({DataAdd.class}) @RequestBody List<${className}AddOrEditVm> list) {

        ResponseVo<Integer> result = ${classNameLower}Service.addList(list);
        return result;
    }
    <#if table.hasPrimaryKey>

    /**
     * 根据主键物理删除
     *
     <#list primaryKey as column>
     * @param ${column.targetColumnNameFirstLower}
     </#list>
     * @return
     <#include "/include/author_info1.ftl">
     */
     @RequestMapping(value = { "/delete" }, method = RequestMethod.GET)
     public ResponseVo<Integer> delete(<#list primaryKey as column><#if (column_index > 0)>, </#if>@NotNull @RequestParam("${column.targetColumnNameFirstLower}") ${column.targetDataType} ${column.targetColumnNameFirstLower}</#list>) {

        ResponseVo<Integer> result = ${classNameLower}Service.deleteByPk(${primaryKeyParameterValues});
        return result;
    }
    <#if (table.uniquePrimaryKey??)>

    /**
     * 根据主键列表物理删除
     *
     * @param ${table.uniquePrimaryKey.targetColumnName?uncap_first}List
     * @return
     <#include "/include/author_info1.ftl">
     */
     @RequestMapping(value = { "/deleteList" }, method = RequestMethod.POST)
     public ResponseVo<Integer> deleteList(@RequestBody List<${table.uniquePrimaryKey.targetDataType}> ${table.uniquePrimaryKey.targetColumnName?uncap_first}List) {

        ResponseVo<Integer> result = ${classNameLower}Service.deleteByPkList(${table.uniquePrimaryKey.targetColumnName?uncap_first}List);
        return result;
    }
    </#if>
    <#if table.validStatusColumn??>

    /**
     * 根据主键禁用
     *
     <#list primaryKey as column>
     * @param ${column.targetColumnNameFirstLower}
     </#list>
     * @return
     <#include "/include/author_info1.ftl">
     */
    @RequestMapping(value = "/disable", method = RequestMethod.GET)
    public ResponseVo<Integer> disable(<#list primaryKey as column><#if (column_index > 0)>, </#if>@NotNull ${column.targetDataType} ${column.targetColumnName?uncap_first}</#list>) {

        ResponseVo<Integer> result = ${classNameLower}Service.disableByPk(${primaryKeyParameterValues});
        return result;
    }

    /**
     * 根据主键启用
     *
     <#list primaryKey as column>
     * @param ${column.targetColumnNameFirstLower}
     </#list>
     * @return
     <#include "/include/author_info1.ftl">
     */
    @RequestMapping(value = "/enable", method = RequestMethod.GET)
    public ResponseVo<Integer> enable(<#list primaryKey as column><#if (column_index > 0)>, </#if>@NotNull ${column.targetDataType} ${column.targetColumnName?uncap_first}</#list>) {

        ResponseVo<Integer> result = ${classNameLower}Service.enableByPk(${primaryKeyParameterValues});
        return result;
    }
    <#if (table.uniquePrimaryKey??)>

    /**
     * 根据主键列表禁用
     *
     * @param ${table.uniquePrimaryKey.targetColumnName?uncap_first}List
     * @return
     <#include "/include/author_info1.ftl">
     */
    @RequestMapping(value = "/disableList", method = RequestMethod.POST)
    public ResponseVo<Integer> disableList(@NotNull @RequestBody List<${table.uniquePrimaryKey.targetDataType}> ${table.uniquePrimaryKey.targetColumnName?uncap_first}List) {

        ResponseVo<Integer> result = ${classNameLower}Service.disableByPkList(${table.uniquePrimaryKey.targetColumnName?uncap_first}List);
        return result;
    }

    /**
     * 根据主键列表启用
     *
     * @param ${table.uniquePrimaryKey.targetColumnName?uncap_first}List
     * @return
     <#include "/include/author_info1.ftl">
     */
    @RequestMapping(value = "/enableList", method = RequestMethod.POST)
    public ResponseVo<Integer> enableList(@NotNull @RequestBody List<${table.uniquePrimaryKey.targetDataType}> ${table.uniquePrimaryKey.targetColumnName?uncap_first}List) {

        ResponseVo<Integer> result = ${classNameLower}Service.enableByPkList(${table.uniquePrimaryKey.targetColumnName?uncap_first}List);
        return result;
    }
    </#if>
    </#if>

    /**
     * 根据主键获取
     *
     <#list primaryKey as column>
     * @param ${column.targetColumnNameFirstLower}
     </#list>
     * @return
     <#include "/include/author_info1.ftl">
     */
    @RequestMapping(value = { "/getDetail" }, method = RequestMethod.GET)
    public ResponseVo<${className}DetailVm> getDetail(<#list primaryKey as column><#if (column_index > 0)>, </#if>@NotNull @RequestParam("${column.targetColumnNameFirstLower}") ${column.targetDataType} ${column.targetColumnNameFirstLower}</#list>) {

        ResponseVo<${className}DetailVm> result = ${classNameLower}Service.getByPk(${primaryKeyParameterValues});
        return result;
    }
    </#if>

    /**
     * 分页查询
     *
     * @param searchVm
     * @return
     <#include "/include/author_info1.ftl">
     */
    @RequestMapping(value = { "/find" }, method = RequestMethod.POST)
    public ResponseVo<PageInfo<${className}Vo>> find(@RequestBody ${className}SearchVm searchVm) {

        ResponseVo<PageInfo<${className}Vo>> result = ${classNameLower}Service.findPageList(searchVm);
        return result;
    }

}
