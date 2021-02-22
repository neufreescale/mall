package org.emall.area.model.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @author gaopeng 2021/2/23
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
public class AreaForViewDto {

    /**
     * 当前地址的code
     */
    private String code;

    /**
     * 当前地址对应中文名称，例如辽宁省大连市普兰店市
     */
    private String name;

    /**
     * 当前地址code解析出来每个层级的信息
     */
    private List<AreaDto> levels;

    /**
     * 当前地址code解析出来每个层级所有的地址信息
     */
    private List<List<AreaDto>> perLevelAll;
}
