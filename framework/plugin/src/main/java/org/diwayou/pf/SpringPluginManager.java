package org.diwayou.pf;

import org.apache.commons.lang3.StringUtils;
import org.pf4j.DefaultExtensionFinder;
import org.pf4j.DefaultPluginManager;
import org.pf4j.ExtensionFactory;
import org.pf4j.ExtensionFinder;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.nio.file.Paths;

/**
 * @author gaopeng 2021/1/15
 */
public class SpringPluginManager extends DefaultPluginManager implements ApplicationContextAware, BeanFactoryPostProcessor {

    private ApplicationContext applicationContext;

    private PluginConfiguration configuration;

    public SpringPluginManager(PluginConfiguration configuration) {
        super();

        initConfig(configuration);
    }

    public SpringPluginManager(String pluginsRoot, PluginConfiguration configuration) {
        super(Paths.get(pluginsRoot));

        initConfig(configuration);
    }

    private void initConfig(PluginConfiguration configuration) {
        this.configuration = configuration;

        if (StringUtils.isNotBlank(configuration.getSystemVersion())) {
            setSystemVersion(configuration.getSystemVersion());
        }
    }

    @Override
    protected ExtensionFinder createExtensionFinder() {
        DefaultExtensionFinder extensionFinder = (DefaultExtensionFinder) super.createExtensionFinder();
        extensionFinder.add(new DiwayouServiceProviderExtensionFinder(this));

        return extensionFinder;
    }

    @Override
    protected ExtensionFactory createExtensionFactory() {
        return new SpringExtensionFactory(this);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    public ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        loadPlugins();
        startPlugins();

        ExtensionsInjector extensionsInjector = new ExtensionsInjector(this, beanFactory);
        extensionsInjector.injectExtensions();
    }
}
