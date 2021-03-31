<#include "/include/table/properties.ftl">
package ${modulePackage}.controller;

import ${commonPackage}.annotation.SetFieldTypes;
import ${commonPackage}.models.PageList;
import ${commonPackage}.utils.ExcelUtils;
import ${modulePackage}.model.request.${className}AddRequest;
import ${modulePackage}.model.request.${className}QueryRequest;
import ${modulePackage}.model.request.${className}SaveRequest;
import ${modulePackage}.model.response.${className}DetailResponse;
import ${modulePackage}.model.response.${className}ListItemResponse;
import ${modulePackage}.service.${className}Service;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.*;
import java.io.IOException;
import java.net.URI;
import java.util.Collection;
import java.util.List;

@Api(tags = {"${tableComment}相关接口"})
@CrossOrigin
@RequestMapping("/${tablePath}")
@RestController
@Validated
public class ${className}Controller extends BaseController {

    private final ${className}Service ${classNameFirstLower}Service;

    @Autowired
    public ${className}Controller(${className}Service ${classNameFirstLower}Service) {
        this.${classNameFirstLower}Service = ${classNameFirstLower}Service;
    }

    @ApiOperation(value = "添加${tableComment}", notes = "添加${tableComment}")
    @ApiImplicitParam(name = "${classNameFirstLower}", value = "添加${tableComment}请求实体", required = true, dataType = "${className}AddRequest", paramType = "body")
    @PostMapping("/add")
    @RequiresPermissions("${module}:${tableShortPath}:add")
    public ResponseEntity<${className}DetailResponse> add(@Validated @RequestBody @SetFieldTypes(types = {"create"}) ${className}AddRequest ${classNameFirstLower}) {
        ${className}DetailResponse detail = ${classNameFirstLower}Service.add(${classNameFirstLower});
        return ResponseEntity.created(URI.create("")).body(detail);
    }
    <#-- region 删除/启用/禁用 -->
    <#if (table.hasUniPk)>

    @ApiOperation(value = "根据${uniPkComment}删除${tableComment}", notes = "根据${uniPkComment}删除${tableComment}")
    @ApiImplicitParam(name = "${uniPkFieldName}s", value = "${uniPkComment}，多个用英文逗号分开", required = true, dataType = "${uniPkFieldBasicType}", paramType = "query")
    @DeleteMapping("/delete")
    @RequiresPermissions("${module}:${tableShortPath}:del")
    public ResponseEntity<Integer> deleteBy${uniPkPropertyName}s(@RequestParam("${uniPkFieldName}s") @NotNull(message = "${uniPkComment}不能为空") Collection<${uniPkFieldType}> ${uniPkFieldName}s) {
        Integer count = ${classNameFirstLower}Service.deleteBy${uniPkPropertyName}List(${uniPkFieldName}s);
        return ResponseEntity.ok(count);
    }
    <#else>

    @ApiOperation(value = "根据<#include "/include/table/pk_fun_comment.ftl">删除${tableComment}", notes = "根据<#include "/include/table/pk_fun_comment.ftl">删除${tableComment}")
    @ApiImplicitParams({
            <#list pks as column>
            <#include "/include/column/properties.ftl">
            @ApiImplicitParam(name = "${fieldName}", value = "${columnFullComment}", required = true, dataType = "${fieldBasicType}", paramType = "query")<#if (column?has_next)>,</#if>
            </#list>
    })
    @DeleteMapping("/delete")
    @RequiresPermissions("${module}:${tableShortPath}:del")
    public ResponseEntity<Integer> deleteBy<#include "/include/table/pk_fun_names.ftl">(
            <#list pks as column>
            <#include "/include/column/properties.ftl">
            @RequestParam("${fieldName}") @${isString ? string('NotBlank','NotNull')}(message = "${fieldName}(${columnComment})不能为空") ${fieldType} ${fieldName}<#if (column?has_next)>,</#if>
            </#list>
    ) {
        Integer count = ${classNameFirstLower}Service.deleteBy<#include "/include/table/pk_fun_names.ftl">(<#include "/include/table/pk_values.ftl">);
        return ResponseEntity.ok(count);
    }
    </#if>
    <#-- endregion 删除/启用/禁用 -->

    <#-- region 更新 -->
    <#if (table.hasAutoIncUniPk)>

    @ApiOperation(value = "根据<#include "/include/table/pk_fun_comment.ftl">更新${tableComment}", notes = "根据<#include "/include/table/pk_fun_comment.ftl">更新${tableComment}")
    @ApiImplicitParam(name = "${classNameFirstLower}", value = "更新${tableComment}请求实体", required = true, dataType = "${className}SaveRequest", paramType = "body")
    @PatchMapping("/update")
    @RequiresPermissions("${module}:${tableShortPath}:update")
    public ResponseEntity<${className}DetailResponse> updateBy<#include "/include/table/pk_fun_names.ftl">(@Validated @RequestBody @SetFieldTypes(types = {"update"}) ${className}SaveRequest ${classNameFirstLower}) {
        ${className}DetailResponse detail = ${classNameFirstLower}Service.updateBy<#include "/include/table/pk_fun_names.ftl">(${classNameFirstLower});
        return ResponseEntity.ok(detail);
    }
    <#else>

    @ApiOperation(value = "根据<#include "/include/table/pk_fun_comment.ftl">更新${tableComment}", notes = "根据<#include "/include/table/pk_fun_comment.ftl">更新${tableComment}")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "${classNameFirstLower}", value = "更新${tableComment}请求实体", required = true, dataType = "${className}SaveRequest", paramType = "body"),
            <#list pks as column>
            <#include "/include/column/properties.ftl">
            @ApiImplicitParam(name = "${fieldName}", value = "${columnFullComment}", required = true, dataType = "${fieldBasicType}", paramType = "query")<#if (column?has_next)>,</#if>
            </#list>
    })
    @PatchMapping("/update")
    @RequiresPermissions("${module}:${tableShortPath}:update")
    public ResponseEntity<${className}DetailResponse> updateBy<#include "/include/table/pk_fun_names.ftl">(
            @Validated @RequestBody @SetFieldTypes(types = {"update"}) ${className}SaveRequest ${classNameFirstLower},
            <#list pks as column>
            <#include "/include/column/properties.ftl">
            @RequestParam("${fieldName}") @${isString ? string('NotBlank','NotNull')}(message = "${fieldName}(${columnComment})不能为空") ${fieldType} ${fieldName}<#if (column?has_next)>,</#if>
            </#list>
    ) {
        ${className}DetailResponse detail = ${classNameFirstLower}Service.updateBy<#include "/include/table/pk_fun_names.ftl">(${classNameFirstLower}, <#include "/include/table/pk_values.ftl">);
        return ResponseEntity.ok(detail);
    }
    </#if>
    <#-- endregion 更新 -->

    <#-- region 详情 -->
    <#if (table.hasUniPk)>

    @ApiOperation(value = "根据<#include "/include/table/pk_fun_comment.ftl">获取${tableComment}详情", notes = "根据<#include "/include/table/pk_fun_comment.ftl">获取${tableComment}详情")
    @ApiImplicitParam(name = "${uniPkFieldName}", value = "${uniPkFullComment}", required = true, dataType = "${fieldBasicType}", paramType = "query")
    @GetMapping("/detail")
    @RequiresPermissions("${module}:${tableShortPath}:view")
    public ResponseEntity<${className}DetailResponse> getBy<#include "/include/table/pk_fun_names.ftl">(@RequestParam("${uniPkFieldName}") <#if uniPkIsString>@NotBlank(message = "${uniPkComment}不能为空")<#else>@NotNull(message = "${uniPkComment}不能为空") @Min(value = 1, message = "${uniPkComment}必须大于0")</#if> ${uniPkFieldType} ${uniPkFieldName}) {
        ${className}DetailResponse detail = ${classNameFirstLower}Service.getBy<#include "/include/table/pk_fun_names.ftl">(${uniPkFieldName});
        return ResponseEntity.ok(detail);
    }
    <#else>

    @ApiOperation(value = "根据<#include "/include/table/pk_fun_comment.ftl">获取${tableComment}详情", notes = "根据<#include "/include/table/pk_fun_comment.ftl">获取${tableComment}详情")
    @ApiImplicitParams({
            <#list pks as column>
            <#include "/include/column/properties.ftl">
            @ApiImplicitParam(name = "${fieldName}", value = "${columnFullComment}", required = true, dataType = "${fieldBasicType}", paramType = "query")<#if (column?has_next)>,</#if>
            </#list>
    })
    @GetMapping("/detail")
    @RequiresPermissions("${module}:${tableShortPath}:view")
    public ResponseEntity<${className}DetailResponse> getBy<#include "/include/table/pk_fun_names.ftl">(
            <#list pks as column>
            <#include "/include/column/properties.ftl">
            @RequestParam("${fieldName}") @${isString ? string('NotBlank','NotNull')}(message = "${fieldName}(${columnComment})不能为空") ${fieldType} ${fieldName}<#if (column?has_next)>,</#if>
            </#list>
    ) {
        ${className}DetailResponse detail = ${classNameFirstLower}Service.getBy<#include "/include/table/pk_fun_names.ftl">(<#include "/include/table/pk_values.ftl">);
        return ResponseEntity.ok(detail);
    }
    </#if>
    <#-- endregion 详情 -->

    @ApiOperation(value = "根据条件获取${tableComment}列表", notes = "根据条件获取${tableComment}列表")
    @GetMapping("/list")
    @RequiresPermissions("${module}:${tableShortPath}:view")
    public ResponseEntity<List<${className}ListItemResponse>> getList(${className}QueryRequest queryRequest) {
        return ResponseEntity.ok(${classNameFirstLower}Service.getList(queryRequest));
    }

    @ApiOperation(value = "根据条件分页获取${tableComment}列表", notes = "根据条件分页获取${tableComment}列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", value = "页码", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "pageSize", value = "分页大小", dataType = "int", paramType = "query")
    })
    @GetMapping("/page-list")
    @RequiresPermissions("${module}:${tableShortPath}:view")
    public ResponseEntity<PageList<${className}ListItemResponse>> getPageList(
            ${className}QueryRequest queryRequest,
            @RequestParam(value = "pageNum", defaultValue = "1") @Min(value = 1, message = "页码必须大于0") Integer pageNum,
            @RequestParam(value = "pageSize", defaultValue = "50") @Min(value = 1, message = "分页大小必须大于0") Integer pageSize
    ) {
        return ResponseEntity.ok(${classNameFirstLower}Service.getPageList(queryRequest, pageNum, pageSize));
    }

    @ApiOperation(value = "根据条件导出${tableComment}列表", notes = "根据条件导出${tableComment}列表")
    @ApiImplicitParam(name = "exportName", value = "导出文件名", dataType = "String", paramType = "query")
    @GetMapping("/export")
    @RequiresPermissions("${module}:${tableShortPath}:export")
    public ResponseEntity<?> export(HttpServletResponse response, ${className}QueryRequest queryRequest,
                                    @RequestParam(value = "exportName", defaultValue = "${tableComment}", required = false) String exportName) throws IOException {

        ExcelUtils.export(response, ${classNameFirstLower}Service.getList(queryRequest), ${className}ListItemResponse.class, exportName, "${tableComment}");
        return null;
    }
}
