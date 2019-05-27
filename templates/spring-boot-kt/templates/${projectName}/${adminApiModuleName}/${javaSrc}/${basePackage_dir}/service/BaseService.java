package ${basePackage}.service;

import ${baseCommonPackage}.model.*;

import java.util.List;

public interface BaseService<AddOrEditVm, DetailVm, OrderVm extends OrderCondition, SearchVm> {

    /**
     * 添加
     *
     * @param vm
     * @return
     <#include "/include/author_info1.ftl">
     */
    int add(AddOrEditVm vm);

    /**
     * 添加列表
     *
     * @param list
     * @return
     <#include "/include/author_info1.ftl">
     */
    int addList(List<AddOrEditVm> list);

    /**
     * 根据条件删除
     *
     * @param searchVm
     * @return
     <#include "/include/author_info1.ftl">
     */
    int delete(SearchVm searchVm);

    /**
     * 根据条件获取实体
     *
     * @param searchVm
     * @return
     <#include "/include/author_info1.ftl">
     */
    DetailVm get(SearchVm searchVm);

    /**
     * 获取列表
     *
     * @param search
     * @return
     <#include "/include/author_info1.ftl">
     */
    List<DetailVm> getList(OrderSearch<SearchVm, OrderVm> search);

    /**
     * 分页查询
     *
     * @param searchPage
     * @return
     <#include "/include/author_info1.ftl">
     */
    PageInfoVo<DetailVm> getPageInfo(OrderSearchPage<SearchVm, OrderVm> searchPage);
}