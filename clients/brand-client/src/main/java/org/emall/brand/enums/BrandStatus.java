package org.emall.brand.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author gaopeng 2021/2/24
 */
@Getter
@AllArgsConstructor
public enum BrandStatus {

    normal(1, "正常"),
    deleted(2, "已删除");

    private final int id;

    private final String name;

    public static BrandStatus valueOf(int id) {
        for (BrandStatus status : values()) {
            if (status.getId() == id) {
                return status;
            }
        }

        throw new IllegalStateException("状态不正确");
    }
}
