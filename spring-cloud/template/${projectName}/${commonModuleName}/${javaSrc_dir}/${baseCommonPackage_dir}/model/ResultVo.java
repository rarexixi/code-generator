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

    public ResultVo(OperationConstants constants, Object extData) {
        this(false, constants.getCode(), constants.getMessage(), null, extData);
    }

    public ResultVo(int code, String message) {
        this(false, code, message, null);
    }

    public ResultVo(boolean success, int code, String message, T result) {
        this(success, code, message, result, null);
    }

    public ResultVo(boolean success, int code, String message, T result, Object extData) {
        this.success = success;
        this.code = code;
        this.message = message;
        this.result = result;
        this.extData = extData;
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

    public Object getExtData() {
        return extData;
    }

    public void setExtData(Object extData) {
        this.extData = extData;
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
