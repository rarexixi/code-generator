<#assign className = table.tableClassName>
<#assign classNameLower = table.tableClassName?uncap_first>
<#assign primaryKey = table.primaryKey>
<#assign primaryKeyParameters = table.primaryKeyParameters>
<#assign primaryKeyParameterValues = table.primaryKeyParameterValues>
package ${basePackage}.admin.controller;

import ${baseCommonPackage}.model.ResponseVo;
import ${baseCommonPackage}.utils.LogUtil;
import ${basePackage}.admin.service.${className}Service;
import ${basePackage}.admin.vm.addoredit.${className}AddOrEditVm;
import ${basePackage}.admin.vm.detail.${className}DetailVm;
import ${basePackage}.admin.vm.search.${className}SearchVm;
import ${basePackage}.vo.${className}Vo;

import com.github.pagehelper.PageInfo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

<#include "/include/java_copyright.ftl">
@RestController
@RequestMapping("/${table.tableClassName?lower_case}")
public class ${className}Controller {

    private static LogUtil logger = LogUtil.build(${className}Controller.class);

    @Autowired
    private ${className}Service ${classNameLower}Service;

    /**
     * 添加列表
     *
     * @param list
     * @return
    <#include "/include/author_info1.ftl">
     */
    @RequestMapping(value = "/addList", method = RequestMethod.POST)
    public ResponseVo<Integer> addList(@RequestBody List<${className}AddOrEditVm> list) {

        ResponseVo<Integer> result = ${classNameLower}Service.addList(list);
        return result;
    }
    <#if table.hasPrimaryKey>

    /**
     * 根据主键物理删除
     *
     <#list primaryKey as column>
     * @param ${column.columnFieldNameFirstLower}
     </#list>
     * @return
     <#include "/include/author_info1.ftl">
     */
     @RequestMapping(value = { "/delete" }, method = RequestMethod.GET)
     public ResponseVo<Integer> delete(<#list primaryKey as column><#if (column_index > 0)>, </#if>@RequestParam("${column.columnFieldNameFirstLower}") ${column.columnFieldType} ${column.columnFieldNameFirstLower}</#list>) {

        ResponseVo<Integer> result = ${classNameLower}Service.deleteByPk(${primaryKeyParameterValues});
        return result;
    }
    <#if (table.uniquePrimaryKey??)>

    /**
     * 根据主键列表物理删除
     *
     * @param ${table.uniquePrimaryKey.columnFieldName?uncap_first}List
     * @return
     <#include "/include/author_info1.ftl">
     */
     @RequestMapping(value = { "/deletelist" }, method = RequestMethod.POST)
     public ResponseVo<Integer> deleteList(@RequestBody List<${table.uniquePrimaryKey.columnFieldType}> ${table.uniquePrimaryKey.columnFieldName?uncap_first}List) {

        ResponseVo<Integer> result = ${classNameLower}Service.deleteByPkList(${table.uniquePrimaryKey.columnFieldName?uncap_first}List);
        return result;
    }
    </#if>
    <#if table.validStatusColumn??>

    /**
     * 根据主键冻结
     *
     <#list primaryKey as column>
     * @param ${column.columnFieldNameFirstLower}
     </#list>
     * @return
     <#include "/include/author_info1.ftl">
     */
    @RequestMapping(value = "/disable", method = RequestMethod.GET)
    public ResponseVo<Integer> disable(${primaryKeyParameters}) {

        ResponseVo<Integer> result = ${classNameLower}Service.disableByPk(${primaryKeyParameterValues});
        return result;
    }

    /**
     * 根据主键激活
     *
     <#list primaryKey as column>
     * @param ${column.columnFieldNameFirstLower}
     </#list>
     * @return
     <#include "/include/author_info1.ftl">
     */
    @RequestMapping(value = "/enable", method = RequestMethod.GET)
    public ResponseVo<Integer> enable(${primaryKeyParameters}) {

        ResponseVo<Integer> result = ${classNameLower}Service.enableByPk(${primaryKeyParameterValues});
        return result;
    }
    <#if (table.uniquePrimaryKey??)>

    /**
     * 根据主键列表冻结
     *
     * @param ${table.uniquePrimaryKey.columnFieldName?uncap_first}List
     * @return
     <#include "/include/author_info1.ftl">
     */
    @RequestMapping(value = "/disablelist", method = RequestMethod.POST)
    public ResponseVo<Integer> disableList(@RequestBody List<${table.uniquePrimaryKey.columnFieldType}> ${table.uniquePrimaryKey.columnFieldName?uncap_first}List) {

        ResponseVo<Integer> result = ${classNameLower}Service.disableByPkList(${table.uniquePrimaryKey.columnFieldName?uncap_first}List);
        return result;
    }

    /**
     * 根据主键列表激活
     *
     * @param ${table.uniquePrimaryKey.columnFieldName?uncap_first}List
     * @return
     <#include "/include/author_info1.ftl">
     */
    @RequestMapping(value = "/enablelist", method = RequestMethod.POST)
    public ResponseVo<Integer> enableList(@RequestBody List<${table.uniquePrimaryKey.columnFieldType}> ${table.uniquePrimaryKey.columnFieldName?uncap_first}List) {

        ResponseVo<Integer> result = ${classNameLower}Service.enableByPkList(${table.uniquePrimaryKey.columnFieldName?uncap_first}List);
        return result;
    }
    </#if>
    </#if>

    /**
     * 保存
     *
     * @param vm
     * @return
     <#include "/include/author_info1.ftl">
     */
    @RequestMapping(value = { "/save" }, method = RequestMethod.POST)
    public ResponseVo<Integer> save(${className}AddOrEditVm vm<#if !table.hasAutoIncrementUniquePrimaryKey><#list primaryKey as column>, @RequestParam(value = "old${column.columnFieldName}", required = false) ${column.columnFieldType} old${column.columnFieldName}</#list></#if>) {

        ResponseVo<Integer> result;
        if (<#list primaryKey as column><#if (column_index > 0)> || </#if>vm.get${column.columnFieldName}() == null</#list>) {
            result = ${classNameLower}Service.add(vm);
        } else {
            result = ${classNameLower}Service.updateByPk(vm<#if !table.hasAutoIncrementUniquePrimaryKey>, ${table.primaryKeyOldParameterValues}</#if>);
        }

        return result;
    }

    /**
     * 根据主键获取
     *
     <#list primaryKey as column>
     * @param ${column.columnFieldNameFirstLower}
     </#list>
     * @return
     <#include "/include/author_info1.ftl">
     */
    @RequestMapping(value = { "/getdetail" }, method = RequestMethod.GET)
    public ResponseVo<${className}DetailVm> getDetail(<#list primaryKey as column><#if (column_index > 0)>, </#if>@RequestParam("${column.columnFieldNameFirstLower}") ${column.columnFieldType} ${column.columnFieldNameFirstLower}</#list>) {

        ResponseVo<${className}DetailVm> result = ${classNameLower}Service.getByPk(${primaryKeyParameterValues});
        return result;
    }
    </#if>

    /**
     * 分页查询
     *
     * @param searchVm
     * @return
     <#include "/include/author_info1.ftl">
     */
    @RequestMapping(value = { "/find" }, method = RequestMethod.GET)
    public ResponseVo<PageInfo<${className}Vo>> find(${className}SearchVm searchVm) {

        ResponseVo<PageInfo<${className}Vo>> result = ${classNameLower}Service.findPageList(searchVm);
        return result;
    }

}
