package ${baseCommonPackage}.model;

<#include "/include/java_copyright.ftl">
public class OrderSearchPage<T, O extends OrderCondition> extends OrderSearch<T, O> {

    public static final int DEFAULT_PAGE_INDEX = 1;
    public static final int DEFAULT_PAGE_SIZE = 10;

    private int pageIndex;
    private int pageSize;

    public OrderSearchPage() {
        this(DEFAULT_PAGE_INDEX, DEFAULT_PAGE_SIZE);
    }

    public OrderSearchPage(int pageIndex, int pageSize) {
        this(pageIndex, pageSize, null, null);
    }

    public OrderSearchPage(int pageIndex, int pageSize, T condition) {
        this(pageIndex, pageSize, condition, null);
    }

    public OrderSearchPage(int pageIndex, int pageSize, OrderSearch<T, O> orderSearch) {
        this(pageIndex, pageSize, orderSearch.getCondition(), orderSearch.getOrder());
    }

    public OrderSearchPage(int pageIndex, int pageSize, T condition, O order) {
        super(condition, order);
        this.setPageIndex(pageIndex);
        this.setPageSize(pageSize);
    }

    public int getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(int pageIndex) {
        this.pageIndex = pageIndex <= 0 ? DEFAULT_PAGE_INDEX : pageIndex;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize <= 0 ? DEFAULT_PAGE_SIZE : pageSize;
    }
}