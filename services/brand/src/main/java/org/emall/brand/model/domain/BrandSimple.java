package org.emall.brand.model.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @author gaopeng 2021/2/24
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class BrandSimple {

    @EqualsAndHashCode.Include
    private Integer id;

    private String name;
}
