package org.emall.search;

import org.diwayou.core.service.ServiceInfo;
import org.springframework.stereotype.Service;

/**
 * @author gaopeng 2021/7/27
 */
@Service
public class SearchServiceInfo implements ServiceInfo {

    @Override
    public String name() {
        return "搜索系统";
    }

    @Override
    public String info() {
        return "管理搜索等相关信息";
    }
}
