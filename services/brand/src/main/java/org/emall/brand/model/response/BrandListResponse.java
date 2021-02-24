package org.emall.brand.model.response;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author gaopeng 2021/2/24
 */
@Data
@NoArgsConstructor
public class BrandListResponse {

    private Integer id;

    private String name;

    private String logo;

    private String certificateCode;
}
