package org.emall.brand.model.request;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.diwayou.core.bean.Convertible;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * @author gaopeng 2021/2/24
 */
@Data
@NoArgsConstructor
public class BrandUpdateRequest implements Convertible {

    @NotNull(message = "id不能为空")
    private Integer id;

    @NotNull(message = "名称不能为空")
    @Size(message = "品牌名称长度范围1~50", min = 1, max = 50)
    private String name;

    @NotNull(message = "logo不能为空")
    private String logo;

    @Size(message = "商标注册证编号长度范围1~50", min = 1, max = 50)
    private String certificateCode;

    @Size(message = "品牌介绍长度范围1~300", min = 1, max = 300)
    private String desc;
}
