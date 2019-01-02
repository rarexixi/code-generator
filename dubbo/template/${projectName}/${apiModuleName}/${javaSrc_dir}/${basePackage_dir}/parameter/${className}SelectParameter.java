<#assign className = table.tableClassName>
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
    private ${column.targetDataType} ${column.targetColumnNameFirstLower};

    public void set${column.targetColumnName}(${column.targetDataType} ${column.targetColumnNameFirstLower}) {
        this.${column.targetColumnNameFirstLower} = ${column.targetColumnNameFirstLower};
    }

    public ${column.targetDataType} get${column.targetColumnName}() {
        return ${column.targetColumnNameFirstLower};
    }
    <#elseif (column.targetDataType == "Integer" || column.targetDataType == "Long" || column.targetDataType == "Short" || column.targetDataType == "Byte")>

    /**
     * ${column.columnComment}
     */
    private ${column.targetDataType} ${column.targetColumnNameFirstLower};

    /**
     * ${column.columnComment} 列表
     */
    private List<${column.targetDataType}> ${column.targetColumnNameFirstLower}List;

    /**
     * 最小 ${column.columnComment}
     */
    private ${column.targetDataType} ${column.targetColumnNameFirstLower}Min;

    /**
     * 最大 ${column.columnComment}
     */
    private ${column.targetDataType} ${column.targetColumnNameFirstLower}Max;

    public void set${column.targetColumnName}(${column.targetDataType} ${column.targetColumnNameFirstLower}) {
        this.${column.targetColumnNameFirstLower} = ${column.targetColumnNameFirstLower};
    }

    public ${column.targetDataType} get${column.targetColumnName}() {
        return ${column.targetColumnNameFirstLower};
    }

    public void set${column.targetColumnName}List(List<${column.targetDataType}> ${column.targetColumnNameFirstLower}List) {
        this.${column.targetColumnNameFirstLower}List = ${column.targetColumnNameFirstLower}List;
    }

    public List<${column.targetDataType}> get${column.targetColumnName}List() {
        return ${column.targetColumnNameFirstLower}List;
    }

    public void set${column.targetColumnName}In(${column.targetDataType}... ${column.targetColumnNameFirstLower}List) {
        if (this.${column.targetColumnNameFirstLower}List == null) {
            this.${column.targetColumnNameFirstLower}List = new ArrayList<${column.targetDataType}>();
        }
        for (${column.targetDataType} ${column.targetColumnNameFirstLower} : ${column.targetColumnNameFirstLower}List) {
            this.${column.targetColumnNameFirstLower}List.add(${column.targetColumnNameFirstLower});
        }
    }

    public void set${column.targetColumnName}Min(${column.targetDataType} ${column.targetColumnNameFirstLower}Min) {
        this.${column.targetColumnNameFirstLower}Min = ${column.targetColumnNameFirstLower}Min;
    }

    public ${column.targetDataType} get${column.targetColumnName}Min() {
        return ${column.targetColumnNameFirstLower}Min;
    }

    public void set${column.targetColumnName}Max(${column.targetDataType} ${column.targetColumnNameFirstLower}Max) {
        this.${column.targetColumnNameFirstLower}Max = ${column.targetColumnNameFirstLower}Max;
    }

    public ${column.targetDataType} get${column.targetColumnName}Max() {
        return ${column.targetColumnNameFirstLower}Max;
    }

    public void set${column.targetColumnName}Between(${column.targetDataType} ${column.targetColumnNameFirstLower}Min, ${column.targetDataType} ${column.targetColumnNameFirstLower}Max) {
        this.${column.targetColumnNameFirstLower}Min = ${column.targetColumnNameFirstLower}Min;
        this.${column.targetColumnNameFirstLower}Max = ${column.targetColumnNameFirstLower}Max;
    }
    <#elseif (column.targetDataType == "Date")>

    /**
     * ${column.columnComment}
     */
    private ${column.targetDataType} ${column.targetColumnNameFirstLower};

    /**
     * 开始 ${column.columnComment}
     */
    private ${column.targetDataType} ${column.targetColumnNameFirstLower}Min;

    /**
     * 结束 ${column.columnComment}
     */
    private ${column.targetDataType} ${column.targetColumnNameFirstLower}Max;

    public void set${column.targetColumnName}(${column.targetDataType} ${column.targetColumnNameFirstLower}) {
        this.${column.targetColumnNameFirstLower} = ${column.targetColumnNameFirstLower};
    }

    public ${column.targetDataType} get${column.targetColumnName}() {
        return ${column.targetColumnNameFirstLower};
    }

    public void set${column.targetColumnName}Min(${column.targetDataType} ${column.targetColumnNameFirstLower}Min) {
        this.${column.targetColumnNameFirstLower}Min = ${column.targetColumnNameFirstLower}Min;
    }

    public ${column.targetDataType} get${column.targetColumnName}Min() {
        return ${column.targetColumnNameFirstLower}Min;
    }

    public void set${column.targetColumnName}Max(${column.targetDataType} ${column.targetColumnNameFirstLower}Max) {
        this.${column.targetColumnNameFirstLower}Max = ${column.targetColumnNameFirstLower}Max;
    }

    public ${column.targetDataType} get${column.targetColumnName}Max() {
        return ${column.targetColumnNameFirstLower}Max;
    }

    public void set${column.targetColumnName}Between(${column.targetDataType} ${column.targetColumnNameFirstLower}Min, ${column.targetDataType} ${column.targetColumnNameFirstLower}Max) {
        this.${column.targetColumnNameFirstLower}Min = ${column.targetColumnNameFirstLower}Min;
        this.${column.targetColumnNameFirstLower}Max = ${column.targetColumnNameFirstLower}Max;
    }
    <#elseif (column.targetDataType == "BigDecimal" || column.targetDataType == "Double" || column.targetDataType == "Float")>

    /**
     * 最小 ${column.columnComment}
     */
    private ${column.targetDataType} ${column.targetColumnNameFirstLower}Min;

    /**
     * 最大 ${column.columnComment}
     */
    private ${column.targetDataType} ${column.targetColumnNameFirstLower}Max;

    public void set${column.targetColumnName}Min(${column.targetDataType} ${column.targetColumnNameFirstLower}Min) {
        this.${column.targetColumnNameFirstLower}Min = ${column.targetColumnNameFirstLower}Min;
    }

    public ${column.targetDataType} get${column.targetColumnName}Min() {
        return ${column.targetColumnNameFirstLower}Min;
    }

    public void set${column.targetColumnName}Max(${column.targetDataType} ${column.targetColumnNameFirstLower}Max) {
        this.${column.targetColumnNameFirstLower}Max = ${column.targetColumnNameFirstLower}Max;
    }

    public ${column.targetDataType} get${column.targetColumnName}Max() {
        return ${column.targetColumnNameFirstLower}Max;
    }

    public void set${column.targetColumnName}Between(${column.targetDataType} ${column.targetColumnNameFirstLower}Min, ${column.targetDataType} ${column.targetColumnNameFirstLower}Max) {
        this.${column.targetColumnNameFirstLower}Min = ${column.targetColumnNameFirstLower}Min;
        this.${column.targetColumnNameFirstLower}Max = ${column.targetColumnNameFirstLower}Max;
    }
    <#elseif (column.targetDataType == "String")>

    /**
     * ${column.columnComment} (完全匹配）
     */
    private ${column.targetDataType} ${column.targetColumnNameFirstLower};

    /**
     * ${column.columnComment} (开始匹配)
     */
    private ${column.targetDataType} ${column.targetColumnNameFirstLower}StartWith;

    /**
     * ${column.columnComment} (泛匹配)
     */
    private ${column.targetDataType} ${column.targetColumnNameFirstLower}Like;

    /**
     * ${column.columnComment} 列表
     */
    private List<${column.targetDataType}> ${column.targetColumnNameFirstLower}List;

    public void set${column.targetColumnName}(${column.targetDataType} ${column.targetColumnNameFirstLower}) {
        this.${column.targetColumnNameFirstLower} = ${column.targetColumnNameFirstLower};
    }

    public ${column.targetDataType} get${column.targetColumnName}() {
        return ${column.targetColumnNameFirstLower};
    }

    public void set${column.targetColumnName}StartWith(${column.targetDataType} ${column.targetColumnNameFirstLower}StartWith) {
        this.${column.targetColumnNameFirstLower}StartWith = ${column.targetColumnNameFirstLower}StartWith;
    }

    public ${column.targetDataType} get${column.targetColumnName}StartWith() {
        return ${column.targetColumnNameFirstLower}StartWith;
    }

    public void set${column.targetColumnName}Like(${column.targetDataType} ${column.targetColumnNameFirstLower}Like) {
        this.${column.targetColumnNameFirstLower}Like = ${column.targetColumnNameFirstLower}Like;
    }

    public ${column.targetDataType} get${column.targetColumnName}Like() {
        return ${column.targetColumnNameFirstLower}Like;
    }

    public void set${column.targetColumnName}List(List<${column.targetDataType}> ${column.targetColumnNameFirstLower}List) {
        this.${column.targetColumnNameFirstLower}List = ${column.targetColumnNameFirstLower}List;
    }

    public List<${column.targetDataType}> get${column.targetColumnName}List() {
        return ${column.targetColumnNameFirstLower}List;
    }

    public void set${column.targetColumnName}In(${column.targetDataType}... ${column.targetColumnNameFirstLower}List) {
        if (this.${column.targetColumnNameFirstLower}List == null) {
            this.${column.targetColumnNameFirstLower}List = new ArrayList<${column.targetDataType}>();
        }
        for (${column.targetDataType} ${column.targetColumnNameFirstLower} : ${column.targetColumnNameFirstLower}List) {
            this.${column.targetColumnNameFirstLower}List.add(${column.targetColumnNameFirstLower});
        }
    }
    <#else>

    /**
     * ${column.columnComment}
     */
    private ${column.targetDataType} ${column.targetColumnNameFirstLower};

    public void set${column.targetColumnName}(${column.targetDataType} ${column.targetColumnNameFirstLower}) {
        this.${column.targetColumnNameFirstLower} = ${column.targetColumnNameFirstLower};
    }

    public ${column.targetDataType} get${column.targetColumnName}() {
        return ${column.targetColumnNameFirstLower};
    }
    </#if>

    public void set${column.targetColumnName}Asc() {
        super.orderByMap.put("${column.tableName}.${column.columnName}", "ASC");
    }

    public void set${column.targetColumnName}Desc() {
        super.orderByMap.put("${column.tableName}.${column.columnName}", "DESC");
    }

    //endregion
</#if>
</#list>
}
