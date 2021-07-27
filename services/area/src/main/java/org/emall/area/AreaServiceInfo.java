package org.emall.area;

import org.diwayou.core.service.ServiceInfo;
import org.springframework.stereotype.Service;

/**
 * @author gaopeng 2021/7/27
 */
@Service
public class AreaServiceInfo implements ServiceInfo {

    @Override
    public String name() {
        return "地址系统";
    }

    @Override
    public String info() {
        return "管理省市区等相关信息";
    }
}
