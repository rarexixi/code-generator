<#assign className = table.tableClassName>
package ${basePackage}.condition;

import ${basePackage}.condition.select.${className}SelectCondition;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

<#include "/include/java_copyright.ftl">
public class ${className}Condition implements Serializable {

    public ${className}Condition() {
        this.selectConditionList = new ArrayList<${className}SelectCondition>();
    }

    private List<${className}SelectCondition> selectConditionList;

    public void setSelectConditionList(List<${className}SelectCondition> selectConditionList) {
        this.selectConditionList = selectConditionList;
    }

    public List<${className}SelectCondition> getSelectConditionList() {
        return selectConditionList;
    }
}
