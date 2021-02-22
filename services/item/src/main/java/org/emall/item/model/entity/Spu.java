package org.emall.item.model.entity;

import java.math.BigDecimal;

/**
 * @author gaopeng 2021/2/23
 */
public class Spu {

    private Long id;

    /**
     * 名称
     */
    private String name;

    /**
     * 类目id
     */
    private Integer categoryId;

    /**
     * 市场价
     */
    private BigDecimal price;

    /**
     * 关键属性
     */
    private String keyProperty;

    /**
     * 不变属性
     */
    private String immutableProperty;

    /**
     * 扩展字段，JSON
     */
    private String features;

    /**
     * 状态
     */
    private Integer status;
}
