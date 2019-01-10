package ${basePackage}.models.common;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.*;

<#include "/include/java_copyright.ftl">
public class BaseCondition implements Serializable {
<#list baseColumns as column>
<#include "/include/column/properties.ftl">
<#if (column.ignoreSearch || column.dataType?ends_with("text"))>
<#else>
    <#if (canBeEqual)>

    /**
     * ${columnFullComment}
     */
    private ${fieldType} ${fieldName};
    </#if>
    <#if (canBeList)>

    /**
     * ${columnComment} 列表
     */
    private List<${fieldType}> ${fieldName}List;
    </#if>
    <#if (canBeRange)>

    /**
     * 最小 ${columnComment}
     */
    private ${fieldType} ${fieldName}Min;

    /**
     * 最大 ${columnComment}
     */
    private ${fieldType} ${fieldName}Max;
    </#if>
    <#if (canBeNull)>

    /**
     * ${columnComment}为null
     */
    private Boolean ${fieldName}IsNull;
    </#if>
    <#if (column.dataType?ends_with("char"))>

    /**
     * ${columnComment}为空
     */
    private Boolean ${fieldName}IsEmpty;

    /**
     * ${columnComment}
     */
    private ${fieldType} ${fieldName}StartWith;

    /**
     * ${columnComment}
     */
    private ${fieldType} ${fieldName}Contains;
    </#if>
</#if>
</#list>
<#list baseColumns as column>
<#include "/include/column/properties.ftl">
<#if (column.ignoreSearch || column.dataType?ends_with("text"))>
<#else>
    <#if (canBeEqual)>

    public void set${propertyName}(${fieldType} ${fieldName}) {
        this.${fieldName} = ${fieldName};
    }

    public ${fieldType} get${propertyName}() {
        return ${fieldName};
    }
    </#if>
    <#if (canBeList)>

    public void set${propertyName}List(List<${fieldType}> ${fieldName}List) {
        this.${fieldName}List = ${fieldName}List;
    }

    public List<${fieldType}> get${propertyName}List() {
        return ${fieldName}List;
    }

    public void set${propertyName}In(${fieldType}... ${fieldName}List) {
        if (this.${fieldName}List == null) {
            this.${fieldName}List = new ArrayList<>();
        }
        for (${fieldType} ${fieldName} : ${fieldName}List) {
            this.${fieldName}List.add(${fieldName});
        }
    }
    </#if>
    <#if (canBeRange)>

    public void set${propertyName}Min(${fieldType} ${fieldName}Min) {
        this.${fieldName}Min = ${fieldName}Min;
    }

    public ${fieldType} get${propertyName}Min() {
        return ${fieldName}Min;
    }

    public void set${propertyName}Max(${fieldType} ${fieldName}Max) {
        this.${fieldName}Max = ${fieldName}Max;
    }

    public ${fieldType} get${propertyName}Max() {
        return ${fieldName}Max;
    }

    public void set${propertyName}Between(${fieldType} ${fieldName}Min, ${fieldType} ${fieldName}Max) {
        this.${fieldName}Min = ${fieldName}Min;
        this.${fieldName}Max = ${fieldName}Max;
    }
    </#if>
    <#if (canBeNull)>

    public void set${propertyName}IsNull(Boolean ${fieldName}IsNull) {
        this.${fieldName}IsNull = ${fieldName}IsNull;
    }

    public Boolean get${propertyName}IsNull() {
        return ${fieldName}IsNull;
    }
    </#if>
    <#if (column.dataType?ends_with("char"))>

    public void set${propertyName}IsEmpty(Boolean ${fieldName}IsEmpty) {
        this.${fieldName}IsEmpty = ${fieldName}IsEmpty;
    }

    public Boolean get${propertyName}IsEmpty() {
        return ${fieldName}IsEmpty;
    }

    public void set${propertyName}StartWith(${fieldType} ${fieldName}StartWith) {
        this.${fieldName}StartWith = ${fieldName}StartWith;
    }

    public ${fieldType} get${propertyName}StartWith() {
        return ${fieldName}StartWith;
    }

    public void set${propertyName}Contains(${fieldType} ${fieldName}Contains) {
        this.${fieldName}Contains = ${fieldName}Contains;
    }

    public ${fieldType} get${propertyName}Contains() {
        return ${fieldName}Contains;
    }
    </#if>
</#if>
</#list>
}
