<#assign className = table.tableClassName>
<#assign classNameLower = table.tableClassNameFirstLower>
<#assign primaryKey = table.primaryKey>
<#assign primaryKeyParameters = table.primaryKeyParameters>
<#assign primaryKeyParameterValues = table.primaryKeyParameterValues>
<#macro mapperEl value>${r"${"}${value}}</#macro>
package ${basePackage}.quickadmin.controller;

import ${basePackage}.common.model.Result;
import ${basePackage}.entity.${className}Entity;
import ${basePackage}.parameter.${className}SelectParameter;
import ${basePackage}.quickadmin.service.${className}Service;
import ${basePackage}.quickadmin.vm.in.${className}OperateModel;
import ${basePackage}.quickadmin.vm.in.${className}SearchModel;
import ${basePackage}.quickadmin.vm.out.${className}DetailModel;
import ${basePackage}.quickadmin.vm.out.${className}ListModel;
import ${basePackage}.vo.${className}Vo;

import com.github.pagehelper.PageInfo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

<#include "/include/java_copyright.ftl">
@Controller
@RequestMapping({ "/${className?lower_case}" })
public class ${className}Controller {

    @Value("<@mapperEl 'spring.application.name'/>")
    String sessionId;

    @Autowired
    ${className}Service ${classNameLower}Service;

    @ResponseBody
    @RequestMapping(value = { "/find", "/find/" }, method = RequestMethod.GET)
    public Result<PageInfo<${className}Vo>> find(${className}SelectParameter inModel) {

        Result<PageInfo<${className}Vo>> result = ${classNameLower}Service.find${className}PageList(inModel, sessionId);
        return result;
    }

    @ResponseBody
    @RequestMapping(value = { "/getdetail", "/getdetail/" }, method = RequestMethod.GET)
    public Result<${className}Vo> getDetail(<#list primaryKey as column><#if (column_index > 0)>, </#if>@RequestParam("${column.columnFieldNameFirstLower}") ${column.columnFieldType} ${column.columnFieldNameFirstLower}</#list>) {

        Result<${className}Vo> result = ${classNameLower}Service.get${className}ByPk(${primaryKeyParameterValues}, sessionId);
        return result;
    }

    @ResponseBody
    @RequestMapping(value = { "/save", "/save/" }, method = RequestMethod.POST)
    public Result<Integer> save(${className}Entity entity<#if !table.hasAutoIncrementUniquePrimaryKey><#list primaryKey as column>, @RequestParam(value = "old${column.columnFieldName}", required = false) ${column.columnFieldType} old${column.columnFieldName}</#list></#if>) {

        Result<Integer> result;
        <#if !table.hasAutoIncrementUniquePrimaryKey>
        if (<#list primaryKey as column><#if (column_index > 0)> && </#if>old${column.columnFieldName} != null</#list>) {
            result = ${classNameLower}Service.update${className}ByPk(entity, ${table.primaryKeyOldParameterValues}, sessionId);
        } else {
            result = ${classNameLower}Service.add${className}(entity, sessionId);
        }
        <#else>
        if (<#list primaryKey as column><#if (column_index > 0)> && </#if>entity.get${column.columnFieldName}() != null</#list>) {
            result = ${classNameLower}Service.update${className}ByPk(entity, sessionId);
        } else {
            result = ${classNameLower}Service.add${className}(entity, sessionId);
        }
        </#if>
        return result;
    }

    @ResponseBody
    @RequestMapping(value = { "/delete", "/delete/" }, method = RequestMethod.GET)
    public Result<Integer> delete(<#list primaryKey as column><#if (column_index > 0)>, </#if>@RequestParam("${column.columnFieldNameFirstLower}") ${column.columnFieldType} ${column.columnFieldNameFirstLower}</#list>) {

        Result<Integer> result = ${classNameLower}Service.delete${className}ByPk(${primaryKeyParameterValues}, sessionId);
        return result;
    }
}
