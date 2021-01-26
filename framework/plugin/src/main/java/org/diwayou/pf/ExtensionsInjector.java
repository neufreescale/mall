package org.diwayou.pf;

import lombok.extern.slf4j.Slf4j;
import org.pf4j.PluginManager;
import org.pf4j.PluginWrapper;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;

import java.util.List;
import java.util.Set;

/**
 * @author gaopeng 2021/1/15
 */
@Slf4j
public class ExtensionsInjector {

    protected final PluginManager pluginManager;

    protected final ConfigurableListableBeanFactory beanFactory;

    public ExtensionsInjector(PluginManager pluginManager, ConfigurableListableBeanFactory beanFactory) {
        this.pluginManager = pluginManager;
        this.beanFactory = beanFactory;
    }

    public void injectExtensions() {
        // add extensions from classpath (non plugin)
        Set<String> extensionClassNames = pluginManager.getExtensionClassNames(null);
        for (String extensionClassName : extensionClassNames) {
            try {
                log.debug("Register extension '{}' as bean", extensionClassName);
                Class<?> extensionClass = getClass().getClassLoader().loadClass(extensionClassName);
                registerExtension(extensionClass);
            } catch (ClassNotFoundException e) {
                log.error("Load extension '{}' fail", extensionClassName, e);
            }
        }

        // add extensions for each started plugin
        List<PluginWrapper> startedPlugins = pluginManager.getStartedPlugins();
        for (PluginWrapper plugin : startedPlugins) {
            log.debug("Registering extensions of the plugin '{}' as beans", plugin.getPluginId());
            extensionClassNames = pluginManager.getExtensionClassNames(plugin.getPluginId());
            for (String extensionClassName : extensionClassNames) {
                try {
                    log.debug("Register extension '{}' as bean", extensionClassName);
                    Class<?> extensionClass = plugin.getPluginClassLoader().loadClass(extensionClassName);
                    registerExtension(extensionClass);
                } catch (ClassNotFoundException e) {
                    log.error("Load plugin '{}' extension '{}' fail", plugin.getPluginId(), extensionClassName, e);
                }
            }
        }
    }

    /**
     * Register an extension as bean.
     * Current implementation register extension as singleton using {@code beanFactory.registerSingleton()}.
     * The extension instance is created using {@code pluginManager.getExtensionFactory().create(extensionClass)}.
     * The bean name is the extension class name.
     * Override this method if you wish other register strategy.
     */
    protected void registerExtension(Class<?> extensionClass) {
        Object extension = pluginManager.getExtensionFactory().create(extensionClass);

        beanFactory.registerSingleton(extension.getClass().getName(), extension);
    }
}
