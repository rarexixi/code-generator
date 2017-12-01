package ${basePackage}.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import org.xi.filemanager.mapper.BaseMapper;

import java.io.Serializable;
import java.util.List;

@Transactional
public class BaseServiceImpl<T extends Serializable, C extends Serializable> implements BaseMapper<T, C> {

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
     * @return
     */
    @Transactional(readOnly = true)
    @Override
    public List<T> findByCondition(C condition) {
        return this.mapper.findByCondition(condition);
    }

}
