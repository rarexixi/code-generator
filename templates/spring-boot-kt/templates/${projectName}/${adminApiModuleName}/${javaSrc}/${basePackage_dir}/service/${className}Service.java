<#include "/include/table/properties.ftl">
package ${basePackage}.service;

import ${baseCommonPackage}.model.ResponseVo;

import ${basePackage}.vm.addoredit.${className}AddOrEditVm;
import ${basePackage}.vm.detail.${className}DetailVm;
import ${basePackage}.vm.order.${className}OrderVm;
import ${basePackage}.vm.search.${className}SearchVm;

<#include "/include/java_copyright.ftl">
public interface ${className}Service extends BaseService<${className}AddOrEditVm, ${className}DetailVm, ${className}OrderVm, ${className}SearchVm> {
    <#if table.validStatusColumn??>

    /**
     * 根据条件禁用${tableComment}
     *
     * @param searchVm
     * @return
     <#include "/include/author_info1.ftl">
     */
    ResponseVo<Integer> disable(${className}SearchVm searchVm);

    /**
     * 根据条件启用${tableComment}
     *
     * @param searchVm
     * @return
     <#include "/include/author_info1.ftl">
     */
    ResponseVo<Integer> enable(${className}SearchVm searchVm);
    </#if>
    <#if (table.hasPk)>

    /**
     * 根据主键更新${tableComment}
     *
     * @param vm
     <#if !table.hasAutoIncUniPk>
     <#list pks as column>
     <#include "/include/column/properties.ftl">
     * @param ${fieldName}
     </#list>
     </#if>
     * @return
     <#include "/include/author_info1.ftl">
     */
    ResponseVo<Integer> update(${className}AddOrEditVm vm<#if !table.hasAutoIncUniPk>, <#include "/include/table/pk_params.ftl"></#if>);

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
    ResponseVo<${className}DetailVm> getDetail(<#include "/include/table/pk_params.ftl">);
    </#if>
}
