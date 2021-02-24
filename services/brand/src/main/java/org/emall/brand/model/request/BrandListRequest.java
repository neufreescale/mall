package org.emall.brand.model.request;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.diwayou.core.page.PageConvertible;

import javax.validation.constraints.Size;

/**
 * @author gaopeng 2021/2/24
 */
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class BrandListRequest extends PageConvertible {

    @Size(message = "品牌名称长度范围0~50", max = 50)
    private String name;
}
