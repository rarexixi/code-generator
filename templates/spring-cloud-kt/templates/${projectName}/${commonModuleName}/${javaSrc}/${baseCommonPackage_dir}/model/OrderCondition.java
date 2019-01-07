package ${baseCommonPackage}.model;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

<#include "/include/java_copyright.ftl">
public class OrderCondition implements Serializable {

    protected Map<String, String> orderByMap = new LinkedHashMap<>();

    public String getOrderBy() {
        if (orderByMap.isEmpty()) {
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
