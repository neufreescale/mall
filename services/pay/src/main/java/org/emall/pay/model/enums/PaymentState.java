package org.emall.pay.model.enums;

/**
 * @author gaopeng 2021/1/20
 */
public enum PaymentState {
    Init,
    ExecuteCharge,
    Paid,
    Returned,
    ChargeFailed,
    Pending,
    ReturnFailed
}
