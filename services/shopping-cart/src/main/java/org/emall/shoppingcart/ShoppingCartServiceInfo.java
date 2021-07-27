package org.emall.shoppingcart;

import org.diwayou.core.service.ServiceInfo;
import org.springframework.stereotype.Service;

/**
 * @author gaopeng 2021/7/27
 */
@Service
public class ShoppingCartServiceInfo implements ServiceInfo {

    @Override
    public String name() {
        return "购物车系统";
    }

    @Override
    public String info() {
        return "管理购物车等相关信息";
    }
}
