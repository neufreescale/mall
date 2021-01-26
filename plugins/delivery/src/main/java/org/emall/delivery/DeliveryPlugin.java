package org.emall.delivery;

import lombok.extern.slf4j.Slf4j;
import org.diwayou.pf.SpringPluginManager;
import org.pf4j.Plugin;
import org.pf4j.PluginWrapper;
import org.springframework.context.support.GenericApplicationContext;

/**
 * @author gaopeng 2021/1/15
 */
@Slf4j
public class DeliveryPlugin extends Plugin {

    private SpringPluginManager pluginManager;

    private GenericApplicationContext applicationContext;

    public DeliveryPlugin(PluginWrapper wrapper) {
        super(wrapper);
        this.pluginManager = (SpringPluginManager) wrapper.getPluginManager();
        this.applicationContext = (GenericApplicationContext) this.pluginManager.getApplicationContext();
    }

    @Override
    public void start() {
        log.info("Delivery plugin start");
    }

    @Override
    public void stop() {
        log.info("Delivery plugin stop");
    }
}
