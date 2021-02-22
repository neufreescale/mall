package org.emall.area.model.entity;

import lombok.Data;
import org.diwayou.core.bean.Convertible;
import org.emall.area.model.enums.AreaType;

/**
 * @author gaopeng 2021/2/23
 */
@Data
public class Area implements Convertible {

    private Integer id;

    /**
     * 编码，12位
     */
    private String code;

    /**
     * 名称
     */
    private String name;

    /**
     * 区域类型
     */
    private AreaType type;

    /**
     * 父区域
     */
    private Integer parentId;
}
