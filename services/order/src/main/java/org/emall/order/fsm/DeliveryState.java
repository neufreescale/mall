package org.emall.order.fsm;

/**
 * @author gaopeng 2021/1/20
 */
public enum DeliveryState {
    Init,
    Delivered,
    Failed,
    Cancelled
}
