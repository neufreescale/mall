package org.emall.order;

import org.diwayou.core.service.ServiceInfo;
import org.springframework.stereotype.Service;

/**
 * @author gaopeng 2021/7/27
 */
@Service
public class OrderServiceInfo implements ServiceInfo {

    @Override
    public String name() {
        return "订单系统";
    }

    @Override
    public String info() {
        return "管理订单等相关信息";
    }
}
