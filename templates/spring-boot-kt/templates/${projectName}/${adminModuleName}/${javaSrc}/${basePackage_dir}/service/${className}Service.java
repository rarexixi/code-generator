<#include "/include/table/properties.ftl">
package ${basePackage}.service;

import ${basePackage}.common.service.BaseService;
import ${basePackage}.common.service.BaseServiceExtension;
import ${basePackage}.models.condition.${className}Condition;
import ${basePackage}.models.condition.extension.${className}ConditionExtension;
import ${basePackage}.models.condition.order.${className}OrderCondition;
import ${basePackage}.models.entity.${className}Entity;
import ${basePackage}.models.entity.extension.${className}EntityExtension;

<#include "/include/java_copyright.ftl">
public interface ${className}Service extends BaseService<${className}Entity, ${className}Condition, ${className}OrderCondition>,
        BaseServiceExtension<${className}EntityExtension, ${className}ConditionExtension, ${className}OrderCondition>{

    <#if table.hasPk>

    /**
     * 根据主键获取${tableComment}详情
     *
     <#list pks as column>
     <#include "/include/column/properties.ftl">
     * @param ${fieldName}
     </#list>
     * @return
     <#include "/include/author_info1.ftl">
     */
    ${className}EntityExtension getByPk(<#include "/include/table/pk_params.ftl">);
    </#if>
}
