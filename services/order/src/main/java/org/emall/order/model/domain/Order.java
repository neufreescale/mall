package org.emall.order.model.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author gaopeng 2021/1/20
 */
@NoArgsConstructor
@Data
public class Order {

    private Long id;

    /**
     * 订单编号
     */
    private String code;

    /**
     * 订单状态
     */
    private Integer status;
}
