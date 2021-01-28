package org.diwayou.core.spi;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * @author gaopeng 2021/1/29
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ServiceRegister {

    private static final Map<Class<?>, Collection<Class<?>>> SERVICES = new ConcurrentHashMap<>();

    public static <T> void register(final Class<T> service) {
        if (SERVICES.containsKey(service)) {
            return;
        }
        for (T each : ServiceLoader.load(service)) {
            registerServiceClass(service, each);
        }
    }

    private static <T> void registerServiceClass(final Class<T> service, final T instance) {
        Collection<Class<?>> serviceClasses = SERVICES.computeIfAbsent(service, unused -> new LinkedHashSet<>());
        serviceClasses.add(instance.getClass());
    }

    @SuppressWarnings("unchecked")
    public static <T> Collection<T> newServiceInstances(final Class<T> service) {
        return SERVICES.containsKey(service) ?
                SERVICES.get(service).stream()
                        .map(each -> (T) newServiceInstance(each))
                        .collect(Collectors.toList())
                : Collections.emptyList();
    }

    @SuppressWarnings("unchecked")
    public static <T> T newAnyInstance(final Class<T> service) {
        return SERVICES.get(service).stream()
                .findAny()
                .map(c -> (T) newServiceInstance(c))
                .orElseThrow(() -> new RuntimeException("can't find a service"));
    }

    private static Object newServiceInstance(final Class<?> clazz) {
        try {
            return clazz.newInstance();
        } catch (final InstantiationException | IllegalAccessException ex) {
            throw new RuntimeException(ex);
        }
    }
}
