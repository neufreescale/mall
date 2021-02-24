package org.diwayou.core.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.diwayou.core.exception.CustomException;

/**
 * @author gaopeng 2021/2/24
 */
@Getter
@AllArgsConstructor
public enum Status {

    Normal(1, "正常"),
    Deleted(2, "已删除"),
    Draft(3, "草稿"),
    Pending(4, "待审核"),
    Processing(5, "进行中"),
    Completed(6, "已完成"),
    Rejected(7, "审核不通过"),
    ;

    private final int id;

    private final String name;

    public static Status valueOf(int id) {
        for (Status status : values()) {
            if (status.getId() == id) {
                return status;
            }
        }

        throw new CustomException("状态不正确");
    }
}
