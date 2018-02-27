<#assign className = table.tableClassName>
<#assign classNameLower = table.tableClassNameFirstLower>
package ${basePackage}.quickapi.vm.in;

import org.xi.condition.order.BaseOrderCondition;
import ${basePackage}.parameter.${className}SelectParameter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.io.Serializable;

public class ${className}SearchModel extends BaseOrderCondition implements Serializable {
    <#list table.columns as column>

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
    </#list>

    /**
     * 获取数据搜索实体
     */
    public ${className}SelectParameter get${className}SelectParameter() {
        ${className}SelectParameter parameter = new ${className}SelectParameter();
        <#list table.columns as column>
        <#if column.columnName == table.validStatusField.fieldName>
        parameter.set${column.columnFieldName}(${column.columnFieldNameFirstLower});
        <#elseif (column.columnFieldType == "Integer" || column.columnFieldType == "Long" || column.columnFieldType == "Short" || column.columnFieldType == "Byte")>
        parameter.set${column.columnFieldName}(${column.columnFieldNameFirstLower});
        parameter.set${column.columnFieldName}List(${column.columnFieldNameFirstLower}List);
        <#elseif (column.columnFieldType == "Date" || column.columnFieldType == "BigDecimal" || column.columnFieldType == "Double" || column.columnFieldType == "Float")>
        parameter.set${column.columnFieldName}Min(${column.columnFieldNameFirstLower}Min);
        parameter.set${column.columnFieldName}Max(${column.columnFieldNameFirstLower}Max);
        <#elseif (column.columnFieldType == "String")>
        parameter.set${column.columnFieldName}(${column.columnFieldNameFirstLower});
        parameter.set${column.columnFieldName}StartWith(${column.columnFieldNameFirstLower}StartWith);
        parameter.set${column.columnFieldName}Like(${column.columnFieldNameFirstLower}Like);
        <#else>
        parameter.set${column.columnFieldName}(${column.columnFieldNameFirstLower});
        </#if>
        </#list>

        return parameter;
    }

}
