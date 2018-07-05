package ${baseCommonPackage}.model;

import java.io.Serializable;

<#include "/include/java_copyright.ftl">
public class ResponseVo<T> implements Serializable {

    public ResponseVo() {

    }

    public ResponseVo(T result) {
        this(true, null, result);
    }

    public ResponseVo(boolean success) {
        this(success, null, null);
    }

    public ResponseVo(String message) {
        this(false, message, null);
    }

    public ResponseVo(boolean success, String message, T result) {
        this.success = success;
        this.message = message;
        this.result = result;
    }

    /**
     * 是否成功
     */
    private boolean success;
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
        return "ResponseVo{" +
                "success=" + success +
                ", message='" + message + '\'' +
                ", result=" + result +
                '}';
    }
}
