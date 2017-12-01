<#assign className = table.tableClassName>
<#assign classNameLower = table.tableClassNameFirstLower>
package ${basePackage}.controller;

<#include "/include/java_copyright.ftl">
@RequestMapping({ "/${className?lower_case}" })
public class ${className}Controller {
}