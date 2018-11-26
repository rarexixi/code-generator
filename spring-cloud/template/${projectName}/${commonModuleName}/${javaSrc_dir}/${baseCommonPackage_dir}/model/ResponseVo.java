package ${baseCommonPackage}.model;

import ${baseCommonPackage}.constant.OperationConstants;

import java.io.Serializable;

<#include "/include/java_copyright.ftl">
public class ResponseVo<T> implements Serializable {

    public ResponseVo() {
    }

    public ResponseVo(T result) {
        this(true, null, result);
    }

    public ResponseVo(OperationConstants constants) {
        this(false, constants.getMessage(), null);
    }

    public ResponseVo(OperationConstants constants, Object extData) {
        this(false, constants.getMessage(), null, extData);
    }

    public ResponseVo(String message) {
        this(false, message, null);
    }

    public ResponseVo(boolean success, String message, T result) {
        this(success, message, result, null);
    }

    public ResponseVo(boolean success, String message, T result, Object extData) {
        this.success = success;
        this.message = message;
        this.result = result;
        this.extData = extData;
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
    /**
     * 额外数据
     */
    private Object extData;

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

    public Object getExtData() {
        return extData;
    }

    public void setExtData(Object extData) {
        this.extData = extData;
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
