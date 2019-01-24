package ${baseCommonPackage}.constant;

<#include "/include/java_copyright.ftl">
public enum OperationConstants {

    SYSTEM_ERROR(100000, "系统错误"),
    SERVICE_NOT_AVAILABLE(100100, "服务提供方不存在"),
    NOT_NULL_OR_EMPTY(200101, "不能为空"),
    PARAMETER_VALIDATION_FAILED(200102, "参数验证不通过");

    private String message;
    private int code;

    OperationConstants(int code, String message) {
        this.message = message;
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public int getCode() {
        return code;
    }

    @Override
    public String toString() {
        return "OperationConstants{" +
                "message='" + message + '\'' +
                ", code=" + code +
                '}';
    }
}
