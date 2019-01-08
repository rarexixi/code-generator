package ${basePackage}.common.service.impl;

import ${baseCommonPackage}.model.OrderCondition;
import ${baseCommonPackage}.model.SearchPage;
import ${basePackage}.common.mapper.BaseMapper;
import ${basePackage}.common.service.BaseService;
import ${basePackage}.models.common.BaseCondition;
import ${basePackage}.models.common.BaseEntity;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public abstract class BaseServiceImpl<T extends BaseEntity, C extends BaseCondition> implements BaseService<T, C> {

    protected BaseMapper<T, C> mapper;

    /**
     * 添加
     *
     * @param model
     * @return
     */
    @Override
    public int insert(T model) {
        return mapper.insert(model);
    }

    /**
     * 添加
     *
     * @param models
     * @return
     */
    @Override
    public int insert(List<T> models) {
        return mapper.insertList(models);
    }

    /**
     * 根据条件删除
     *
     * @param condition
     * @return
     */
    @Override
    public int delete(C condition) {
        return mapper.deleteByCondition(condition);
    }

    /**
     * 根据条件删除
     *
     * @param conditionList
     * @return
     */
    @Override
    public int delete(List<C> conditionList) {
        return mapper.deleteByConditionList(conditionList);
    }

    /**
     * 根据条件更新
     *
     * @param model
     * @param condition
     * @return
     */
    @Override
    public int update(T model, C condition) {
        return mapper.updateByCondition(model, condition);
    }

    /**
     * 根据条件更新
     *
     * @param model
     * @param conditionList
     * @return
     */
    @Override
    public int update(T model, List<C> conditionList) {
        return mapper.updateByConditionList(model, conditionList);
    }

    /**
     * 查询
     *
     * @param condition
     * @return
     */
    @Transactional(readOnly = true)
    @Override
    public T get(C condition) {
        return mapper.getByCondition(condition);
    }

    /**
     * 查询符合条件的列表
     *
     * @param condition
     * @return
     */
    @Transactional(readOnly = true)
    @Override
    public List<T> getList(C condition) {

        return mapper.selectByCondition(condition, null);
    }

    /**
     * 查询符合条件的列表
     *
     * @param condition
     * @param order
     * @return
     */
    @Transactional(readOnly = true)
    @Override
    public List<T> getList(C condition, OrderCondition order) {

        List<T> list = mapper.selectByCondition(condition, order);
        return list;
    }

    /**
     * 查询符合条件的列表
     *
     * @param conditionList
     * @return
     */
    @Transactional(readOnly = true)
    @Override
    public List<T> getList(List<C> conditionList) {

        List<T> list = mapper.selectByConditionList(conditionList, null);
        return list;
    }

    /**
     * 查询符合条件的列表
     *
     * @param conditionList
     * @param order
     * @return
     */
    @Transactional(readOnly = true)
    @Override
    public List<T> getList(List<C> conditionList, OrderCondition order) {

        List<T> list = mapper.selectByConditionList(conditionList, order);
        return list;
    }

    /**
     * 分页查询符合条件的列表
     *
     * @param searchPage
     * @return
     */
    @Transactional(readOnly = true)
    @Override
    public PageInfo<T> getPageInfo(SearchPage<C> searchPage) {

        return getPageInfo(searchPage, null);
    }

    /**
     * 分页查询符合条件的列表
     *
     * @param searchPage
     * @param order
     * @return
     */
    @Transactional(readOnly = true)
    @Override
    public PageInfo<T> getPageInfo(SearchPage<C> searchPage, OrderCondition order) {

        C condition;
        if (searchPage == null || (condition = searchPage.getCondition()) == null) return null;

        PageHelper.startPage(searchPage.getPageIndex(), searchPage.getPageSize());
        List<T> list = mapper.selectByCondition(condition, order);
        PageInfo<T> pageInfo = new PageInfo<>(list);
        return pageInfo;
    }

    /**
     * 分页查询符合条件的列表
     *
     * @param searchPage
     * @return
     */
    @Transactional(readOnly = true)
    @Override
    public PageInfo<T> getPageInfoByList(SearchPage<List<C>> searchPage) {

        return getPageInfoByList(searchPage, null);
    }

    /**
     * 分页查询符合条件的列表
     *
     * @param searchPage
     * @param order
     * @return
     */
    @Transactional(readOnly = true)
    @Override
    public PageInfo<T> getPageInfoByList(SearchPage<List<C>> searchPage, OrderCondition order) {

        List<C> conditionList;
        if (searchPage == null || (conditionList = searchPage.getCondition()) == null) return null;

        PageHelper.startPage(searchPage.getPageIndex(), searchPage.getPageSize());
        List<T> list = mapper.selectByConditionList(conditionList, order);
        PageInfo<T> pageInfo = new PageInfo<>(list);
        return pageInfo;
    }

    /**
     * 查询数量
     *
     * @param condition
     * @return
     */
    @Transactional(readOnly = true)
    @Override
    public int countByCondition(C condition) {
        return mapper.countByCondition(condition);
    }

    /**
     * 查询数量
     *
     * @param conditionList
     * @return
     */
    @Transactional(readOnly = true)
    @Override
    public int countByConditionList(List<C> conditionList) {
        return mapper.countByConditionList(conditionList);
    }
}
