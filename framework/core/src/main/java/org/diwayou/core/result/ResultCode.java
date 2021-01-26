package org.diwayou.core.result;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author gaopeng 2021/1/15
 */
@Getter
@AllArgsConstructor
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum ResultCode {

    Ok(0, "成功"),
    Error(-1, "失败"),
    ParameterError(-2, "参数有误"),
    ;

    private int code;

    private String msg;
}
