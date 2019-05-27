<#include "/include/table/properties.ftl">
package ${basePackage}.controller;

import ${baseCommonPackage}.model.PageInfoVo;
import ${baseCommonPackage}.model.ResponseVo;
import ${baseCommonPackage}.model.OrderSearch;
import ${baseCommonPackage}.model.OrderSearchPage;
import ${baseCommonPackage}.utils.VoUtils;
import ${baseCommonPackage}.utils.poi.ExcelUtils;
import ${baseCommonPackage}.validation.*;
import ${basePackage}.service.${className}Service;
import ${basePackage}.vm.addoredit.${className}AddOrEditVm;
import ${basePackage}.vm.detail.${className}DetailVm;
import ${basePackage}.vm.order.${className}OrderVm;
import ${basePackage}.vm.search.${className}SearchVm;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.annotation.Validated;

import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.*;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;

<#include "/include/java_copyright.ftl">
@CrossOrigin
@RequestMapping("/${tablePath}")
@RestController
@Validated
public class ${className}Controller {

    private final ${className}Service ${classNameFirstLower}Service;

    @Autowired
    public ${className}Controller(${className}Service ${classNameFirstLower}Service) {
        this.${classNameFirstLower}Service = ${classNameFirstLower}Service;
    }

    /**
     * 添加${tableComment}
     *
     * @param vm
     * @return
     <#include "/include/author_info1.ftl">
     */
    @PostMapping("/add")
    public ResponseVo<${className}AddOrEditVm> add(@Validated({DataAdd.class}) @RequestBody ${className}AddOrEditVm vm, Errors errors) {
        ${classNameFirstLower}Service.add(vm);
        return new ResponseVo<>(vm);
    }

    /**
     * 添加${tableComment}列表
     *
     * @param list
     * @return
     <#include "/include/author_info1.ftl">
     */
    @PostMapping("/add-list")
    public ResponseVo<Integer> addList(@Validated({DataAdd.class}) @RequestBody List<${className}AddOrEditVm> list, Errors errors) {
        int result = ${classNameFirstLower}Service.addList(list);
        return new ResponseVo<>(result);
    }

    /**
     * 根据条件删除${tableComment}
     *
     * @param searchVm
     * @return
     <#include "/include/author_info1.ftl">
     */
    @PostMapping("/delete")
    public ResponseVo<Integer> delete(@RequestBody ${className}SearchVm searchVm) {
        int result = ${classNameFirstLower}Service.delete(searchVm);
        return new ResponseVo<>(result);
    }
    <#if table.validStatusColumn??>

    /**
     * 根据条件禁用${tableComment}
     *
     * @param searchVm
     * @return
     <#include "/include/author_info1.ftl">
     */
    @PostMapping("/disable")
    public ResponseVo<Integer> disable(@RequestBody ${className}SearchVm searchVm) {
        int result = ${classNameFirstLower}Service.disable(searchVm);
        return new ResponseVo<>(result);
    }

    /**
     * 根据条件启用${tableComment}
     *
     * @param searchVm
     * @return
     <#include "/include/author_info1.ftl">
     */
    @PostMapping("/enable")
    public ResponseVo<Integer> enable(@RequestBody ${className}SearchVm searchVm) {
        int result = ${classNameFirstLower}Service.enable(searchVm);
        return new ResponseVo<>(result);
    }
    </#if>

    /**
     * 根据条件获取${tableComment}实体
     *
     * @param searchVm
     * @return
     <#include "/include/author_info1.ftl">
     */
    @PostMapping("/get")
    public ResponseVo<${className}DetailVm> get(@RequestBody ${className}SearchVm searchVm) {
        ${className}DetailVm result = ${classNameFirstLower}Service.get(searchVm);
        return new ResponseVo<>(result);
    }
    <#if table.hasPk>

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
    @PostMapping("/update")
    public ResponseVo<Integer> update(@Validated({DataEdit.class}) @RequestBody ${className}AddOrEditVm vm, Errors errors<#if !table.hasAutoIncUniPk><#list pks as column><#include "/include/column/properties.ftl"><#assign annotationName = (isString ? string('NotBlank', 'NotNull'))>, @${annotationName}(message = "${fieldName} (${columnComment})不能为空") @RequestParam(value = "${fieldName}") ${fieldType} ${fieldName}</#list></#if>) {
        int result = ${classNameFirstLower}Service.update(vm<#if !table.hasAutoIncUniPk>, <#include "/include/table/pk_values.ftl"></#if>);
        return new ResponseVo<>(result);
    }

    /**
     * 根据主键获取${tableComment}详情
     *
     <#if !table.hasAutoIncUniPk>
     <#list pks as column>
     <#include "/include/column/properties.ftl">
     * @param ${fieldName}
     </#list>
     </#if>
     * @return
     <#include "/include/author_info1.ftl">
     */
    @GetMapping("/detail")
    public ResponseVo<${className}DetailVm> getDetail(<#list pks as column><#include "/include/column/properties.ftl"><#assign annotationName = (isString ? string('NotBlank', 'NotNull'))>@${annotationName}(message = "${fieldName} (${columnComment})不能为空") @RequestParam(value = "${fieldName}") ${fieldType} ${fieldName}<#if column?has_next>,</#if></#list>) {
        ${className}DetailVm result = ${classNameFirstLower}Service.getDetail(<#include "/include/table/pk_values.ftl">);
        return new ResponseVo<>(result);
    }
    </#if>

    /**
     * 获取${tableComment}列表
     *
     * @param search
     * @return
     <#include "/include/author_info1.ftl">
     */
    @PostMapping("/list")
    public ResponseVo<List<${className}DetailVm>> getList(@RequestBody OrderSearch<${className}SearchVm, ${className}OrderVm> search) {
        List<${className}DetailVm> result = ${classNameFirstLower}Service.getList(search);
        return result;
    }

    /**
     * 分页查询${tableComment}
     *
     * @param searchPage
     * @return
     <#include "/include/author_info1.ftl">
     */
    @PostMapping("/page-list")
    public ResponseVo<PageInfoVo<${className}DetailVm>> getPageInfo(@RequestBody OrderSearchPage<${className}SearchVm, ${className}OrderVm> searchPage) {
        PageInfoVo<${className}DetailVm> result = ${classNameFirstLower}Service.getPageInfo(searchPage);
        return new ResponseVo<>(result);
    }

    /**
     * 导出Excel
     *
     * @param params
     * @return
     <#include "/include/author_info1.ftl">
     */
    @RequestMapping(value = {"/export"})
    public void export(HttpServletResponse response, String params, @RequestParam(defaultValue = "", required = false) String exportName) throws IOException, IllegalAccessException {

        OrderSearch<${className}SearchVm, ${className}OrderVm> search = VoUtils.getOrderSearch(params, ${className}SearchVm.class, ${className}OrderVm.class);
        List<${className}DetailVm> list = ${classNameFirstLower}Service.getList(search);

        String fileName = StringUtils.isBlank(exportName) ? "${tableComment}" : exportName;
        response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName + ".xlsx", "utf-8"));
        ExcelUtils.exportExcel(fileName, ${className}DetailVm.class, list, response.getOutputStream());
    }

}
