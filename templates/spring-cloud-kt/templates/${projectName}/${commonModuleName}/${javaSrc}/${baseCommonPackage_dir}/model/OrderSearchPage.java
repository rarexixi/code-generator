package ${baseCommonPackage}.model;

<#include "/include/java_copyright.ftl">
public class OrderSearchPage<T, O extends OrderCondition> extends SearchPage<T> {

    private O orderCondition;

    public O getOrderCondition() {
        return orderCondition;
    }

    public void setOrderCondition(O orderCondition) {
        this.orderCondition = orderCondition;
    }
}