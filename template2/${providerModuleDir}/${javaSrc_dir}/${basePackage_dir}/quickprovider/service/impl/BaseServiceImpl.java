package ${basePackage}.quickprovider.service.impl;

import ${basePackage}.condition.order.OrderCondition;
import ${basePackage}.quickprovider.service.BaseService;
import ${basePackage}.quickprovider.mapper.BaseMapper;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.List;

@Transactional
public class BaseServiceImpl<T extends Serializable, C extends Serializable> implements BaseService<T, C> {

    @Autowired
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
    public int insertList(List<T> models) {
        return mapper.insertList(models);
    }

    /**
     * 根据条件删除
     *
     * @param condition
     * @return
     */
    @Override
    public int deleteByCondition(C condition) {
        return mapper.deleteByCondition(condition);
    }

    /**
     * 根据条件更新
     *
     * @param model
     * @param condition
     * @return
     */
    @Override
    public int updateByCondition(T model, C condition) {
        return mapper.updateByCondition(model, condition);
    }

    /**
     * 查询
     *
     * @param condition
     * @param order
     * @param page
     * @return
     */
    @Transactional(readOnly = true)
    @Override
    public PageInfo<T> findByCondition(C condition, OrderCondition order, PageInfo page) {

        //先查询总数量
        PageHelper.startPage(page.getPageNum(), page.getPageSize());
        List<T> list = this.mapper.findByCondition(condition, order);
        PageInfo<T> pageInfo = new PageInfo<>(list);
        return pageInfo;
    }

}
