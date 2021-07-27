package org.emall.pay;

import org.diwayou.core.service.ServiceInfo;
import org.springframework.stereotype.Service;

/**
 * @author gaopeng 2021/7/27
 */
@Service
public class PayServiceInfo implements ServiceInfo {

    @Override
    public String name() {
        return "支付系统";
    }

    @Override
    public String info() {
        return "管理支付等相关信息";
    }
}
