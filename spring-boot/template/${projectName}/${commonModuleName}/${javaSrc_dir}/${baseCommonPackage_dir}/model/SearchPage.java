package ${baseCommonPackage}.model;

import java.io.Serializable;

<#include "/include/java_copyright.ftl">
public class SearchPage implements Serializable {

    public static final int DEFAULT_PAGE_INDEX = 1;
    public static final int DEFAULT_PAGE_SIZE=10;

    protected int pageIndex;
    protected int pageSize;

    public SearchPage() {
        this(DEFAULT_PAGE_INDEX, DEFAULT_PAGE_SIZE);
    }

    public SearchPage(int pageIndex, int pageSize) {
        this.pageIndex = pageIndex;
        this.pageSize = pageSize;
    }

    public int getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(int pageIndex) {
        this.pageIndex = pageIndex;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }
}
