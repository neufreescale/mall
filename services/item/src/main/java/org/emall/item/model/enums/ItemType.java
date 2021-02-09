package org.emall.item.model.enums;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author gaopeng 2021/2/9
 */
@Getter
@AllArgsConstructor
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum ItemType {

    real(1, "实体商品"),
    virtual(2, "虚拟商品"),
    service(3, "服务商品"),
    ;

    private final int id;

    private final String name;
}
