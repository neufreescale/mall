package org.emall.brand;

import org.diwayou.core.service.ServiceInfo;
import org.springframework.stereotype.Service;

/**
 * @author gaopeng 2021/7/27
 */
@Service
public class BrandServiceInfo implements ServiceInfo {

    @Override
    public String name() {
        return "品牌系统";
    }

    @Override
    public String info() {
        return "管理品牌等相关信息";
    }
}
