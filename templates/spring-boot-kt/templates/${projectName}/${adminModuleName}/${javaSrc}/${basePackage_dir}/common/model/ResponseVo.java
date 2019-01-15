package ${basePackage}.common.model;

import ${basePackage}.common.constant.OperationConstants;

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

    public ResponseVo(boolean success, String msg) {
        this(success, msg, null, null);
    }

    public ResponseVo(boolean success, String msg, T result) {
        this(success, msg, result, null);
    }

    public ResponseVo(boolean success, String msg, T result, Object extData) {
        this.success = success;
        this.msg = msg;
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
    private String msg;
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

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
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
                ", msg='" + msg + "'" +
                ", result=" + result +
                '}';
    }
}
