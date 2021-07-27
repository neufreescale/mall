package org.emall.coupon;

import org.diwayou.core.service.ServiceInfo;
import org.springframework.stereotype.Service;

/**
 * @author gaopeng 2021/7/27
 */
@Service
public class CouponServiceInfo implements ServiceInfo {

    @Override
    public String name() {
        return "优惠券系统";
    }

    @Override
    public String info() {
        return "管理优惠券等相关信息";
    }
}
