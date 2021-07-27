package org.emall.category;

import org.diwayou.core.service.ServiceInfo;
import org.springframework.stereotype.Service;

/**
 * @author gaopeng 2021/7/27
 */
@Service
public class CategoryServiceInfo implements ServiceInfo {

    @Override
    public String name() {
        return "分类系统";
    }

    @Override
    public String info() {
        return "管理分类等相关信息";
    }
}
