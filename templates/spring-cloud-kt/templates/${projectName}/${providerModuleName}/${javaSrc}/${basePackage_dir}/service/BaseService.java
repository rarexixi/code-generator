package ${basePackage}.service;

import ${baseCommonPackage}.model.OrderCondition;
import ${baseCommonPackage}.model.SearchPage;

import ${basePackage}.models.entity.BaseEntity;
import ${basePackage}.models.condition.BaseCondition;

import com.github.pagehelper.PageInfo;

import java.io.Serializable;
import java.util.List;

public interface BaseService<T extends BaseEntity, C extends BaseCondition> {

    /**
     * 添加
     *
     * @param model
     * @return
     */
    int insert(T model);

    /**
     * 添加
     *
     * @param models
     * @return
     */
    int insert(List<T> models);

    /**
     * 根据条件删除
     *
     * @param condition
     * @return
     */
    int delete(C condition);

    /**
     * 根据条件删除
     *
     * @param conditionList
     * @return
     */
    int delete(List<C> conditionList);

    /**
     * 根据条件更新
     *
     * @param model
     * @param condition
     * @return
     */
    int update(T model, C condition);

    /**
     * 根据条件更新
     *
     * @param model
     * @param conditionList
     * @return
     */
    int update(T model, List<C> conditionList);

    /**
     * 查询
     *
     * @param condition
     * @return
     */
    T get(C condition);

    /**
     * 查询符合条件的列表
     *
     * @param condition
     * @return
     */
    List<T> getList(C condition);

    /**
     * 查询符合条件的列表
     *
     * @param condition
     * @param order
     * @return
     */
    List<T> getList(C condition, OrderCondition order);

    /**
     * 查询符合条件的列表
     *
     * @param conditionList
     * @return
     */
    List<T> getList(List<C> conditionList);

    /**
     * 查询符合条件的列表
     *
     * @param conditionList
     * @param order
     * @return
     */
    List<T> getList(List<C> conditionList, OrderCondition order);

    /**
     * 分页查询符合条件的列表
     *
     * @param condition
     * @param searchPage
     * @return
     */
    PageInfo<T> getPageInfo(SearchPage<C> searchPage);

    /**
     * 分页查询符合条件的列表
     *
     * @param condition
     * @param order
     * @param searchPage
     * @return
     */
    PageInfo<T> getPageInfo(SearchPage<C> searchPage, OrderCondition order);

    /**
     * 分页查询符合条件的列表
     *
     * @param conditionList
     * @param searchPage
     * @return
     */
    PageInfo<T> getPageInfo(SearchPage<List<C>> searchPage);

    /**
     * 分页查询符合条件的列表
     *
     * @param conditionList
     * @param order
     * @param searchPage
     * @return
     */
    PageInfo<T> getPageInfo(SearchPage<List<C>> searchPage, OrderCondition order);

    /**
     * 查询数量
     *
     * @param condition
     * @return
     */
    int countByCondition(C condition);

    /**
     * 查询数量
     *
     * @param conditionList
     * @return
     */
    int countByConditionList(List<C> conditionList);
}
