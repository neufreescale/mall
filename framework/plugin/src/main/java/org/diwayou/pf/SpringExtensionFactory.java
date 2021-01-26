package org.diwayou.pf;

import lombok.extern.slf4j.Slf4j;
import org.pf4j.ExtensionFactory;
import org.pf4j.Plugin;
import org.pf4j.PluginManager;
import org.pf4j.PluginWrapper;
import org.springframework.context.ApplicationContext;

/**
 * @author gaopeng 2021/1/15
 */
@Slf4j
public class SpringExtensionFactory implements ExtensionFactory {

    private final PluginManager pluginManager;

    private final boolean autowire;

    public SpringExtensionFactory(PluginManager pluginManager) {
        this(pluginManager, true);
    }

    public SpringExtensionFactory(PluginManager pluginManager, boolean autowire) {
        this.pluginManager = pluginManager;
        this.autowire = autowire;
    }

    @Override
    public <T> T create(Class<T> extensionClass) {
        T extension = createWithoutSpring(extensionClass);
        if (autowire && extension != null) {
            PluginWrapper pluginWrapper = pluginManager.whichPlugin(extensionClass);
            if (pluginWrapper != null) {
                Plugin plugin = pluginWrapper.getPlugin();
                if (plugin instanceof SpringPlugin) {
                    ApplicationContext pluginContext = ((SpringPlugin) plugin).getApplicationContext();
                    pluginContext.getAutowireCapableBeanFactory().autowireBean(extension);
                } else if (this.pluginManager instanceof SpringPluginManager) {
                    SpringPluginManager springPluginManager = (SpringPluginManager) this.pluginManager;
                    ApplicationContext pluginContext = springPluginManager.getApplicationContext();
                    pluginContext.getAutowireCapableBeanFactory().autowireBean(extension);
                }
            }
        }

        return extension;
    }

    protected <T> T createWithoutSpring(Class<T> extensionClass) {
        try {
            return extensionClass.newInstance();
        } catch (Exception e) {
            log.error("", e);
        }

        return null;
    }
}
