package org.emall.order.model.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

/**
 * @author gaopeng 2021/1/20
 */
@NoArgsConstructor
@Data
@Accessors(chain = true)
public class Order {

    private Long id;

    /**
     * 订单编号
     */
    private String code;

    private Long sellerId;

    private Long buyerId;

    private Integer type;

    private Integer source;

    private Long parentId;

    private BigDecimal amount;

    /**
     * 订单状态
     */
    private Integer status;
}
