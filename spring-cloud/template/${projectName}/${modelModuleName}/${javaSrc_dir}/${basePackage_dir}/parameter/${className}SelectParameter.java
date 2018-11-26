<#include "/include/table/properties.ftl">
package ${basePackage}.parameter;

import ${basePackage}.condition.order.BaseOrderCondition;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.*;

<#include "/include/java_copyright.ftl">
public class ${className}SelectParameter extends BaseSelectParameter {
<#list table.columns as column>
<#if column.ignoreSearch>
<#else>

    //region ${column.columnComment}
    <#if column.columnName == table.validStatusField.fieldName>

    /**
     * ${column.columnComment}
     */
    private ${column.columnFieldType} ${column.columnFieldNameFirstLower};

    public void set${column.columnFieldName}(${column.columnFieldType} ${column.columnFieldNameFirstLower}) {
        this.${column.columnFieldNameFirstLower} = ${column.columnFieldNameFirstLower};
    }

    public ${column.columnFieldType} get${column.columnFieldName}() {
        return ${column.columnFieldNameFirstLower};
    }
    <#elseif (column.columnFieldType == "Integer" || column.columnFieldType == "Long" || column.columnFieldType == "Short" || column.columnFieldType == "Byte")>

    /**
     * ${column.columnComment}
     */
    private ${column.columnFieldType} ${column.columnFieldNameFirstLower};

    /**
     * ${column.columnComment} 列表
     */
    private List<${column.columnFieldType}> ${column.columnFieldNameFirstLower}List;

    /**
     * 最小 ${column.columnComment}
     */
    private ${column.columnFieldType} ${column.columnFieldNameFirstLower}Min;

    /**
     * 最大 ${column.columnComment}
     */
    private ${column.columnFieldType} ${column.columnFieldNameFirstLower}Max;

    public void set${column.columnFieldName}(${column.columnFieldType} ${column.columnFieldNameFirstLower}) {
        this.${column.columnFieldNameFirstLower} = ${column.columnFieldNameFirstLower};
    }

    public ${column.columnFieldType} get${column.columnFieldName}() {
        return ${column.columnFieldNameFirstLower};
    }

    public void set${column.columnFieldName}List(List<${column.columnFieldType}> ${column.columnFieldNameFirstLower}List) {
        this.${column.columnFieldNameFirstLower}List = ${column.columnFieldNameFirstLower}List;
    }

    public List<${column.columnFieldType}> get${column.columnFieldName}List() {
        return ${column.columnFieldNameFirstLower}List;
    }

    public void set${column.columnFieldName}In(${column.columnFieldType}... ${column.columnFieldNameFirstLower}List) {
        if (this.${column.columnFieldNameFirstLower}List == null) {
            this.${column.columnFieldNameFirstLower}List = new ArrayList<${column.columnFieldType}>();
        }
        for (${column.columnFieldType} ${column.columnFieldNameFirstLower} : ${column.columnFieldNameFirstLower}List) {
            this.${column.columnFieldNameFirstLower}List.add(${column.columnFieldNameFirstLower});
        }
    }

    public void set${column.columnFieldName}Min(${column.columnFieldType} ${column.columnFieldNameFirstLower}Min) {
        this.${column.columnFieldNameFirstLower}Min = ${column.columnFieldNameFirstLower}Min;
    }

    public ${column.columnFieldType} get${column.columnFieldName}Min() {
        return ${column.columnFieldNameFirstLower}Min;
    }

    public void set${column.columnFieldName}Max(${column.columnFieldType} ${column.columnFieldNameFirstLower}Max) {
        this.${column.columnFieldNameFirstLower}Max = ${column.columnFieldNameFirstLower}Max;
    }

    public ${column.columnFieldType} get${column.columnFieldName}Max() {
        return ${column.columnFieldNameFirstLower}Max;
    }

    public void set${column.columnFieldName}Between(${column.columnFieldType} ${column.columnFieldNameFirstLower}Min, ${column.columnFieldType} ${column.columnFieldNameFirstLower}Max) {
        this.${column.columnFieldNameFirstLower}Min = ${column.columnFieldNameFirstLower}Min;
        this.${column.columnFieldNameFirstLower}Max = ${column.columnFieldNameFirstLower}Max;
    }
    <#elseif (column.columnFieldType == "Date")>

    /**
     * ${column.columnComment}
     */
    private ${column.columnFieldType} ${column.columnFieldNameFirstLower};

    /**
     * 开始 ${column.columnComment}
     */
    private ${column.columnFieldType} ${column.columnFieldNameFirstLower}Min;

    /**
     * 结束 ${column.columnComment}
     */
    private ${column.columnFieldType} ${column.columnFieldNameFirstLower}Max;

    public void set${column.columnFieldName}(${column.columnFieldType} ${column.columnFieldNameFirstLower}) {
        this.${column.columnFieldNameFirstLower} = ${column.columnFieldNameFirstLower};
    }

    public ${column.columnFieldType} get${column.columnFieldName}() {
        return ${column.columnFieldNameFirstLower};
    }

    public void set${column.columnFieldName}Min(${column.columnFieldType} ${column.columnFieldNameFirstLower}Min) {
        this.${column.columnFieldNameFirstLower}Min = ${column.columnFieldNameFirstLower}Min;
    }

    public ${column.columnFieldType} get${column.columnFieldName}Min() {
        return ${column.columnFieldNameFirstLower}Min;
    }

    public void set${column.columnFieldName}Max(${column.columnFieldType} ${column.columnFieldNameFirstLower}Max) {
        this.${column.columnFieldNameFirstLower}Max = ${column.columnFieldNameFirstLower}Max;
    }

    public ${column.columnFieldType} get${column.columnFieldName}Max() {
        return ${column.columnFieldNameFirstLower}Max;
    }

    public void set${column.columnFieldName}Between(${column.columnFieldType} ${column.columnFieldNameFirstLower}Min, ${column.columnFieldType} ${column.columnFieldNameFirstLower}Max) {
        this.${column.columnFieldNameFirstLower}Min = ${column.columnFieldNameFirstLower}Min;
        this.${column.columnFieldNameFirstLower}Max = ${column.columnFieldNameFirstLower}Max;
    }
    <#elseif (column.columnFieldType == "BigDecimal" || column.columnFieldType == "Double" || column.columnFieldType == "Float")>

    /**
     * 最小 ${column.columnComment}
     */
    private ${column.columnFieldType} ${column.columnFieldNameFirstLower}Min;

    /**
     * 最大 ${column.columnComment}
     */
    private ${column.columnFieldType} ${column.columnFieldNameFirstLower}Max;

    public void set${column.columnFieldName}Min(${column.columnFieldType} ${column.columnFieldNameFirstLower}Min) {
        this.${column.columnFieldNameFirstLower}Min = ${column.columnFieldNameFirstLower}Min;
    }

    public ${column.columnFieldType} get${column.columnFieldName}Min() {
        return ${column.columnFieldNameFirstLower}Min;
    }

    public void set${column.columnFieldName}Max(${column.columnFieldType} ${column.columnFieldNameFirstLower}Max) {
        this.${column.columnFieldNameFirstLower}Max = ${column.columnFieldNameFirstLower}Max;
    }

    public ${column.columnFieldType} get${column.columnFieldName}Max() {
        return ${column.columnFieldNameFirstLower}Max;
    }

    public void set${column.columnFieldName}Between(${column.columnFieldType} ${column.columnFieldNameFirstLower}Min, ${column.columnFieldType} ${column.columnFieldNameFirstLower}Max) {
        this.${column.columnFieldNameFirstLower}Min = ${column.columnFieldNameFirstLower}Min;
        this.${column.columnFieldNameFirstLower}Max = ${column.columnFieldNameFirstLower}Max;
    }
    <#elseif (column.columnFieldType == "String")>

    /**
     * ${column.columnComment} (完全匹配）
     */
    private ${column.columnFieldType} ${column.columnFieldNameFirstLower};

    /**
     * ${column.columnComment} (开始匹配)
     */
    private ${column.columnFieldType} ${column.columnFieldNameFirstLower}StartWith;

    /**
     * ${column.columnComment} (泛匹配)
     */
    private ${column.columnFieldType} ${column.columnFieldNameFirstLower}Like;

    /**
     * ${column.columnComment} 列表
     */
    private List<${column.columnFieldType}> ${column.columnFieldNameFirstLower}List;

    public void set${column.columnFieldName}(${column.columnFieldType} ${column.columnFieldNameFirstLower}) {
        this.${column.columnFieldNameFirstLower} = ${column.columnFieldNameFirstLower};
    }

    public ${column.columnFieldType} get${column.columnFieldName}() {
        return ${column.columnFieldNameFirstLower};
    }

    public void set${column.columnFieldName}StartWith(${column.columnFieldType} ${column.columnFieldNameFirstLower}StartWith) {
        this.${column.columnFieldNameFirstLower}StartWith = ${column.columnFieldNameFirstLower}StartWith;
    }

    public ${column.columnFieldType} get${column.columnFieldName}StartWith() {
        return ${column.columnFieldNameFirstLower}StartWith;
    }

    public void set${column.columnFieldName}Like(${column.columnFieldType} ${column.columnFieldNameFirstLower}Like) {
        this.${column.columnFieldNameFirstLower}Like = ${column.columnFieldNameFirstLower}Like;
    }

    public ${column.columnFieldType} get${column.columnFieldName}Like() {
        return ${column.columnFieldNameFirstLower}Like;
    }

    public void set${column.columnFieldName}List(List<${column.columnFieldType}> ${column.columnFieldNameFirstLower}List) {
        this.${column.columnFieldNameFirstLower}List = ${column.columnFieldNameFirstLower}List;
    }

    public List<${column.columnFieldType}> get${column.columnFieldName}List() {
        return ${column.columnFieldNameFirstLower}List;
    }

    public void set${column.columnFieldName}In(${column.columnFieldType}... ${column.columnFieldNameFirstLower}List) {
        if (this.${column.columnFieldNameFirstLower}List == null) {
            this.${column.columnFieldNameFirstLower}List = new ArrayList<${column.columnFieldType}>();
        }
        for (${column.columnFieldType} ${column.columnFieldNameFirstLower} : ${column.columnFieldNameFirstLower}List) {
            this.${column.columnFieldNameFirstLower}List.add(${column.columnFieldNameFirstLower});
        }
    }
    <#else>

    /**
     * ${column.columnComment}
     */
    private ${column.columnFieldType} ${column.columnFieldNameFirstLower};

    public void set${column.columnFieldName}(${column.columnFieldType} ${column.columnFieldNameFirstLower}) {
        this.${column.columnFieldNameFirstLower} = ${column.columnFieldNameFirstLower};
    }

    public ${column.columnFieldType} get${column.columnFieldName}() {
        return ${column.columnFieldNameFirstLower};
    }
    </#if>

    public void set${column.columnFieldName}Asc() {
        super.orderByMap.put("${column.tableName}.${column.columnName}", "ASC");
    }

    public void set${column.columnFieldName}Desc() {
        super.orderByMap.put("${column.tableName}.${column.columnName}", "DESC");
    }

    //endregion
</#if>
</#list>
}
