<#assign className = table.tableClassName>
<#assign classNameLower = table.tableClassNameFirstLower>
<#assign primaryKey = table.primaryKey>
<#assign primaryKeyParameters = table.primaryKeyParameters>
<#assign primaryKeyParameterValues = table.primaryKeyParameterValues>
package ${basePackage}.api.service.impl;

import ${resultFullClass};
import ${paginationFullClass};
import org.xi.common.utils.LogUtil;
import ${basePackage}.api.service.${className}Api;
import ${basePackage}.entity.${className}Entity;
import ${basePackage}.parameter.${className}SelectParameter;
import ${basePackage}.service.${className}Service;
import ${basePackage}.vo.${className}Vo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

<#include "/include/java_copyright.ftl">
@Service("${className}Api")
public class ${className}ApiImpl implements ${className}Api {

    private static LogUtil logger = LogUtil.build(${className}ApiImpl.class);

    @Autowired
    private ${className}Service ${classNameLower}Service;

    /**
     * 添加
     *
     * @param ${classNameLower}
     * @param sessionId
     * @return
     <#include "/include/author_info1.ftl">
     */
    @Override
    public ${resultClass}<${className}Entity> add${className}(${className}Entity ${classNameLower}, String sessionId) {
        return null;
    }

    /**
     * 添加列表
     *
     * @param ${classNameLower}List
     * @param sessionId
     * @return
    <#include "/include/author_info1.ftl">
     */
    @Override
    public ${resultClass}<${className}Entity> add${className}(List<${className}Entity> ${classNameLower}List, String sessionId) {
        return null;
    }

    /**
     * 分页查询
     *
     * @param parameter
     * @param pagination
     * @param sessionId
     * @return
     <#include "/include/author_info1.ftl">
     */
    @Override
    public ${resultClass}<${paginationClass}<${className}Vo>> find${className}PageList(${className}SelectParameter parameter, ${paginationClass} pagination, String sessionId) {
        return null;
    }

}
