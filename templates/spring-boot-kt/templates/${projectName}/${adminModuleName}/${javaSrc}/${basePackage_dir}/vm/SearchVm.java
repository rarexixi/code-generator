package ${basePackage}.vm;

import ${basePackage}.models.common.BaseCondition;

public interface SearchVm<T extends BaseCondition> {

    default T getConditionExtension() {
        return null;
    }
}
