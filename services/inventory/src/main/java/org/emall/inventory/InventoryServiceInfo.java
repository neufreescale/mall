package org.emall.inventory;

import org.diwayou.core.service.ServiceInfo;
import org.springframework.stereotype.Service;

/**
 * @author gaopeng 2021/7/27
 */
@Service
public class InventoryServiceInfo implements ServiceInfo {

    @Override
    public String name() {
        return "库存系统";
    }

    @Override
    public String info() {
        return "管理库存等相关信息";
    }
}
