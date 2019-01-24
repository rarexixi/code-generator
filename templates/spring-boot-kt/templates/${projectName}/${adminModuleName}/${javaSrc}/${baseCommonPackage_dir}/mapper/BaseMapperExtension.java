package ${baseCommonPackage}.mapper;

import org.apache.ibatis.annotations.Param;
import ${baseCommonPackage}.model.OrderCondition;
import ${basePackage}.models.common.BaseCondition;
import ${basePackage}.models.common.BaseEntity;

import java.util.List;

public interface BaseMapperExtension<T extends BaseEntity, C extends BaseCondition, O extends OrderCondition> {

    /**
     * 查询
     *
     * @param condition
     * @param order
     * @return
     */
    List<T> getExList(@Param("condition") C condition, @Param("order") O order);
}
