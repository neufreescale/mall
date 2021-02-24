package org.emall.brand.model.request;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

/**
 * @author gaopeng 2021/2/24
 */
@Data
@NoArgsConstructor
public class BrandDeleteRequest {

    @NotNull(message = "id不能为空")
    private Integer id;
}
