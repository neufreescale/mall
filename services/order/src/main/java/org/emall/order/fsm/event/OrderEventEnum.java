package org.emall.order.fsm.event;

/**
 * @author gaopeng 2021/1/28
 */
public enum OrderEventEnum {
    Confirm,
    Processing,
    Completed,
    Attention,
    Returned,
    Cancelled
}
