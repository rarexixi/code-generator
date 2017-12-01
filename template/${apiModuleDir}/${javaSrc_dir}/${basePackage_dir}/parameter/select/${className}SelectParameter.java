<#assign className = table.tableClassName>
package ${basePackage}.parameter.select;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.*;

<#include "/include/java_copyright.ftl">
public class ${className}SelectParameter extends BaseSelectParameter implements Serializable {

    public ${className}SelectParameter() {
        super();
    }
    <#list table.columns as column>

    //region ${column.columnComment}
    <#if column.columnFieldNameFirstLower == 'isActive'>

    /**
    * ${column.columnComment}
    */
    private ${column.columnFieldType} ${column.columnFieldNameFirstLower};

    public void set${column.columnFieldName}(${column.columnFieldType} ${column.columnFieldNameFirstLower}) {
        this.${column.columnFieldNameFirstLower} = ${column.columnFieldNameFirstLower};
    }

    public ${column.columnFieldType} get${column.columnFieldName}() {
        return this.${column.columnFieldNameFirstLower};
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

    public void set${column.columnFieldName}(${column.columnFieldType} ${column.columnFieldNameFirstLower}) {
        this.${column.columnFieldNameFirstLower} = ${column.columnFieldNameFirstLower};
    }

    public ${column.columnFieldType} get${column.columnFieldName}() {
        return this.${column.columnFieldNameFirstLower};
    }

    public void set${column.columnFieldName}List(List<${column.columnFieldType}> ${column.columnFieldNameFirstLower}List) {
        this.${column.columnFieldNameFirstLower}List = ${column.columnFieldNameFirstLower}List;
    }

    public List<${column.columnFieldType}> get${column.columnFieldName}List() {
        return this.${column.columnFieldNameFirstLower}List;
    }

    public void set${column.columnFieldName}In(${column.columnFieldType}... ${column.columnFieldNameFirstLower}List) {
        if (this.${column.columnFieldNameFirstLower}List == null) {
            this.${column.columnFieldNameFirstLower}List = new ArrayList<${column.columnFieldType}>();
        }
        for (${column.columnFieldType} ${column.columnFieldNameFirstLower} : ${column.columnFieldNameFirstLower}List) {
            this.${column.columnFieldNameFirstLower}List.add(${column.columnFieldNameFirstLower});
        }
    }
    <#elseif (column.columnFieldType == "Date" || column.columnFieldType == "BigDecimal" || column.columnFieldType == "Double" || column.columnFieldType == "Float")>

    /**
    * <#if column.columnFieldType == "Date">开始<#else>最小</#if> ${column.columnComment}
    */
    private ${column.columnFieldType} ${column.columnFieldNameFirstLower}Min;

    /**
    * <#if column.columnFieldType == "Date">结束<#else>最大</#if> ${column.columnComment}
    */
    private ${column.columnFieldType} ${column.columnFieldNameFirstLower}Max;

    public void set${column.columnFieldName}Min(${column.columnFieldType} ${column.columnFieldNameFirstLower}Min) {
        this.${column.columnFieldNameFirstLower}Min = ${column.columnFieldNameFirstLower}Min;
    }

    public ${column.columnFieldType} get${column.columnFieldName}Min() {
        return this.${column.columnFieldNameFirstLower}Min;
    }

    public void set${column.columnFieldName}Max(${column.columnFieldType} ${column.columnFieldNameFirstLower}Max) {
        this.${column.columnFieldNameFirstLower}Max = ${column.columnFieldNameFirstLower}Max;
    }

    public ${column.columnFieldType} get${column.columnFieldName}Max() {
        return this.${column.columnFieldNameFirstLower}Max;
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

    public void set${column.columnFieldName}(${column.columnFieldType} ${column.columnFieldNameFirstLower}) {
        this.${column.columnFieldNameFirstLower} = ${column.columnFieldNameFirstLower};
    }

    public ${column.columnFieldType} get${column.columnFieldName}() {
        return this.${column.columnFieldNameFirstLower};
    }

    public void set${column.columnFieldName}StartWith(${column.columnFieldType} ${column.columnFieldNameFirstLower}StartWith) {
        this.${column.columnFieldNameFirstLower}StartWith = ${column.columnFieldNameFirstLower}StartWith;
    }

    public ${column.columnFieldType} get${column.columnFieldName}StartWith() {
        return this.${column.columnFieldNameFirstLower}StartWith;
    }

    public void set${column.columnFieldName}Like(${column.columnFieldType} ${column.columnFieldNameFirstLower}Like) {
        this.${column.columnFieldNameFirstLower}Like = ${column.columnFieldNameFirstLower}Like;
    }

    public ${column.columnFieldType} get${column.columnFieldName}Like() {
        return this.${column.columnFieldNameFirstLower}Like;
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
        return this.${column.columnFieldNameFirstLower};
    }
    </#if>

    public void set${column.columnFieldName}Asc() {
        super.orderByMap.put("${column.tableName}.${column.columnName}", "ASC");
    }

    public void set${column.columnFieldName}Desc() {
        super.orderByMap.put("${column.tableName}.${column.columnName}", "DESC");
    }

    //endregion
    </#list>
}
