package ${basePackage}.admin.service;

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
    ResponseVo<AddOrEditVm> add(AddOrEditVm vm);

    /**
     * 添加列表
     *
     * @param list
     * @return
     <#include "/include/author_info1.ftl">
     */
    ResponseVo<Integer> addList(List<AddOrEditVm> list);

    /**
     * 根据条件删除
     *
     * @param searchVm
     * @return
     <#include "/include/author_info1.ftl">
     */
    ResponseVo<Integer> delete(SearchVm searchVm);

    /**
     * 根据条件获取实体
     *
     * @param searchVm
     * @return
     <#include "/include/author_info1.ftl">
     */
    ResponseVo<DetailVm> get(SearchVm searchVm);

    /**
     * 获取列表
     *
     * @param search
     * @return
     <#include "/include/author_info1.ftl">
     */
    ResponseVo<List<DetailVm>> getList(OrderSearch<SearchVm, OrderVm> search);

    /**
     * 分页查询
     *
     * @param searchPage
     * @return
     <#include "/include/author_info1.ftl">
     */
    ResponseVo<PageInfoVo<DetailVm>> getPageInfo(OrderSearchPage<SearchVm, OrderVm> searchPage);
}