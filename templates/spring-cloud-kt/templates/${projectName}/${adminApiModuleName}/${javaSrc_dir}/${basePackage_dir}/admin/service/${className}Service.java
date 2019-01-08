<#include "/include/table/properties.ftl">
package ${basePackage}.admin.service;

import ${baseCommonPackage}.model.PageInfoVo;
import ${baseCommonPackage}.model.ResponseVo;
import ${baseCommonPackage}.model.SearchPage;

import ${basePackage}.admin.vm.addoredit.${className}AddOrEditVm;
import ${basePackage}.admin.vm.detail.${className}DetailVm;
import ${basePackage}.admin.vm.search.${className}SearchVm;

import java.util.List;

<#include "/include/java_copyright.ftl">
public interface ${className}Service {

    /**
     * 添加${tableComment}
     *
     * @param vm
     * @return
     <#include "/include/author_info1.ftl">
     */
    ResponseVo<${className}AddOrEditVm> add(${className}AddOrEditVm vm);

    /**
     * 添加${tableComment}列表
     *
     * @param list
     * @return
     <#include "/include/author_info1.ftl">
     */
    ResponseVo<Integer> addList(List<${className}AddOrEditVm> list);

    /**
     * 根据条件删除${tableComment}
     *
     * @param condition
     * @return
     <#include "/include/author_info1.ftl">
     */
    ResponseVo<Integer> delete(${className}SearchVm condition);
    <#if table.validStatusColumn??>

    /**
     * 根据条件禁用${tableComment}
     *
     * @param condition
     * @return
     <#include "/include/author_info1.ftl">
     */
    ResponseVo<Integer> disable(${className}SearchVm condition);

    /**
     * 根据条件启用${tableComment}
     *
     * @param condition
     * @return
     <#include "/include/author_info1.ftl">
     */
    ResponseVo<Integer> enableList(${className}SearchVm condition);
    </#if>

    /**
     * 根据条件获取${tableComment}实体
     *
     * @param condition
     * @return
     <#include "/include/author_info1.ftl">
     */
    ResponseVo<${className}DetailVm> get(${className}SearchVm condition);
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

    /**
     * 获取${tableComment}列表
     *
     * @param condition
     * @return
     <#include "/include/author_info1.ftl">
     */
    ResponseVo<List<${className}DetailVm>> getList(${className}SearchVm condition);

    /**
     * 分页查询${tableComment}
     *
     * @param searchPage
     * @return
     <#include "/include/author_info1.ftl">
     */
    ResponseVo<PageInfoVo<${className}DetailVm>> getPageInfo(SearchPage<${className}SearchVm> searchPage);
}
