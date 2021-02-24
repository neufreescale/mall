package org.emall.brand.model.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.diwayou.core.bean.Convertible;
import org.emall.brand.enums.BrandStatus;

/**
 * @author gaopeng 2021/2/22
 */
@Data
@NoArgsConstructor
public class Brand implements Convertible {

    private Integer id;

    /**
     * 名称
     */
    private String name;

    /**
     * logo
     */
    private String logo;

    /**
     * 商标注册证编号
     */
    private String certificateCode;

    /**
     * 品牌介绍
     */
    private String desc;

    /**
     * 状态
     * @see BrandStatus
     */
    private Integer status;
}
