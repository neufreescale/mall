package org.emall.store;

import org.diwayou.core.service.ServiceInfo;
import org.springframework.stereotype.Service;

/**
 * @author gaopeng 2021/7/27
 */
@Service
public class StoreServiceInfo implements ServiceInfo {

    @Override
    public String name() {
        return "店铺系统";
    }

    @Override
    public String info() {
        return "管理店铺等相关信息";
    }
}
