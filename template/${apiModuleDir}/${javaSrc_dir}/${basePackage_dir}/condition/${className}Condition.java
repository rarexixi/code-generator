<#assign className = table.tableClassName>
package ${basePackage}.condition;

import ${basePackage}.condition.order.FileOrderCondition;
import ${basePackage}.condition.select.FileSelectCondition;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

<#include "/include/java_copyright.ftl">
public class ${className}Condition implements Serializable {

    public ${className}Condition() {
        this.selectConditionList = new ArrayList<${className}SelectCondition>();
        this.orderCondition = new ${className}OrderCondition();
    }

    private List<${className}SelectCondition> selectConditionList;
    private ${className}OrderCondition orderCondition;

    public void setSelectConditionList(List<${className}SelectCondition> selectConditionList) {
        this.selectConditionList = selectConditionList;
    }

    public List<${className}SelectCondition> getSelectConditionList() {
        return this.selectConditionList;
    }

    public void setOrderCondition(${className}OrderCondition orderCondition) {
        this.orderCondition = orderCondition;
    }

    public ${className}OrderCondition getOrderCondition() {
        return this.orderCondition;
    }
}
