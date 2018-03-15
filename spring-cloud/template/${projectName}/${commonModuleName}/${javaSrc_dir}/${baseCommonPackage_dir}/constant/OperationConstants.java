package ${baseCommonPackage}.constant;

/**
 * @author 郗世豪（xish@cloud-young.com）
 * @date 2017/12/05 10:32
 */
public enum OperationConstants {

    SYSTEM_ERROR(100000, "系统错误"),
    NOT_NULL(100001, "不能为空"),
    VALIDATION_NOT_PASS(100002, "验证不通过"),
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
