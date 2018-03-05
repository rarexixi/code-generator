<#assign className = table.tableClassName>
<#assign classNameLower = table.tableClassName?uncap_first>
<#assign primaryKey = table.primaryKey>
<#assign primaryKeyParameters = table.primaryKeyParameters>
<#assign primaryKeyParameterValues = table.primaryKeyParameterValues>
package ${basePackage}.admin.service.impl;

import ${baseCommonPackage}.model.Result;
import ${baseCommonPackage}.model.SearchPage;
import ${basePackage}.admin.service.${className}Service;
import ${basePackage}.api.service.${className}Api;
import ${basePackage}.entity.${className}Entity;
import ${basePackage}.parameter.${className}SelectParameter;
import ${basePackage}.vo.${className}Vo;

import com.github.pagehelper.PageInfo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

<#include "/include/java_copyright.ftl">
@Service("${classNameLower}Service")
public class ${className}ServiceImpl implements ${className}Service {

    @Value("${r'${spring.application.name}'}")
    private String sessionId;

    @Autowired
    private ${className}Api ${classNameLower}Api;

    /**
     * 添加
     *
     * @param entity
     * @return
     <#include "/include/author_info1.ftl">
     */
    @Override
    public Result<Integer> add(${className}Entity entity) {
        return ${classNameLower}Api.add(entity, sessionId);
    }

    /**
     * 添加列表
     *
     * @param list
     * @return
    <#include "/include/author_info1.ftl">
     */
    @Override
    public Result<Integer> addList(List<${className}Entity> list) {
        return ${classNameLower}Api.addList(list, sessionId);
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
    @Override
    public Result<Integer> deleteByPk(${primaryKeyParameters}) {
        return ${classNameLower}Api.deleteByPk(${primaryKeyParameterValues}, sessionId);
    }
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
    @Override
    public Result<Integer> disableByPk(${primaryKeyParameters}) {
        return ${classNameLower}Api.disableByPk(${primaryKeyParameterValues}, sessionId);
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
    @Override
    public Result<Integer> enableByPk(${primaryKeyParameters}) {
        return ${classNameLower}Api.enableByPk(${primaryKeyParameterValues}, sessionId);
    }
    </#if>

    /**
     * 根据主键更新
     *
     * @param entity
     * @return
     <#include "/include/author_info1.ftl">
     */
    @Override
    public Result<Integer> updateByPk(${className}Entity entity<#if !table.hasAutoIncrementUniquePrimaryKey><#list primaryKey as column>, ${column.columnFieldType} old${column.columnFieldName}</#list></#if>) {
        return ${classNameLower}Api.updateByPk(entity<#if !table.hasAutoIncrementUniquePrimaryKey><#list primaryKey as column>, old${column.columnFieldName}</#list></#if>, sessionId);
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
    @Override
    public Result<${className}Vo> getByPk(${primaryKeyParameters}) {
        return ${classNameLower}Api.getByPk(${primaryKeyParameterValues}, sessionId);
    }
    </#if>

    /**
     * 分页查询
     *
     * @param parameter
     * @return
     <#include "/include/author_info1.ftl">
     */
    @Override
    public Result<PageInfo<${className}Vo>> findPageList(${className}SelectParameter parameter) {
        return ${classNameLower}Api.findPageList(parameter, sessionId);
    }
}
