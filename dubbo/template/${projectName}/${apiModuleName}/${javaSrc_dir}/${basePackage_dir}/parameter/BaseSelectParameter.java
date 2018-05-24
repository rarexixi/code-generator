package ${basePackage}.parameter;

import ${baseCommonPackage}.model.SearchPage;
import ${basePackage}.condition.order.OrderCondition;
import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

<#include "/include/java_copyright.ftl">
public class BaseSelectParameter extends SearchPage implements OrderCondition, Serializable {

    public BaseSelectParameter() {
        orderByMap = new LinkedHashMap<>();
    }

    protected Map<String, String> orderByMap;

    public String getOrderBy() {
        int size = orderByMap.size();
        if (size == 0) {
            return null;
        }
        String orderBy = orderByMap
                .entrySet()
                .stream()
                .map(entry -> entry.getKey() + " " + entry.getValue())
                .collect(Collectors.joining(", "));
        return orderBy;
    }
}
