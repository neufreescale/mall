package org.diwayou.pf;

import com.google.common.collect.Sets;
import org.pf4j.PluginManager;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author gaopeng 2021/1/15
 */
public class SingletonSpringExtensionFactory extends SpringExtensionFactory {

    private final Set<String> extensionClassNames;

    private final Map<String, Object> cache;

    public SingletonSpringExtensionFactory(PluginManager pluginManager) {
        this(pluginManager, true);
    }

    public SingletonSpringExtensionFactory(PluginManager pluginManager, String... extensionClassNames) {
        this(pluginManager, true, extensionClassNames);
    }

    public SingletonSpringExtensionFactory(PluginManager pluginManager, boolean autowire, String... extensionClassNames) {
        super(pluginManager, autowire);

        this.extensionClassNames = Sets.newHashSet(extensionClassNames);

        cache = new HashMap<>();
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> T create(Class<T> extensionClass) {
        String extensionClassName = extensionClass.getName();
        if (cache.containsKey(extensionClassName)) {
            return (T) cache.get(extensionClassName);
        }

        T extension = super.create(extensionClass);
        if (extensionClassNames.isEmpty() || extensionClassNames.contains(extensionClassName)) {
            cache.put(extensionClassName, extension);
        }

        return extension;
    }
}
