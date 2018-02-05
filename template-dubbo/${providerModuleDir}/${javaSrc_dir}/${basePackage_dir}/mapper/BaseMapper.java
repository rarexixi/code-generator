package ${basePackage}.mapper;

import ${basePackage}.condition.order.OrderCondition;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.io.Serializable;
import java.util.List;

@Mapper
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
     * 根据条件更新
     *
     * @param model
     * @param condition
     * @return
     */
    int updateByCondition(@Param("model") T model, @Param("condition") C condition);

    /**
     * 查询
     *
     * @param condition
     * @return
     */
    List<T> findByCondition(@Param("condition") C condition, @Param("order") OrderCondition order);

}
