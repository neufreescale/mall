package org.emall.pay.mq;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author gaopeng 2021/3/5
 */
@Data
@NoArgsConstructor
public class PaidMessage {

    private String orderId;
}
