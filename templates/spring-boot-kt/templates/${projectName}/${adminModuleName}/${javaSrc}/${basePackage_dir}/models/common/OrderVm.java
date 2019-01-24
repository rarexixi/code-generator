package ${basePackage}.models.common;

import ${baseCommonPackage}.model.OrderCondition;

public interface OrderVm<T extends OrderCondition> extends OrderCondition {

    default T getOrderCondition() {
        return null;
    }
}
