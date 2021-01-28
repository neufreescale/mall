package org.emall.order.fsm.state;

/**
 * @author gaopeng 2021/1/20
 */
public enum OrderState {
    Init,
    Confirmed,
    Processing,
    Completed,
    Attention,
    Returned,
    Cancelled
}
