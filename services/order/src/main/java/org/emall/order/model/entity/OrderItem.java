package org.emall.order.model.entity;

import java.math.BigDecimal;

/**
 * @author gaopeng 2021/1/20
 */
public class OrderItem {

    private Long id;

    /**
     * 订单id
     */
    private Long orderId;

    /**
     * 商品id
     */
    private Long productId;

    /**
     * 商品名称
     */
    private String name;

    /**
     * 商品价格
     */
    private BigDecimal price;

    /**
     * sku
     */
    private Long skuId;

    /**
     * 数量
     */
    private Integer quantity;
}
