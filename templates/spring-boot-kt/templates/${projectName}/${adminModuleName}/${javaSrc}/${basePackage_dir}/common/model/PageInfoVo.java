package ${basePackage}.common.model;

import java.io.Serializable;
import java.util.List;

public class PageInfoVo<T> implements Serializable {

    public PageInfoVo() {
    }

    public PageInfoVo(Integer pageIndex, Integer pageSize, Long total, List<T> list) {
        this.pageIndex = pageIndex;
        this.pageSize = pageSize;
        this.total = total;
        this.list = list;
    }

    private Integer pageIndex;
    private Integer pageSize;
    private Long total;
    private List<T> list;

    public Integer getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(Integer pageIndex) {
        this.pageIndex = pageIndex;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }
}