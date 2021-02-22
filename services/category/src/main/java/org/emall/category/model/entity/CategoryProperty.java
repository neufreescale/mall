package org.emall.category.model.entity;

/**
 * 类目属性
 *
 * @author gaopeng 2021/2/22
 */
public class CategoryProperty {

    private Integer id;

    /**
     * 类目id
     */
    private Integer categoryId;

    /**
     * 属性id
     */
    private Integer propertyId;

    /**
     * 属性名称的别名
     */
    private String propNameAlias;

    /**
     * 是否必填
     */
    private Boolean required;

    /**
     * 是否多选
     */
    private Boolean multiSelect;

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
