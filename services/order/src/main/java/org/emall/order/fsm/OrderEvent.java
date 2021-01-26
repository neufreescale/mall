package org.emall.order.fsm;

/**
 * @author gaopeng 2021/1/20
 */
public enum OrderEvent {
    Confirm,
    Processing,
    Completed,
    Attention,
    Returned,
    Cancelled
}
