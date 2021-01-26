package org.emall.order.fsm;

/**
 * @author gaopeng 2021/1/20
 */
public enum PaymentEvent {
    Init,
    ExecuteCharge,
    Paid,
    Returned,
    ChargeFailed,
    Pending,
    ReturnFailed
}
