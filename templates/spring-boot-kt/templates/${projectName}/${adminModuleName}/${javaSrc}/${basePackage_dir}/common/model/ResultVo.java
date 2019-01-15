package ${basePackage}.common.model;

import ${basePackage}.common.constant.OperationConstants;

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

    public ResultVo(int code, String msg) {
        this(false, code, msg, null);
    }

    public ResultVo(boolean success, int code, String msg, T result) {
        this(success, code, msg, result, null);
    }

    public ResultVo(boolean success, int code, String msg, T result, Object extData) {
        this.success = success;
        this.code = code;
        this.msg = msg;
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

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
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
        return "ResultVo{" +
                "success=" + success +
                ", code=" + code +
                ", msg='" + msg + "'" +
                ", result=" + result +
                '}';
    }
}
