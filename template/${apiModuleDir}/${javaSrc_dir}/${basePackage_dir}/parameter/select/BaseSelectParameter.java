package ${basePackage}.parameter.select;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

<#include "/include/java_copyright.ftl">
public class BaseSelectParameter implements Serializable {

    public BaseSelectParameter() {
        orderByMap = new HashMap<String, String>();
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
