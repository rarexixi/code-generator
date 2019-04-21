package ${baseCommonPackage}.model;

import ${baseCommonPackage}.constant.OperationConstants;

import lombok.*;
import java.io.Serializable;

@NoArgsConstructor
@Getter
@Setter
@ToString
public class ResultVo<T> implements Serializable {

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
}
