package ${basePackage}.condition.order;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

<#include "/include/java_copyright.ftl">
public class BaseOrderCondition implements OrderCondition {

    public BaseOrderCondition() {
        orderByMap = new HashMap<>();
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
