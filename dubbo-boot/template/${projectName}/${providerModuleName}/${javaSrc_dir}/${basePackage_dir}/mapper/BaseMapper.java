package ${basePackage}.mapper;

import ${basePackage}.condition.order.OrderCondition;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.io.Serializable;
import java.util.List;

public interface BaseMapper<T extends Serializable, C extends Serializable> {

    /**
     * 添加
     *
     * @param model
     * @return
     */
    int insert(@Param("model") T model);

    /**
     * 添加列表
     *
     * @param modelList
     * @return
     */
    int insertList(@Param("list") List<T> modelList);

    /**
     * 根据条件删除
     *
     * @param condition
     * @return
     */
    int deleteByCondition(@Param("condition") C condition);

    /**
     * 根据条件删除
     *
     * @param conditionList
     * @return
     */
    int deleteByConditionList(@Param("conditionList") List<C> conditionList);

    /**
     * 根据条件更新
     *
     * @param model
     * @param condition
     * @return
     */
    int updateByCondition(@Param("model") T model, @Param("condition") C condition);

    /**
     * 根据条件更新
     *
     * @param model
     * @param conditionList
     * @return
     */
    int updateByConditionList(@Param("model") T model, @Param("conditionList") List<C> conditionList);

    /**
     * 查询
     *
     * @param condition
     * @return
     */
    T getByCondition(@Param("condition") C condition);

    /**
     * 查询
     *
     * @param condition
     * @return
     */
    List<T> findByCondition(@Param("condition") C condition, @Param("order") OrderCondition order);

    /**
     * 查询
     *
     * @param conditionList
     * @return
     */
    List<T> findByConditionList(@Param("conditionList") List<C> conditionList, @Param("order") OrderCondition order);

    /**
     * 查询数量
     *
     * @param condition
     * @return
     */
    int countByCondition(@Param("condition") C condition);

    /**
     * 查询数量
     *
     * @param conditionList
     * @return
     */
    int countByConditionList(@Param("conditionList") List<C> conditionList);

}
