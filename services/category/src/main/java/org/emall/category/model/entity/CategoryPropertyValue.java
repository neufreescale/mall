package org.emall.category.model.entity;

/**
 * 类目属性值
 *
 * @author gaopeng 2021/2/23
 */
public class CategoryPropertyValue {

    private Integer id;

    /**
     * 类目属性id
     */
    private Integer catPropId;

    /**
     * 类目id
     */
    private Integer categoryId;

    /**
     * 属性id
     */
    private Integer propertyId;

    /**
     * 属性值id
     */
    private Integer propValueId;

    /**
     * 属性值别名
     */
    private String propValueAlias;

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
