package ${modulePackage}.persistence.mapper;

import ${commonPackage}.models.SelectCondition;
import ${commonPackage}.models.UpdateCondition;
import ${modulePackage}.model.BaseEntity;

import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface BaseMapper<T extends BaseEntity, TE extends T, MC extends UpdateCondition, SC extends SelectCondition> {

    /**
     * 添加
     *
     * @param entity 新增实体
     * @return 影响的行数
     */
    int insert(@Param("entity") T entity);

    /**
     * 根据条件删除
     *
     * @param condition 删除条件
     * @return 影响的行数
     */
    int delete(@Param("condition") MC condition);

    /**
     * 根据条件更新entity中不为空的字段
     *
     * @param entity    更新实体
     * @param condition 更新条件
     * @return 影响的行数
     */
    int update(@Param("entity") T entity, @Param("condition") MC condition);

    /**
     * 根据条件查询结果
     *
     * @param condition 查询条件
     * @return 查询结果
     */
    List<TE> select(@Param("condition") SC condition);

    /**
     * 根据条件查询数量
     *
     * @param condition 查询条件
     * @return 查询结果数量
     */
    int count(@Param("condition") SC condition);
}