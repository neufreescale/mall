package org.emall.user;

import org.diwayou.core.service.ServiceInfo;
import org.springframework.stereotype.Service;

/**
 * @author gaopeng 2021/7/27
 */
@Service
public class UserServiceInfo implements ServiceInfo {

    @Override
    public String name() {
        return "用户系统";
    }

    @Override
    public String info() {
        return "管理用户相关信息";
    }
}
