package org.emall.promotion;

import org.diwayou.core.service.ServiceInfo;
import org.springframework.stereotype.Service;

/**
 * @author gaopeng 2021/7/27
 */
@Service
public class PromotionServiceInfo implements ServiceInfo {

    @Override
    public String name() {
        return "促销系统";
    }

    @Override
    public String info() {
        return "管理促销等相关信息";
    }
}
