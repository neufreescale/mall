package org.emall.user.model.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.diwayou.core.bean.Convertible;

/**
 * @author gaopeng 2021/1/21
 */
@Data
@NoArgsConstructor
public class User implements Convertible {

    private Long id;

    private String name;
}
