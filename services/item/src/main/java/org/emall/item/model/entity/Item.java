package org.emall.item.model.entity;

/**
 * @author gaopeng 2021/2/9
 */
public class Item {

    private Long id;

    private String name;

    private Integer categoryId;

    /**
     * @see org.emall.item.model.enums.ItemType
     */
    private Integer type;
}
