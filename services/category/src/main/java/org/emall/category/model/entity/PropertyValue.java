package org.emall.category.model.entity;

/**
 * 属性值
 *
 * @author gaopeng 2021/2/22
 */
public class PropertyValue {

    /**
     * 属性值id
     */
    private Integer id;

    /**
     * 子属性id
     */
    private Integer childPropertyId;

    /**
     * 属性id
     */
    private Integer propertyId;

    /**
     * 属性值
     */
    private String value;

    /**
     * 扩展字段，JSON
     */
    private String features;

    /**
     * 排序值
     */
    private Integer sortOrder;

    /**
     * 状态
     */
    private Integer status;
}
