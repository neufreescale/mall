package org.emall.category.model.enums;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author gaopeng 2021/2/22
 */
@Getter
@AllArgsConstructor
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum PropertyType {

    KEY(1, "关键属性"),
    SALE(2, "销售属性"),
    NORMAL(3, "一般属性"),
    PRODUCT(4, "商品属性")
    ;

    private final int id;

    private final String name;
}
