package org.emall.user.model.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author gaopeng 2021/5/6
 */
@Getter
@AllArgsConstructor
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum SyncStatus {

    NO_SYNC(0, "未同步"),
    SYNC(1, "已同步"),
    FAIL(2, "同步失败")
    ;

    private int id;

    private String name;
}
