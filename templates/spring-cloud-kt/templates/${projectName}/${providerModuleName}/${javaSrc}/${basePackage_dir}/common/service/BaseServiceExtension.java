package ${basePackage}.common.service;

import ${baseCommonPackage}.model.OrderCondition;
import ${baseCommonPackage}.model.OrderSearch;
import ${baseCommonPackage}.model.OrderSearchPage;
import ${basePackage}.models.common.BaseCondition;
import ${basePackage}.models.common.BaseEntity;

import com.github.pagehelper.PageInfo;

import java.util.List;

public interface BaseServiceExtension<T extends BaseEntity, C extends BaseCondition, O extends OrderCondition> {

    /**
     * 获取列表（不分页）
     *
     * @param search
     * @return
     */
    List<T> getExList(OrderSearch<C, O> search);

    /**
     * 分页查询
     *
     * @param searchPage
     * @return
     */
    PageInfo<T> getPageList(OrderSearchPage<C, O> searchPage);
}
