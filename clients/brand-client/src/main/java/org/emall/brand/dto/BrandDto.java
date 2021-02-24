package org.emall.brand.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author gaopeng 2021/2/24
 */
@Data
@NoArgsConstructor
public class BrandDto {

    private Integer id;

    /**
     * 名称
     */
    private String name;

    /**
     * logo
     */
    private String logo;
}
