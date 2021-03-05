package org.emall.order.thirdparty.cart;

import org.emall.order.model.domain.OrderProduct;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

/**
 * @author gaopeng 2021/3/5
 */
@Component
public class CartManager {

    public List<OrderProduct> getSelected(Long buyerId) {
        OrderProduct orderProduct = new OrderProduct()
                .setProductId(1L)
                .setSkuId(1L)
                .setSellerId(2L)
                .setShopId(1)
                .setTitle("测试商品")
                .setPrice(BigDecimal.valueOf(100))
                .setQuantity(2);

        return Collections.singletonList(orderProduct);
    }
}
