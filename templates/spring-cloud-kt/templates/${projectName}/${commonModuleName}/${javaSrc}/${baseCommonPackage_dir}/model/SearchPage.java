package ${baseCommonPackage}.model;

import java.io.Serializable;

<#include "/include/java_copyright.ftl">
public class SearchPage<T> implements Serializable {

    public static final int DEFAULT_PAGE_INDEX = 1;
    public static final int DEFAULT_PAGE_SIZE = 10;

    private int pageIndex;
    private int pageSize;
    private T condition;

    public SearchPage() {
        this(DEFAULT_PAGE_INDEX, DEFAULT_PAGE_SIZE);
    }

    public SearchPage(int pageIndex, int pageSize) {
        this.setPageIndex(pageIndex);
        this.setPageSize(pageSize);
    }

    public SearchPage(int pageIndex, int pageSize, T condition) {
        this.setPageIndex(pageIndex);
        this.setPageSize(pageSize);
        this.setCondition(condition);
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

    public T getCondition() {
        return condition;
    }

    public void setCondition(T condition) {
        this.condition = condition;
    }
}