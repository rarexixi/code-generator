<#assign className = table.tableClassName>
<#assign classNameLower = table.tableClassNameFirstLower>
package ${basePackage}.quickapi.controller;

import ${basePackage}.quickapi.vm.in.${className}OperateModel;
import ${basePackage}.quickapi.vm.in.${className}SearchModel;
import ${basePackage}.quickapi.vm.out.${className}DetailModel;
import ${basePackage}.quickapi.vm.out.${className}ListModel;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

<#include "/include/java_copyright.ftl">
@RequestMapping({ "/${className?lower_case}" })
public class ${className}Controller {


    @RequestMapping(value = { "", "/" }, method = RequestMethod.GET)
    public String index(${className}SearchModel inModel, Model outModel) {

        return "filemanager/index";
    }
}
