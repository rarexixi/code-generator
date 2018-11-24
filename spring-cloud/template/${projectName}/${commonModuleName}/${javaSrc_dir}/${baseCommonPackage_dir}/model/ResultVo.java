package ${baseCommonPackage}.model;

import ${baseCommonPackage}.constant.OperationConstants;

import java.io.Serializable;

<#include "/include/java_copyright.ftl">
public class ResultVo<T> implements Serializable {

    public ResultVo() {

    }

    public ResultVo(T result) {
        this(true, 0, null, result);
    }

    public ResultVo(OperationConstants constants) {
        this(false, constants.getCode(), constants.getMessage(), null);
    }

    public ResultVo(int code, String message) {
        this(false, code, message, null);
    }

    public ResultVo(boolean success, int code, String message, T result) {
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
        return "ResultVo{" +
                "success=" + success +
                ", code=" + code +
                ", message='" + message + '\'' +
                ", result=" + result +
                '}';
    }
}
