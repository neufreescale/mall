package org.emall.item;

import org.diwayou.core.service.ServiceInfo;
import org.springframework.stereotype.Service;

/**
 * @author gaopeng 2021/7/27
 */
@Service
public class ItemServiceInfo implements ServiceInfo {

    @Override
    public String name() {
        return "商品系统";
    }

    @Override
    public String info() {
        return "管理商品等相关信息";
    }
}
