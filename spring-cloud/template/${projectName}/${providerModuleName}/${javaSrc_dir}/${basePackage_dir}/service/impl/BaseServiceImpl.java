package ${basePackage}.service.impl;

import ${baseCommonPackage}.mapper.BaseMapper;
import ${baseCommonPackage}.model.OrderCondition;
import ${baseCommonPackage}.model.SearchPage;
import ${basePackage}.service.BaseService;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.List;

@Transactional
public abstract class BaseServiceImpl<T extends Serializable, C extends Serializable> implements BaseService<T, C> {

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
     * @param condition
     * @param page
     * @return
     */
    @Transactional(readOnly = true)
    @Override
    public PageInfo<T> getPageInfo(C condition, SearchPage page) {

        return getPageInfo(condition, null, page);
    }

    /**
     * 分页查询符合条件的列表
     *
     * @param condition
     * @param order
     * @param page
     * @return
     */
    @Transactional(readOnly = true)
    @Override
    public PageInfo<T> getPageInfo(C condition, OrderCondition order, SearchPage page) {

        if (page == null) {
            PageHelper.startPage(SearchPage.DEFAULT_PAGE_INDEX, SearchPage.DEFAULT_PAGE_SIZE);
        } else {
            PageHelper.startPage(page.getPageIndex(), page.getPageSize());
        }
        List<T> list = mapper.selectByCondition(condition, order);
        PageInfo<T> pageInfo = new PageInfo<>(list);
        return pageInfo;
    }

    /**
     * 分页查询符合条件的列表
     *
     * @param conditionList
     * @param page
     * @return
     */
    @Transactional(readOnly = true)
    @Override
    public PageInfo<T> getPageInfo(List<C> conditionList, SearchPage page) {

        return getPageInfo(conditionList, null, page);
    }

    /**
     * 分页查询符合条件的列表
     *
     * @param conditionList
     * @param order
     * @param page
     * @return
     */
    @Transactional(readOnly = true)
    @Override
    public PageInfo<T> getPageInfo(List<C> conditionList, OrderCondition order, SearchPage page) {

        if (page == null) {
            PageHelper.startPage(SearchPage.DEFAULT_PAGE_INDEX, SearchPage.DEFAULT_PAGE_SIZE);
        } else {
            PageHelper.startPage(page.getPageIndex(), page.getPageSize());
        }
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
