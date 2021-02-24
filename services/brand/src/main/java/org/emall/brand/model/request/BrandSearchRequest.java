package org.emall.brand.model.request;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.diwayou.core.page.PageConvertible;

/**
 * @author gaopeng 2021/2/24
 */
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class BrandSearchRequest extends PageConvertible {

    private String name;
}
