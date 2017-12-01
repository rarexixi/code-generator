<#assign className = table.tableClassName>
<#assign classNameLower = table.tableClassNameFirstLower>
package ${basePackage}.controller;

import ${basePackage}.vm.in.${className}OperateModel;
import ${basePackage}.vm.in.${className}SearchModel;
import ${basePackage}.vm.out.${className}DetailModel;
import ${basePackage}.vm.out.${className}ListModel;

<#include "/include/java_copyright.ftl">
@RequestMapping({ "/${className?lower_case}" })
public class ${className}Controller {


    @RequestMapping(value = { "", "/" }, method = RequestMethod.GET)
    public String index(${className}SearchModel inModel, Model outModel) {

        return "filemanager/index";
    }
}