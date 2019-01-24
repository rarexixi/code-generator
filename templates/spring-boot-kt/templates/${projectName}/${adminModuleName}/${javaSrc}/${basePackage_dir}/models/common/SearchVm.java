package ${basePackage}.models.common;


public interface SearchVm<T extends BaseCondition> {

    default T getConditionExtension() {
        return null;
    }
}
