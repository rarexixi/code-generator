<#assign className = table.tableClassName>
<#assign classNameLower = table.tableClassNameFirstLower>
package ${basePackage}.quickapi.vm.in;

import ${basePackage}.parameter.${className}SelectParameter;

import java.math.BigDecimal;
import java.util.Date;
import java.io.Serializable;

public class ${className}SearchModel implements Serializable {

    <#list table.columns as column>
    /**
     * ${column.columnComment}
     */
    private ${column.columnFieldType} ${column.columnFieldNameFirstLower};

    </#list>

    <#list table.columns as column>
    /**
    * 设置${column.columnComment}
    */
    public void set${column.columnFieldName}(${column.columnFieldType} ${column.columnFieldNameFirstLower}) {
        this.${column.columnFieldNameFirstLower} = ${column.columnFieldNameFirstLower};
    }

    /**
    * 获取${column.columnComment}
    */
    public ${column.columnFieldType} get${column.columnFieldName}() {
        return this.${column.columnFieldNameFirstLower};
    }

    </#list>
    /**
     * 获取数据搜索实体
     */
    public ${className}SelectParameter get${className}SelectParameter() {
        ${className}SelectParameter parameter = new ${className}SelectParameter();
        <#list table.columns as column>
        parameter.set${column.columnFieldName}(this.${column.columnFieldNameFirstLower});
        </#list>
        return parameter;
    }

}
