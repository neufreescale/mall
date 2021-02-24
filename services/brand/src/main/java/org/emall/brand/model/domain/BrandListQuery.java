package org.emall.brand.model.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.diwayou.core.page.PageQuery;

/**
 * @author gaopeng 2021/2/24
 */
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class BrandListQuery extends PageQuery {

    private String name;
}
