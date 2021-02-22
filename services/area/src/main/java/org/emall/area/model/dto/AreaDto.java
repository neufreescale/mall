package org.emall.area.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.emall.area.model.enums.AreaType;

/**
 * @author gaopeng 2021/2/23
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AreaDto {

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
