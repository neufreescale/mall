package org.emall.item.model.entity;

import java.math.BigDecimal;

/**
 * @author gaopeng 2021/2/23
 */
public class Sku {

    private Long id;

    /**
     * 商品id
     */
    private Long productId;

    /**
     * 销售属性集合
     */
    private String saleProperty;

    /**
     * 销售价
     */
    private BigDecimal price;

    /**
     * 状态
     */
    private Integer status;
}
