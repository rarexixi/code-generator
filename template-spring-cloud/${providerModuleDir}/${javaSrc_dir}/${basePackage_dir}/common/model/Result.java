package ${basePackage}.common.model;

import com.cloudyoung.config.common.constant.OperationConstants;

import java.io.Serializable;

/**
 * @author 郗世豪（xish@cloud-young.com）
 * @date 2017/11/28 17:53
 */
public class Result<T> implements Serializable {

    public Result() {

    }

    public Result(T result) {
        this(true, 0, null, result);
    }

    public Result(OperationConstants constants) {
        this(false, constants.getCode(), constants.getMessage(), null);
    }

    public Result(int code, String message) {
        this(false, code, message, null);
    }

    public Result(boolean success, int code, String message, T result) {
        this.success = success;
        this.code = code;
        this.message = message;
        this.result = result;
    }

    /**
     * 是否成功
     */
    private boolean success;
    /**
     * 编码
     */
    private int code;
    /**
     * 信息
     */
    private String message;
    /**
     * 返回数据
     */
    private T result;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return "Result{" +
                "success=" + success +
                ", code=" + code +
                ", message='" + message + '\'' +
                ", result=" + result +
                '}';
    }
}
