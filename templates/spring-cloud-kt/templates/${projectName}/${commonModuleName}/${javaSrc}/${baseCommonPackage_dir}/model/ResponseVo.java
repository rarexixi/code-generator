package ${baseCommonPackage}.model;

import ${baseCommonPackage}.constant.OperationConstants;

import lombok.*;
import java.io.Serializable;

@NoArgsConstructor
@Getter
@Setter
@ToString
public class ResponseVo<T> implements Serializable {

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
}
