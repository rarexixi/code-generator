<#assign className = table.tableClassName>
<#assign classNameLower = table.tableClassName?uncap_first>
<#assign primaryKey = table.primaryKey>
<#assign primaryKeyParameters = table.primaryKeyParameters>
<#assign primaryKeyParameterValues = table.primaryKeyParameterValues>
package ${basePackage}.admin.controller;

import ${baseCommonPackage}.model.ResponseVo;
import ${baseCommonPackage}.model.ResultVo;
import ${baseCommonPackage}.utils.LogUtils;
import ${basePackage}.admin.service.${className}Service;
import ${basePackage}.admin.vm.addoredit.${className}AddOrEditVm;
import ${basePackage}.admin.vm.detail.${className}DetailVm;
import ${basePackage}.admin.vm.search.${className}SearchVm;
import ${basePackage}.entity.${className}Entity;
import ${basePackage}.parameter.${className}SelectParameter;
import ${basePackage}.vo.${className}Vo;

import com.github.pagehelper.PageInfo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

<#include "/include/java_copyright.ftl">
@RestController
@RequestMapping("/${table.tableClassName?lower_case}")
public class ${className}Controller {

    private static LogUtils logger = LogUtils.build(${className}Controller.class);

    @Value("${r'${spring.application.name}'}")
    private String applicationName;

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
    public ResponseVo<${className}AddOrEditVm> add(${className}AddOrEditVm vm) {

        ResponseVo<${className}AddOrEditVm> responseVo;
        ${className}Entity entity = vm.get${className}Entity();
        ResultVo<${className}Entity> apiResult = ${classNameLower}Api.add(entity, getSessionId());
        if (apiResult.isSuccess()) {
            vm.set${className}Entity(apiResult.getResult());
            responseVo = new ResponseVo<>(vm);
        } else {
            responseVo = new ResponseVo<>(apiResult.getMessage());
        }

        return responseVo;
    }

    /**
     * 编辑
     *
     * @param vm
     * @return
     <#include "/include/author_info1.ftl">
     */
    @RequestMapping(value = { "/edit" }, method = RequestMethod.POST)
    public ResponseVo<Integer> edit(${className}AddOrEditVm vm<#if !table.hasAutoIncrementUniquePrimaryKey><#list primaryKey as column>, ${column.columnFieldType} old${column.columnFieldName}</#list></#if>) {

        ResponseVo<Integer> responseVo;
        ResultVo<Integer> apiResult = ${classNameLower}Api.updateByPk(vm.get${className}Entity()<#if !table.hasAutoIncrementUniquePrimaryKey><#list primaryKey as column>, old${column.columnFieldName}</#list></#if>, getSessionId());
        if (apiResult.isSuccess()) {
            responseVo = new ResponseVo<>(apiResult.getResult());
        } else {
            responseVo = new ResponseVo<>(apiResult.getMessage());
        }

        return responseVo;
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

        ResponseVo<Integer> responseVo;
        List<${className}Entity> entityList = list.stream().map(o->o.get${className}Entity()).collect(Collectors.toList());
        ResultVo<Integer> apiResult = ${classNameLower}Service.addList(entityList, getSessionId());
        if (apiResult.isSuccess()) {
            responseVo = new ResponseVo<>(apiResult.getResult());
        } else {
            responseVo = new ResponseVo<>(apiResult.getMessage());
        }

        return responseVo;
    }
    <#if table.hasPrimaryKey>

    /**
     * 根据主键物理删除
     *
     <#list primaryKey as column>
     * @param ${column.columnFieldNameFirstLower}
     </#list>
     * @return
     <#include "/include/author_info1.ftl">
     */
     @RequestMapping(value = { "/delete" }, method = RequestMethod.GET)
     public ResponseVo<Integer> delete(<#list primaryKey as column><#if (column_index > 0)>, </#if>@RequestParam("${column.columnFieldNameFirstLower}") ${column.columnFieldType} ${column.columnFieldNameFirstLower}</#list>) {

         ResponseVo<Integer> responseVo;
         ResultVo<Integer> apiResult = ${classNameLower}Service.deleteByPk(${primaryKeyParameterValues}, getSessionId());
         if (apiResult.isSuccess()) {
             responseVo = new ResponseVo<>(apiResult.getResult());
         } else {
             responseVo = new ResponseVo<>(apiResult.getMessage());
         }

         return responseVo;
    }
    <#if (table.uniquePrimaryKey??)>

    /**
     * 根据主键列表物理删除
     *
     * @param ${table.uniquePrimaryKey.columnFieldName?uncap_first}List
     * @return
     <#include "/include/author_info1.ftl">
     */
     @RequestMapping(value = { "/deleteList" }, method = RequestMethod.POST)
     public ResponseVo<Integer> deleteList(@RequestBody List<${table.uniquePrimaryKey.columnFieldType}> ${table.uniquePrimaryKey.columnFieldName?uncap_first}List) {

        ResponseVo<Integer> responseVo;
        ResultVo<Integer> apiResult = ${classNameLower}Service.deleteByPkList(${table.uniquePrimaryKey.columnFieldName?uncap_first}List, getSessionId());
        if (apiResult.isSuccess()) {
            responseVo = new ResponseVo<>(apiResult.getResult());
        } else {
            responseVo = new ResponseVo<>(apiResult.getMessage());
        }

        return responseVo;
    }
    </#if>
    <#if table.validStatusColumn??>

    /**
     * 根据主键禁用
     *
     <#list primaryKey as column>
     * @param ${column.columnFieldNameFirstLower}
     </#list>
     * @return
     <#include "/include/author_info1.ftl">
     */
    @RequestMapping(value = "/disable", method = RequestMethod.GET)
    public ResponseVo<Integer> disable(${primaryKeyParameters}) {

        ResponseVo<Integer> responseVo;
        ResultVo<Integer> apiResult = ${classNameLower}Service.disableByPk(${primaryKeyParameterValues}, getSessionId());
        if (apiResult.isSuccess()) {
            responseVo = new ResponseVo<>(apiResult.getResult());
        } else {
            responseVo = new ResponseVo<>(apiResult.getMessage());
        }

        return responseVo;
    }

    /**
     * 根据主键启用
     *
     <#list primaryKey as column>
     * @param ${column.columnFieldNameFirstLower}
     </#list>
     * @return
     <#include "/include/author_info1.ftl">
     */
    @RequestMapping(value = "/enable", method = RequestMethod.GET)
    public ResponseVo<Integer> enable(${primaryKeyParameters}) {

        ResponseVo<Integer> responseVo;
        ResultVo<Integer> apiResult = ${classNameLower}Service.enableByPk(${primaryKeyParameterValues}, getSessionId());
        if (apiResult.isSuccess()) {
            responseVo = new ResponseVo<>(apiResult.getResult());
        } else {
            responseVo = new ResponseVo<>(apiResult.getMessage());
        }

        return responseVo;
    }
    <#if (table.uniquePrimaryKey??)>

    /**
     * 根据主键列表禁用
     *
     * @param ${table.uniquePrimaryKey.columnFieldName?uncap_first}List
     * @return
     <#include "/include/author_info1.ftl">
     */
    @RequestMapping(value = "/disableList", method = RequestMethod.POST)
    public ResponseVo<Integer> disableList(@RequestBody List<${table.uniquePrimaryKey.columnFieldType}> ${table.uniquePrimaryKey.columnFieldName?uncap_first}List) {

        ResponseVo<Integer> responseVo;
        ResultVo<Integer> apiResult = ${classNameLower}Service.disableByPkList(${table.uniquePrimaryKey.columnFieldName?uncap_first}List, getSessionId());
        if (apiResult.isSuccess()) {
            responseVo = new ResponseVo<>(apiResult.getResult());
        } else {
            responseVo = new ResponseVo<>(apiResult.getMessage());
        }

        return responseVo;
    }

    /**
     * 根据主键列表启用
     *
     * @param ${table.uniquePrimaryKey.columnFieldName?uncap_first}List
     * @return
     <#include "/include/author_info1.ftl">
     */
    @RequestMapping(value = "/enableList", method = RequestMethod.POST)
    public ResponseVo<Integer> enableList(@RequestBody List<${table.uniquePrimaryKey.columnFieldType}> ${table.uniquePrimaryKey.columnFieldName?uncap_first}List) {

        ResponseVo<Integer> responseVo;
        ResultVo<Integer> apiResult = ${classNameLower}Service.enableByPkList(${table.uniquePrimaryKey.columnFieldName?uncap_first}List, getSessionId());
        if (apiResult.isSuccess()) {
            responseVo = new ResponseVo<>(apiResult.getResult());
        } else {
            responseVo = new ResponseVo<>(apiResult.getMessage());
        }

        return responseVo;
    }
    </#if>
    </#if>

    /**
     * 根据主键获取
     *
     <#list primaryKey as column>
     * @param ${column.columnFieldNameFirstLower}
     </#list>
     * @return
     <#include "/include/author_info1.ftl">
     */
    @RequestMapping(value = { "/getDetail" }, method = RequestMethod.GET)
    public ResponseVo<${className}DetailVm> getDetail(<#list primaryKey as column><#if (column_index > 0)>, </#if>@RequestParam("${column.columnFieldNameFirstLower}") ${column.columnFieldType} ${column.columnFieldNameFirstLower}</#list>) {

        ResponseVo<${className}DetailVm> responseVo;
        ResultVo<${className}Vo> apiResult = ${classNameLower}Service.getByPk(${primaryKeyParameterValues}, getSessionId());
        if (apiResult.isSuccess()) {
            responseVo = new ResponseVo<>(true);
            ${className}Vo vo;
            if ((vo = apiResult.getResult()) != null) {
                ${className}DetailVm vm = new ${className}DetailVm(vo);
                responseVo.setResult(vm);
            }
        } else {
            responseVo = new ResponseVo<>(apiResult.getMessage());
        }

        return responseVo;
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

        ResponseVo<PageInfo<${className}Vo>> responseVo;
        ${className}SelectParameter parameter = searchVm.get${className}SelectParameter();
        ResultVo<PageInfo<${className}Vo>> apiResult = ${classNameLower}Service.findPageList(parameter, getSessionId());
        if (apiResult.isSuccess()) {
            PageInfo<${className}Vo> pageInfo = apiResult.getResult();
            responseVo = new ResponseVo<>(pageInfo);
        } else {
            responseVo = new ResponseVo<>(apiResult.getMessage());
        }

        return responseVo;
    }

    private String getSessionId() {
        return applicationName + "-" + UUID.randomUUID();
    }

}
