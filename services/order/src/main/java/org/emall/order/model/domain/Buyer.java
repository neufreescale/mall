package org.emall.order.model.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @author gaopeng 2021/2/23
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
public class Buyer {

    private Long userId;

    private String nickname;
}
