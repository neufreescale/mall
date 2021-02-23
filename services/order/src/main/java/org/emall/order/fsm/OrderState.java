package org.emall.order.fsm;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author gaopeng 2021/1/20
 */
@Getter
@AllArgsConstructor
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum OrderState {
    Init(0, "状态机初始化"),
    New(1, "新建"),
    Paid(2, "已支付"),
    Confirmed(3, "审核通过"),
    Processing(4, "处理中"),
    Completed(5, "已完成"),
    Attention(6, "异常"),
    Returned(7, "已退货"),
    Cancelled(8, "已取消"),
    ;

    private final int id;

    private final String name;
}
