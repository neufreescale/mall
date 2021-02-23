package org.emall.order.model.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

/**
 * @author gaopeng 2021/1/20
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
public class Product {

    /**
     * 商品id
     */
    private Long productId;

    /**
     * skuId
     */
    private Long skuId;

    /**
     * 卖家id
     */
    private Long sellerId;

    /**
     * 店铺id
     */
    private Integer shopId;

    /**
     * 商品标题
     */
    private String title;

    /**
     * 商品价格
     */
    private BigDecimal price;

    /**
     * 数量
     */
    private Integer quantity;
}
