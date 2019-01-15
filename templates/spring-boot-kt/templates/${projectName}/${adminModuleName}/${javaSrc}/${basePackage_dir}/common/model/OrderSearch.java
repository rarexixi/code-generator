package ${basePackage}.common.model;

import java.io.Serializable;

<#include "/include/java_copyright.ftl">
public class OrderSearch<T, O extends OrderCondition> implements Serializable {

    public OrderSearch() {
    }

    public OrderSearch(T condition, O order) {
        this.condition = condition;
        this.order = order;
    }

    private T condition;
    private O order;

    public T getCondition() {
        return condition;
    }

    public void setCondition(T condition) {
        this.condition = condition;
    }

    public O getOrder() {
        return order;
    }

    public void setOrder(O order) {
        this.order = order;
    }
}