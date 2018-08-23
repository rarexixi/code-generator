<#assign className = table.tableClassName>
<#assign classNameLower = table.tableClassName?uncap_first>
<#assign primaryKey = table.primaryKey>
<#assign primaryKeyParameters = table.primaryKeyParameters>
<#assign primaryKeyParameterValues = table.primaryKeyParameterValues>
package ${basePackage}.admin.service.impl;

import ${baseCommonPackage}.model.ResponseVo;
import ${baseCommonPackage}.model.ResultVo;
import ${baseCommonPackage}.model.SearchPage;
import ${basePackage}.admin.service.${className}Service;
import ${basePackage}.admin.vm.addoredit.${className}AddOrEditVm;
import ${basePackage}.admin.vm.detail.${className}DetailVm;
import ${basePackage}.admin.vm.search.${className}SearchVm;
import ${basePackage}.api.${className}Api;
import ${basePackage}.entity.${className}Entity;
import ${basePackage}.parameter.${className}SelectParameter;
import ${basePackage}.vo.${className}Vo;

import com.alibaba.dubbo.config.annotation.Reference;
import com.github.pagehelper.PageInfo;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

<#include "/include/java_copyright.ftl">
@Service("${classNameLower}Service")
public class ${className}ServiceImpl implements ${className}Service {

    @Value("${r'${spring.application.name}'}")
    private String applicationName;

    @Reference
    private ${className}Api ${classNameLower}Api;

    /**
     * 添加
     *
     * @param vm
     * @return
     <#include "/include/author_info1.ftl">
     */
    @Override
    public ResponseVo<Integer> add(${className}AddOrEditVm vm) {

        ResponseVo<Integer> responseVo;
        ${className}Entity entity = vm.get${className}Entity();
        ResultVo<Integer> apiResult = ${classNameLower}Api.add(entity, getSessionId());
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
    @Override
    public ResponseVo<Integer> addList(List<${className}AddOrEditVm> list) {

        ResponseVo<Integer> responseVo;
        List<${className}Entity> entityList = list.stream().map(o->o.get${className}Entity()).collect(Collectors.toList());
        ResultVo<Integer> apiResult = ${classNameLower}Api.addList(entityList, getSessionId());
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
    @Override
    public ResponseVo<Integer> deleteByPk(${primaryKeyParameters}) {

        ResponseVo<Integer> responseVo;
        ResultVo<Integer> apiResult = ${classNameLower}Api.deleteByPk(${primaryKeyParameterValues}, getSessionId());
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
    @Override
    public ResponseVo<Integer> deleteByPkList(List<${table.uniquePrimaryKey.columnFieldType}> ${table.uniquePrimaryKey.columnFieldName?uncap_first}List) {

        ResponseVo<Integer> responseVo;
        ResultVo<Integer> apiResult = ${classNameLower}Api.deleteByPkList(${table.uniquePrimaryKey.columnFieldName?uncap_first}List, getSessionId());
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
    @Override
    public ResponseVo<Integer> disableByPk(${primaryKeyParameters}) {

        ResponseVo<Integer> responseVo;
        ResultVo<Integer> apiResult = ${classNameLower}Api.disableByPk(${primaryKeyParameterValues}, getSessionId());
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
    @Override
    public ResponseVo<Integer> enableByPk(${primaryKeyParameters}) {

        ResponseVo<Integer> responseVo;
        ResultVo<Integer> apiResult = ${classNameLower}Api.enableByPk(${primaryKeyParameterValues}, getSessionId());
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
    @Override
    public ResponseVo<Integer> disableByPkList(List<${table.uniquePrimaryKey.columnFieldType}> ${table.uniquePrimaryKey.columnFieldName?uncap_first}List) {

        ResponseVo<Integer> responseVo;
        ResultVo<Integer> apiResult = ${classNameLower}Api.disableByPkList(${table.uniquePrimaryKey.columnFieldName?uncap_first}List, getSessionId());
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
    @Override
    public ResponseVo<Integer> enableByPkList(List<${table.uniquePrimaryKey.columnFieldType}> ${table.uniquePrimaryKey.columnFieldName?uncap_first}List) {

        ResponseVo<Integer> responseVo;
        ResultVo<Integer> apiResult = ${classNameLower}Api.enableByPkList(${table.uniquePrimaryKey.columnFieldName?uncap_first}List, getSessionId());
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
     * 根据主键更新
     *
     * @param vm
     * @return
     <#include "/include/author_info1.ftl">
     */
    @Override
    public ResponseVo<Integer> updateByPk(${className}AddOrEditVm vm<#if !table.hasAutoIncrementUniquePrimaryKey><#list primaryKey as column>, ${column.columnFieldType} old${column.columnFieldName}</#list></#if>) {

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
     * 根据主键获取
     *
     <#list primaryKey as column>
     * @param ${column.columnFieldNameFirstLower}
     </#list>
     * @return
     <#include "/include/author_info1.ftl">
     */
    @Override
    public ResponseVo<${className}DetailVm> getByPk(${primaryKeyParameters}) {

        ResponseVo<${className}DetailVm> responseVo;
        ResultVo<${className}Vo> apiResult = ${classNameLower}Api.getByPk(${primaryKeyParameterValues}, getSessionId());
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
    @Override
    public ResponseVo<PageInfo<${className}Vo>> findPageList(${className}SearchVm searchVm) {

        ResponseVo<PageInfo<${className}Vo>> responseVo;
        ${className}SelectParameter parameter = searchVm.get${className}SelectParameter();
        ResultVo<PageInfo<${className}Vo>> apiResult = ${classNameLower}Api.findPageList(parameter, getSessionId());
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
