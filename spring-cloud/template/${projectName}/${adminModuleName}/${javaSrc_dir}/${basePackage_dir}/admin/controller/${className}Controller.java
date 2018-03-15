<#assign className = table.tableClassName>
<#assign classNameLower = table.tableClassName?uncap_first>
<#assign primaryKey = table.primaryKey>
<#assign primaryKeyParameters = table.primaryKeyParameters>
<#assign primaryKeyParameterValues = table.primaryKeyParameterValues>
package ${basePackage}.admin.controller;

import ${baseCommonPackage}.model.ResponseVo;
import ${baseCommonPackage}.model.Result;
import ${baseCommonPackage}.utils.LogUtil;
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

    private static LogUtil logger = LogUtil.build(${className}Controller.class);

    @Value("${r'${spring.application.name}'}")
    private String sessionId;

    @Autowired
    private ${className}Service ${classNameLower}Service;

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
        Result<Integer> apiResult = ${classNameLower}Service.addList(entityList, sessionId);
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
         Result<Integer> apiResult = ${classNameLower}Service.deleteByPk(${primaryKeyParameterValues}, sessionId);
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
     @RequestMapping(value = { "/deletelist" }, method = RequestMethod.POST)
     public ResponseVo<Integer> deleteList(@RequestBody List<${table.uniquePrimaryKey.columnFieldType}> ${table.uniquePrimaryKey.columnFieldName?uncap_first}List) {

        ResponseVo<Integer> responseVo;
        Result<Integer> apiResult = ${classNameLower}Service.deleteByPkList(${table.uniquePrimaryKey.columnFieldName?uncap_first}List, sessionId);
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
     * 根据主键冻结
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
        Result<Integer> apiResult = ${classNameLower}Service.disableByPk(${primaryKeyParameterValues}, sessionId);
        if (apiResult.isSuccess()) {
            responseVo = new ResponseVo<>(apiResult.getResult());
        } else {
            responseVo = new ResponseVo<>(apiResult.getMessage());
        }

        return responseVo;
    }

    /**
     * 根据主键激活
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
        Result<Integer> apiResult = ${classNameLower}Service.enableByPk(${primaryKeyParameterValues}, sessionId);
        if (apiResult.isSuccess()) {
            responseVo = new ResponseVo<>(apiResult.getResult());
        } else {
            responseVo = new ResponseVo<>(apiResult.getMessage());
        }

        return responseVo;
    }
    <#if (table.uniquePrimaryKey??)>

    /**
     * 根据主键列表冻结
     *
     * @param ${table.uniquePrimaryKey.columnFieldName?uncap_first}List
     * @return
     <#include "/include/author_info1.ftl">
     */
    @RequestMapping(value = "/disablelist", method = RequestMethod.POST)
    public ResponseVo<Integer> disableList(@RequestBody List<${table.uniquePrimaryKey.columnFieldType}> ${table.uniquePrimaryKey.columnFieldName?uncap_first}List) {

        ResponseVo<Integer> responseVo;
        Result<Integer> apiResult = ${classNameLower}Service.disableByPkList(${table.uniquePrimaryKey.columnFieldName?uncap_first}List, sessionId);
        if (apiResult.isSuccess()) {
            responseVo = new ResponseVo<>(apiResult.getResult());
        } else {
            responseVo = new ResponseVo<>(apiResult.getMessage());
        }

        return responseVo;
    }

    /**
     * 根据主键列表激活
     *
     * @param ${table.uniquePrimaryKey.columnFieldName?uncap_first}List
     * @return
     <#include "/include/author_info1.ftl">
     */
    @RequestMapping(value = "/enablelist", method = RequestMethod.POST)
    public ResponseVo<Integer> enableList(@RequestBody List<${table.uniquePrimaryKey.columnFieldType}> ${table.uniquePrimaryKey.columnFieldName?uncap_first}List) {

        ResponseVo<Integer> responseVo;
        Result<Integer> apiResult = ${classNameLower}Service.enableByPkList(${table.uniquePrimaryKey.columnFieldName?uncap_first}List, sessionId);
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
     * 保存
     *
     * @param vm
     * @return
     <#include "/include/author_info1.ftl">
     */
    @RequestMapping(value = { "/save" }, method = RequestMethod.POST)
    public ResponseVo<Integer> save(@RequestBody ${className}AddOrEditVm vm<#if !table.hasAutoIncrementUniquePrimaryKey><#list primaryKey as column>, @RequestParam(value = "old${column.columnFieldName}", required = false) ${column.columnFieldType} old${column.columnFieldName}</#list></#if>) {

        ${className}Entity entity = vm.get${className}Entity();
        ResponseVo<Integer> responseVo;
        Result<Integer> apiResult;
        if (<#list primaryKey as column><#if (column_index > 0)> || </#if><#if !table.hasAutoIncrementUniquePrimaryKey>old${column.columnFieldName}<#else>vm.get${column.columnFieldName}()</#if> == null</#list>) {
            apiResult = ${classNameLower}Service.add(entity, sessionId);
        } else {
            apiResult = ${classNameLower}Service.updateByPk(vm.get${className}Entity()<#if !table.hasAutoIncrementUniquePrimaryKey><#list primaryKey as column>, old${column.columnFieldName}</#list></#if>, sessionId);
        }

        if (apiResult.isSuccess()) {
            responseVo = new ResponseVo<>(apiResult.getResult());
        } else {
            responseVo = new ResponseVo<>(apiResult.getMessage());
        }

        return responseVo;
    }

    /**
     * 根据主键获取
     *
     <#list primaryKey as column>
     * @param ${column.columnFieldNameFirstLower}
     </#list>
     * @return
     <#include "/include/author_info1.ftl">
     */
    @RequestMapping(value = { "/getdetail" }, method = RequestMethod.GET)
    public ResponseVo<${className}DetailVm> getDetail(<#list primaryKey as column><#if (column_index > 0)>, </#if>@RequestParam("${column.columnFieldNameFirstLower}") ${column.columnFieldType} ${column.columnFieldNameFirstLower}</#list>) {

        ResponseVo<${className}DetailVm> responseVo;
        Result<${className}Vo> apiResult = ${classNameLower}Service.getByPk(${primaryKeyParameterValues}, sessionId);
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
        Result<PageInfo<${className}Vo>> apiResult = ${classNameLower}Service.findPageList(parameter, sessionId);
        if (apiResult.isSuccess()) {
            PageInfo<${className}Vo> pageInfo = apiResult.getResult();
            responseVo = new ResponseVo<>(pageInfo);
        } else {
            responseVo = new ResponseVo<>(apiResult.getMessage());
        }

        return responseVo;
    }

}
