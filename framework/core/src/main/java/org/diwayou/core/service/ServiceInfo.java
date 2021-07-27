package org.diwayou.core.service;

/**
 * 获取子模块信息，启动的时候打印
 *
 * @author gaopeng 2021/7/27
 */
public interface ServiceInfo {

    /**
     * 服务名称
     */
    String name();

    /**
     * 服务简介
     */
    String info();
}
