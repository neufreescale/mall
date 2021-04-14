package org.emall.order.model.request;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.emall.order.validation.Insert;
import org.emall.order.validation.Phone;

/**
 * @author gaopeng 2021/3/8
 */
@Data
@NoArgsConstructor
public class OrderCreateRequest {

    @Phone(groups = Insert.class)
    private String phone;
}
