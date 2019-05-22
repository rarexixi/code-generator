package ${baseCommonPackage}.mapper;

import ${baseCommonPackage}.model.OrderCondition;

import ${basePackage}.models.common.BaseEntity;
import ${basePackage}.models.common.BaseCondition;

import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface BaseMapper<T extends BaseEntity, C extends BaseCondition> {

    /**
     * 添加
     *
     * @param entity
     * @return
     */
    int insert(@Param("entity") T entity);

    /**
     * 添加列表
     *
     * @param entityList
     * @return
     */
    int insertList(@Param("list") List<T> entityList);

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
     * @param entity
     * @param condition
     * @return
     */
    int updateByCondition(@Param("entity") T entity, @Param("condition") C condition);

    /**
     * 根据条件更新
     *
     * @param entity
     * @param conditionList
     * @return
     */
    int updateByConditionList(@Param("entity") T entity, @Param("conditionList") List<C> conditionList);

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
    List<T> selectByCondition(@Param("condition") C condition, @Param("order") OrderCondition order);

    /**
     * 查询
     *
     * @param conditionList
     * @return
     */
    List<T> selectByConditionList(@Param("conditionList") List<C> conditionList, @Param("order") OrderCondition order);

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
