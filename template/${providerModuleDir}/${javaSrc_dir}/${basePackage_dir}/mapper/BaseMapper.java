package ${basePackage}.mapper;

import java.io.Serializable;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface BaseMapper<T extends Serializable> {

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
     * 根据主键删除
     *
     * @param pk
     * @return
     */
    int deleteByPk(@Param("pk") Object pk);

    /**
     * 根据条件删除
     *
     * @param condition
     * @return
     */
    int deleteByCondition(@Param("condition") T condition);

    /**
     * 根据主键更新
     *
     * @param model
     * @return
     */
    int updateByPk(@Param("model") T model);

    /**
     * 根据条件更新
     *
     * @param model
     * @param condition
     * @return
     */
    int updateByCondition(@Param("model") T model, @Param("condition") T condition);

    /**
     * 根据主键获取
     *
     * @param pk
     * @return
     */
    T getByPk(@Param("pk") Object pk);

    /**
     * 查询
     *
     * @param condition
     * @return
     */
    List<T> findByCondition(@Param("condition") T condition);

}
