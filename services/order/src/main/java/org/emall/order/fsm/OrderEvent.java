package org.emall.order.fsm;

/**
 * @author gaopeng 2021/1/28
 */
public enum OrderEvent {
    /**
     * 创建订单
     */
    Create,

    /**
     * 支付
     */
    Pay,

    /**
     * 审核通过
     */
    Confirm,

    /**
     * 发货或者其它操作
     */
    Process,

    /**
     * 完成
     */
    Complete,
    Attention,
    Returned,

    /**
     * 取消
     */
    Cancel
}
