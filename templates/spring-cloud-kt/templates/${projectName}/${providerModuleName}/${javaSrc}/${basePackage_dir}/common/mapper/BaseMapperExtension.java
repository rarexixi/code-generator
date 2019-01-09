package org.xi.quick.common.mapper;

import org.apache.ibatis.annotations.Param;
import org.xi.common.model.OrderCondition;
import org.xi.quick.models.common.BaseCondition;
import org.xi.quick.models.common.BaseEntity;

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
