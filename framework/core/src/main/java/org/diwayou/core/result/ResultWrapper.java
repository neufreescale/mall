package org.diwayou.core.result;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author gaopeng 2021/1/15
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ResultWrapper {

    private int code;

    private String msg;

    private Object data;

    public static ResultWrapper success(Object data) {
        return new ResultWrapper(ResultCode.Ok, data);
    }

    public static ResultWrapper success() {
        return new ResultWrapper(ResultCode.Ok);
    }

    public static ResultWrapper fail(ResultCode resultCode) {
        return new ResultWrapper(resultCode);
    }

    public static ResultWrapper fail(String msg) {
        return new ResultWrapper(ResultCode.Error.getCode(), msg);
    }

    public static ResultWrapper fail() {
        return new ResultWrapper(ResultCode.Error);
    }

    private ResultWrapper(ResultCode resultCode) {
        this(resultCode.getCode(), resultCode.getMsg());
    }

    private ResultWrapper(int code, String msg) {
        this(code, msg, null);
    }

    private ResultWrapper(ResultCode resultCode, Object data) {
        this(resultCode.getCode(), resultCode.getMsg(), data);
    }
}
