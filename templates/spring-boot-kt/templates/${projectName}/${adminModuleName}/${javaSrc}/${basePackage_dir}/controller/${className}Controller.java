<#include "/include/table/properties.ftl">
package ${basePackage}.controller;

import ${basePackage}.common.model.PageInfoVo;
import ${basePackage}.common.model.ResponseVo;
import ${basePackage}.common.model.OrderSearch;
import ${basePackage}.common.model.OrderSearchPage;
import ${basePackage}.common.utils.poi.ExcelUtils;
import ${basePackage}.common.validation.*;
import ${basePackage}.models.condition.${className}Condition;
import ${basePackage}.models.condition.extension.${className}ConditionExtension;
import ${basePackage}.models.condition.order.${className}OrderCondition;
import ${basePackage}.models.entity.${className}Entity;
import ${basePackage}.models.entity.extension.${className}EntityExtension;
import ${basePackage}.service.${className}Service;
import ${basePackage}.utils.VoUtils;
import ${basePackage}.vm.addoredit.${className}AddOrEditVm;
import ${basePackage}.vm.detail.${className}DetailVm;
import ${basePackage}.vm.order.${className}OrderVm;
import ${basePackage}.vm.search.${className}SearchVm;

import com.github.pagehelper.PageInfo;

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
import java.util.stream.Collectors;

<#include "/include/java_copyright.ftl">
@CrossOrigin
@RequestMapping("/${classNameFirstLower}")
@RestController
@Validated
public class ${className}Controller {

    @Autowired
    private ${className}Service ${classNameFirstLower}Service;

    /**
     * 添加${tableComment}
     *
     * @param vm
     * @return
     <#include "/include/author_info1.ftl">
     */
    @PostMapping("/add")
    public ResponseVo<${className}AddOrEditVm> add(@Validated({DataAdd.class}) @RequestBody ${className}AddOrEditVm vm, Errors errors) {

        ${className}Entity entity = vm.get${className}Entity();
        int count = ${classNameFirstLower}Service.insert(entity);
        vm.set${className}Entity(entity);
        ResponseVo<${className}AddOrEditVm> result = new ResponseVo<>(vm);
        return result;
    }

    /**
     * 添加${tableComment}列表
     *
     * @param list
     * @return
     <#include "/include/author_info1.ftl">
     */
    @PostMapping("/addList")
    public ResponseVo<Integer> addList(@Validated({DataAdd.class}) @RequestBody List<${className}AddOrEditVm> list, Errors errors) {

        List<${className}Entity> entityList = list.stream().map(o -> o.get${className}Entity()).collect(Collectors.toList());
        int count = ${classNameFirstLower}Service.insert(entityList);
        ResponseVo<Integer> result = new ResponseVo<>(count);
        return result;
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

        ${className}Condition condition = searchVm.getCondition();
        int count = ${classNameFirstLower}Service.delete(condition);
        ResponseVo<Integer> result = new ResponseVo<>(count);
        return result;
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

        ${className}Condition condition = searchVm.getCondition();
        ${className}Entity entity = new ${className}Entity();
        entity.set${table.validStatusColumn.targetName}(${table.validStatusColumn.validStatusOption.invalid});
        int count = ${classNameFirstLower}Service.update(entity, condition);
        ResponseVo<Integer> result = new ResponseVo<>(count);
        return result;
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

        ${className}Condition condition = searchVm.getCondition();
        ${className}Entity entity = new ${className}Entity();
        entity.set${table.validStatusColumn.targetName}(${table.validStatusColumn.validStatusOption.valid});
        int count = ${classNameFirstLower}Service.update(entity, condition);
        ResponseVo<Integer> result = new ResponseVo<>(count);
        return result;
    }
    </#if>

    /**
     * 根据主键获取${tableComment}实体
     *
     * @param searchVm
     * @return
     <#include "/include/author_info1.ftl">
     */
    @PostMapping("/get")
    public ResponseVo<${className}DetailVm> get(@RequestBody ${className}SearchVm searchVm) {

        ${className}Condition condition = searchVm.getCondition();
        ${className}Entity entity = ${classNameFirstLower}Service.get(condition);
        ${className}DetailVm vm = new ${className}DetailVm(entity);
        ResponseVo<${className}DetailVm> result = new ResponseVo<>(vm);
        return result;
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

        ${className}Entity entity = vm.get${className}Entity();
        ${className}Condition condition = new ${className}Condition();
        <#list pks as column>
        <#include "/include/column/properties.ftl">
        condition.set${propertyName}(<#if table.hasAutoIncUniPk>entity.get${propertyName}()<#else>${fieldName}</#if>);
        </#list>

        int count = ${classNameFirstLower}Service.update(entity, condition);
        ResponseVo<Integer> result = new ResponseVo<>(count);
        return result;
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
    @GetMapping("/getDetail")
    public ResponseVo<${className}DetailVm> getDetail(<#list pks as column><#include "/include/column/properties.ftl"><#assign annotationName = (isString ? string('NotBlank', 'NotNull'))>@${annotationName}(message = "${fieldName} (${columnComment})不能为空") @RequestParam(value = "${fieldName}") ${fieldType} ${fieldName}<#if column?has_next>,</#if></#list>) {

        ${className}EntityExtension entity = ${classNameFirstLower}Service.getByPk(<#include "/include/table/pk_values.ftl">);
        ${className}DetailVm vm = new ${className}DetailVm(entity);
        ResponseVo<${className}DetailVm> result = new ResponseVo<>(vm);
        return result;
    }
    </#if>

    /**
     * 获取${tableComment}列表
     *
     * @param search
     * @return
     <#include "/include/author_info1.ftl">
     */
    @PostMapping("/getList")
    public ResponseVo<List<${className}DetailVm>> getList(@RequestBody OrderSearch<${className}SearchVm, ${className}OrderVm> search) {

        List<${className}DetailVm> vmList = getVmList(search);
        ResponseVo<List<${className}DetailVm>> result = new ResponseVo<>(vmList);
        return result;
    }

    /**
     * 分页查询${tableComment}
     *
     * @param searchPage
     * @return
     <#include "/include/author_info1.ftl">
     */
    @PostMapping("/getPageInfo")
    public ResponseVo<PageInfoVo<${className}DetailVm>> getPageInfo(@RequestBody OrderSearchPage<${className}SearchVm, ${className}OrderVm> searchPage) {

        ${className}SearchVm searchVm = searchPage.getCondition();
        ${className}OrderVm orderVm = searchPage.getOrder();
        ${className}ConditionExtension condition = searchVm.getConditionExtension();
        ${className}OrderCondition orderCondition = orderVm.getOrderCondition();
        OrderSearchPage<${className}ConditionExtension, ${className}OrderCondition> orderSearchPage =
                new OrderSearchPage<>(searchPage.getPageIndex(), searchPage.getPageSize(), condition, orderCondition);

        PageInfo<${className}EntityExtension> pageInfo = ${classNameFirstLower}Service.getPageList(orderSearchPage);
        PageInfoVo<${className}DetailVm> pageInfoVo = VoUtils.getPageInfoVo(pageInfo, entity -> new ${className}DetailVm(entity));
        ResponseVo<PageInfoVo<${className}DetailVm>> result = new ResponseVo<>(pageInfoVo);
        return result;
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
        List<${className}DetailVm> vmList = getVmList(search);

        String fileName = StringUtils.isBlank(exportName) ? "${tableComment}" : exportName;
        response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName + ".xlsx", "utf-8"));
        ExcelUtils.exportExcel(fileName, ${className}DetailVm.class, vmList, response.getOutputStream());
    }

    private List<${className}DetailVm> getVmList(OrderSearch<${className}SearchVm, ${className}OrderVm> search) {

        ${className}SearchVm searchVm = search.getCondition();
        ${className}OrderVm orderVm = search.getOrder();
        ${className}ConditionExtension condition = searchVm.getConditionExtension();
        ${className}OrderCondition orderCondition = orderVm.getOrderCondition();
        OrderSearch<${className}ConditionExtension, ${className}OrderCondition> orderSearch = new OrderSearch<>(condition, orderCondition);

        List<${className}EntityExtension> list = ${classNameFirstLower}Service.getExList(orderSearch);
        List<${className}DetailVm> vmList = list.stream().map(o -> new ${className}DetailVm(o)).collect(Collectors.toList());
        return vmList;
    }
}
