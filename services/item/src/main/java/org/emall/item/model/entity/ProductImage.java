package org.emall.item.model.entity;

/**
 * 商品图片
 *
 * @author gaopeng 2021/2/23
 */
public class ProductImage {

    private Long id;

    /**
     * 商品id
     */
    private Long productId;

    /**
     * 图片链接
     */
    private String url;

    /**
     * 状态
     */
    private Integer status;
}
