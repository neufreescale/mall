package org.emall.item.model.entity;

import java.math.BigDecimal;

/**
 * 商品
 *
 * @author gaopeng 2021/2/9
 */
public class Product {

    private Long id;

    private Long spuId;

    /**
     * 类目id
     */
    private Integer categoryId;

    /**
     * 店铺id
     */
    private Integer shopId;

    /**
     * 卖家id
     */
    private Long sellerId;

    /**
     * 标题
     */
    private String title;

    /**
     * 编码
     */
    private String code;

    /**
     * 销售价
     */
    private BigDecimal price;

    /**
     * 类型
     *
     * @see org.emall.item.model.enums.ItemType
     */
    private Integer type;

    /**
     * 外部id
     */
    private String outerId;

    /**
     * 状态
     */
    private Integer status;
}
