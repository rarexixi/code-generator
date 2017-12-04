package ${basePackage}.quickprovider.service.impl;

import ${paginationFullClass};

import ${basePackage}.condition.order.OrderCondition;
import ${basePackage}.service.BaseService;
import ${basePackage}.mapper.BaseMapper;

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
        return this.mapper.insert(model);
    }

    /**
     * 添加
     *
     * @param models
     * @return
     */
    @Override
    public int insertList(List<T> models) {
        return this.mapper.insertList(models);
    }

    /**
     * 根据条件删除
     *
     * @param condition
     * @return
     */
    @Override
    public int deleteByCondition(C condition) {
        return this.mapper.deleteByCondition(condition);
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
        return this.mapper.updateByCondition(model, condition);
    }

    /**
     * 查询
     *
     * @param condition
     * @param order
     * @param pagination
     * @return
     */
    @Transactional(readOnly = true)
    @Override
    public ${paginationClass}<T> findByCondition(C condition, OrderCondition order, ${paginationClass} pagination) {

        //先查询总数量
        PageHelper.startPage(pagination.getPage(), pagination.getPageSize());
        //分页查询数据
        List<T> list = this.mapper.findByCondition(condition, order);
        PageInfo page = new PageInfo(list);
        pagination.setContent(list);
        pagination.setDataNumber(page.getTotal());

        return pagination;
    }

}
