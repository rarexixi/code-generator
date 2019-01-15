package ${basePackage}.common.service;

import ${basePackage}.common.model.OrderCondition;
import ${basePackage}.common.model.OrderSearch;
import ${basePackage}.common.model.OrderSearchPage;
import ${basePackage}.models.common.BaseCondition;
import ${basePackage}.models.common.BaseEntity;

import com.github.pagehelper.PageInfo;

import java.util.List;

public interface BaseService<T extends BaseEntity, C extends BaseCondition, O extends OrderCondition> {

    /**
     * 添加
     *
     * @param entity
     * @return
     */
    int insert(T entity);

    /**
     * 添加
     *
     * @param entityList
     * @return
     */
    int insert(List<T> entityList);

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
     * @param entity
     * @param condition
     * @return
     */
    int update(T entity, C condition);

    /**
     * 根据条件更新
     *
     * @param entity
     * @param conditionList
     * @return
     */
    int update(T entity, List<C> conditionList);

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
     * @param search
     * @return
     */
    List<T> getList(OrderSearch<C, O> search);

    /**
     * 查询符合条件的列表
     *
     * @param search
     * @return
     */
    List<T> getListByConditionList(OrderSearch<List<C>, O> search);

    /**
     * 分页查询符合条件的列表
     *
     * @param searchPage
     * @return
     */
    PageInfo<T> getPageInfo(OrderSearchPage<C, O> searchPage);

    /**
     * 分页查询符合条件的列表
     *
     * @param searchPage
     * @return
     */
    PageInfo<T> getPageInfoByConditionList(OrderSearchPage<List<C>, O> searchPage);

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
