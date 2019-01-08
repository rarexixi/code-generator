package ${baseCommonPackage}.model;

<#include "/include/java_copyright.ftl">
public class OrderSearchPage<T, O extends OrderCondition> extends SearchPage<T> {

    public OrderSearchPage() {
        super();
    }

    public OrderSearchPage(int pageIndex, int pageSize, T condition, O orderCondition) {
        super(pageIndex, pageSize, condition);
        this.orderCondition = orderCondition;
    }

    private O orderCondition;

    public O getOrderCondition() {
        return orderCondition;
    }

    public void setOrderCondition(O orderCondition) {
        this.orderCondition = orderCondition;
    }
}