package ${basePackage}.common.service.impl;

import ${basePackage}.common.model.OrderCondition;
import ${basePackage}.common.model.OrderSearch;
import ${basePackage}.common.model.OrderSearchPage;
import ${basePackage}.common.mapper.BaseMapper;
import ${basePackage}.common.mapper.BaseMapperExtension;
import ${basePackage}.common.service.BaseService;
import ${basePackage}.common.service.BaseServiceExtension;
import ${basePackage}.models.common.BaseCondition;
import ${basePackage}.models.common.BaseEntity;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public abstract class BaseServiceImpl<T extends BaseEntity, C extends BaseCondition, O extends OrderCondition, TE extends T, CE extends C>
        implements BaseService<T, C, O>, BaseServiceExtension<TE, CE, O> {

    protected BaseMapper<T, C> mapper;
    protected BaseMapperExtension<TE, CE, O> mapperExtension;

    /**
     * 添加
     *
     * @param entity
     * @return
     */
    @Override
    public int insert(T entity) {
        return mapper.insert(entity);
    }

    /**
     * 添加
     *
     * @param entityList
     * @return
     */
    @Override
    public int insert(List<T> entityList) {
        return mapper.insertList(entityList);
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
     * @param entity
     * @param condition
     * @return
     */
    @Override
    public int update(T entity, C condition) {
        return mapper.updateByCondition(entity, condition);
    }

    /**
     * 根据条件更新
     *
     * @param entity
     * @param conditionList
     * @return
     */
    @Override
    public int update(T entity, List<C> conditionList) {
        return mapper.updateByConditionList(entity, conditionList);
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

        List<T> list = mapper.selectByCondition(condition, null);
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
    public List<T> getListByConditionList(List<C> conditionList) {

        List<T> list = mapper.selectByConditionList(conditionList, null);
        return list;
    }

    /**
     * 查询符合条件的列表
     *
     * @param search
     * @return
     */
    @Transactional(readOnly = true)
    @Override
    public List<T> getList(OrderSearch<C, O> search) {

        List<T> list = mapper.selectByCondition(search.getCondition(), search.getOrder());
        return list;
    }

    /**
     * 查询符合条件的列表
     *
     * @param search
     * @return
     */
    @Transactional(readOnly = true)
    @Override
    public List<T> getListByConditionList(OrderSearch<List<C>, O> search) {

        List<T> list = mapper.selectByConditionList(search.getCondition(), search.getOrder());
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
    public PageInfo<T> getPageInfo(OrderSearchPage<C, O> searchPage) {

        C condition;
        if (searchPage == null || (condition = searchPage.getCondition()) == null) return null;

        PageHelper.startPage(searchPage.getPageIndex(), searchPage.getPageSize());
        List<T> list = mapper.selectByCondition(condition, searchPage.getOrder());
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
    public PageInfo<T> getPageInfoByConditionList(OrderSearchPage<List<C>, O> searchPage) {

        List<C> conditionList;
        if (searchPage == null || (conditionList = searchPage.getCondition()) == null) return null;

        PageHelper.startPage(searchPage.getPageIndex(), searchPage.getPageSize());
        List<T> list = mapper.selectByConditionList(conditionList, searchPage.getOrder());
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


    /**
     * 获取列表（不分页）
     *
     * @param search
     * @return
     */
    @Transactional(readOnly = true)
    @Override
    public List<TE> getExList(OrderSearch<CE, O> search) {

        if (search == null) return null;

        List<TE> list = mapperExtension.getExList(search.getCondition(), search.getOrder());
        return list;
    }

    /**
     * 分页查询
     *
     * @param searchPage
     * @return
     */
    @Transactional(readOnly = true)
    @Override
    public PageInfo<TE> getPageList(OrderSearchPage<CE, O> searchPage) {

        CE condition;
        if (searchPage == null || (condition = searchPage.getCondition()) == null) return null;

        PageHelper.startPage(searchPage.getPageIndex(), searchPage.getPageSize());
        List<TE> list = mapperExtension.getExList(condition, searchPage.getOrder());
        PageInfo<TE> pageInfo = new PageInfo<>(list);
        return pageInfo;
    }
}
