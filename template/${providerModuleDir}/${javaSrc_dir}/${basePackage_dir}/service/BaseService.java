package ${basePackage}.service;

import java.io.Serializable;
import java.util.List;

public interface BaseService<T extends Serializable> {

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
    int insertList(List<T> models);

    /**
     * 根据主键删除
     *
     * @param pk
     * @return
     */
    int deleteByPk(Object pk);

    /**
     * 根据条件删除
     *
     * @param condition
     * @return
     */
    int deleteByCondition(T condition);

    /**
     * 根据主键更新
     *
     * @param model
     * @return
     */
    int updateByPk(T model);

    /**
     * 根据条件更新
     *
     * @param model
     * @param condition
     * @return
     */
    int updateByCondition(T model, T condition);

    /**
     * 根据主键获取
     *
     * @param pk
     * @return
     */
    T getByPk(Object pk);

    /**
     * 查询
     *
     * @param condition
     * @return
     */
    List<T> findByCondition(T condition);

}
