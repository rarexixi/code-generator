<#include "/include/table/properties.ftl">
package ${basePackage}.service.impl;

import ${basePackage}.common.service.impl.BaseServiceImpl;
import ${basePackage}.mapper.${className}Mapper;
import ${basePackage}.models.condition.${className}Condition;
import ${basePackage}.models.condition.extension.${className}ConditionExtension;
import ${basePackage}.models.condition.order.${className}OrderCondition;
import ${basePackage}.models.entity.${className}Entity;
import ${basePackage}.models.entity.extension.${className}EntityExtension;
import ${basePackage}.service.${className}Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

<#include "/include/java_copyright.ftl">
@Service("${classNameFirstLower}Service")
@Transactional
public class ${className}ServiceImpl extends BaseServiceImpl<${className}Entity, ${className}Condition, ${className}OrderCondition, ${className}EntityExtension, ${className}ConditionExtension> implements ${className}Service {

    @Autowired
    public ${className}ServiceImpl(${className}Mapper ${classNameFirstLower}Mapper) {
        this.${classNameFirstLower}Mapper = ${classNameFirstLower}Mapper;
        super.mapper = ${classNameFirstLower}Mapper;
        super.mapperExtension = ${classNameFirstLower}Mapper;
    }

    private ${className}Mapper ${classNameFirstLower}Mapper;

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
    @Transactional(readOnly = true)
    @Override
    public ${className}EntityExtension getByPk(<#include "/include/table/pk_params.ftl">) {
        ${className}EntityExtension vo = ${classNameFirstLower}Mapper.getByPk(<#include "/include/table/pk_values.ftl">);
        return vo;
    }
    </#if>
}
