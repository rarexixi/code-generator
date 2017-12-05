<#assign className = table.tableClassName>
<#assign classNameLower = table.tableClassNameFirstLower>
<#assign primaryKey = table.primaryKey>
<#assign primaryKeyParameters = table.primaryKeyParameters>
<#assign primaryKeyParameterValues = table.primaryKeyParameterValues>
package ${basePackage}.quickadmin.controller;

import ${basePackage}.common.model.Result;
import ${basePackage}.entity.${className}Entity;
import ${basePackage}.quickadmin.service.${className}Service;
import ${basePackage}.quickadmin.vm.in.${className}OperateModel;
import ${basePackage}.quickadmin.vm.in.${className}SearchModel;
import ${basePackage}.quickadmin.vm.out.${className}DetailModel;
import ${basePackage}.quickadmin.vm.out.${className}ListModel;
import ${basePackage}.vo.${className}Vo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

<#include "/include/java_copyright.ftl">
@Controller
@RequestMapping({ "/${className?lower_case}" })
public class ${className}Controller {

    String sessionId = "quick-admin";

    @Autowired
    ${className}Service ${classNameLower}Service;

    @RequestMapping(value = { "", "/" }, method = RequestMethod.GET)
    public String index(${className}SearchModel inModel, Model outModel) {

        return "${className?lower_case}/list";
    }

    @RequestMapping(value = { "/addoredit", "/addoredit/" }, method = RequestMethod.GET)
    public String addOrEdit(${className}SearchModel inModel, Model outModel) {

        return "${className?lower_case}/addoredit";
    }

    @RequestMapping(value = { "/detail", "/detail/" }, method = RequestMethod.GET)
    public String detail(<#list primaryKey as column><#if (column_index > 0)>, </#if>@RequestParam(value="${column.columnFieldNameFirstLower}") ${column.columnFieldType} ${column.columnFieldNameFirstLower}</#list>, Model outModel) {

        return "${className?lower_case}/detail";
    }

    @ResponseBody
    @RequestMapping(value = { "/getdetail", "/getdetail/" }, method = RequestMethod.GET)
    public Result<${className}Vo> getDetail(<#list primaryKey as column><#if (column_index > 0)>, </#if>@RequestParam("${column.columnFieldNameFirstLower}") ${column.columnFieldType} ${column.columnFieldNameFirstLower}</#list>) {

        Result<${className}Vo> result = ${classNameLower}Service.get${className}ByPk(${primaryKeyParameterValues}, sessionId);
        return result;
    }

    @ResponseBody
    @RequestMapping(value = { "/save", "/save/" }, method = RequestMethod.POST)
    public Result<Integer> save(${className}Entity entity) {

        return null;
    }

    @ResponseBody
    @RequestMapping(value = { "/delete", "/delete/" }, method = RequestMethod.GET)
    public Result<Integer> delete(<#list primaryKey as column><#if (column_index > 0)>, </#if>@RequestParam("${column.columnFieldNameFirstLower}") ${column.columnFieldType} ${column.columnFieldNameFirstLower}</#list>) {

        Result<Integer> result = ${classNameLower}Service.delete${className}ByPk(${primaryKeyParameterValues}, sessionId);
        return result;
    }
}
