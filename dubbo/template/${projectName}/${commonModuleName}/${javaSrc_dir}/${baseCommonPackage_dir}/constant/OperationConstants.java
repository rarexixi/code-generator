package ${baseCommonPackage}.constant;

<#include "/include/java_copyright.ftl">
public enum OperationConstants {

    SYSTEM_ERROR(100000, "系统错误"),
    NOT_NULL(100001, "不能为空"),
    PARAMETER_VALIDATION_FAILED(100002, "参数验证不通过"),
    SERVICE_NOT_AVAILABLE(100100, "服务提供方不存在");

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
