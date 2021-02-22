package org.emall.category.model.entity;

/**
 * 属性
 *
 * @author gaopeng 2021/2/22
 */
public class Property {

    /**
     * 属性id
     */
    private Integer id;

    /**
     * 名称
     */
    private String name;

    /**
     * 属性类型
     *
     * @see org.emall.category.model.enums.PropertyType
     */
    private Integer type;

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
