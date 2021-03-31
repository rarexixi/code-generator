<#include "/include/table/properties.ftl">
package ${modulePackage}.presentation.mapper;

import ${modulePackage}.presentation.condition.${className}SelectCondition;
import ${modulePackage}.presentation.condition.${className}UpdateCondition;
import ${modulePackage}.presentation.entity.${className}Entity;
import ${modulePackage}.presentation.entity.${className}EntityExt;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Collection;

/**
 * ${table.comment}数据访问
 *
 * @author ${author}
 */
@Mapper
public interface ${className}Mapper extends BaseMapper<${className}Entity, ${className}EntityExt, ${className}UpdateCondition, ${className}SelectCondition> {
    <#if table.hasPk>

    /**
     * 根据<#include "/include/table/pk_fun_comment.ftl">获取${tableComment}详情
     *
     <#list pks as column>
     <#include "/include/column/properties.ftl">
     * @param ${fieldName} ${columnFullComment}
     </#list>
     * @return ${tableComment}详情
     * @author ${author}
     */
    ${className}EntityExt detail(<#include "/include/table/pk_params_mapper.ftl">);
    </#if>

    /**
     * 批量添加${tableComment}
     *
     * @param list ${tableComment}列表
     * @return 受影响的行数
     * @author ${author}
     */
    int batchInsert(@Param("list") Collection<${className}Entity> list);
}
