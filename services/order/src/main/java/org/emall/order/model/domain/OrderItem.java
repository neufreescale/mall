package org.emall.order.model.domain;

import java.math.BigDecimal;

/**
 * @author gaopeng 2021/1/20
 */
public class OrderItem {

    /**
     * 商品id
     */
    private Long id;

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
