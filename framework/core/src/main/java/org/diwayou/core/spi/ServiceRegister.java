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

    private static final Map<Class<?>, Collection<Object>> SINGLETON_SERVICES = new ConcurrentHashMap<>();

    public static <T> void register(final Class<T> service) {
        if (SERVICES.containsKey(service)) {
            return;
        }
        for (T each : ServiceLoader.load(service)) {
            Collection<Class<?>> serviceClasses = SERVICES.computeIfAbsent(service, unused -> new LinkedHashSet<>());
            serviceClasses.add(each.getClass());
        }
    }

    public static <T> void registerSingleton(final Class<T> service) {
        if (SINGLETON_SERVICES.containsKey(service)) {
            return;
        }
        for (T each : ServiceLoader.load(service)) {
            Collection<Object> services = SINGLETON_SERVICES.computeIfAbsent(service, unused -> new LinkedHashSet<>());
            services.add(each);
        }
    }

    @SuppressWarnings("unchecked")
    public static <T> Collection<T> getSingletons(Class<T> service) {
        return SINGLETON_SERVICES.containsKey(service) ?
                SINGLETON_SERVICES.get(service).stream()
                        .map(each -> (T) each)
                        .collect(Collectors.toList())
                : Collections.emptyList();
    }

    @SuppressWarnings("unchecked")
    public static <T> T getOneSingleton(final Class<T> service) {
        Collection<Object> services = SINGLETON_SERVICES.get(service);
        if (services == null || services.size() != 1) {
            throw new RuntimeException("不能唯一找到一个服务提供者");
        }

        return services.stream()
                .findAny()
                .map(c -> (T) c)
                .orElseThrow(() -> new RuntimeException("can't find a service"));
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
    public static <T> T newOneInstance(final Class<T> service) {
        Collection<Class<?>> serviceClasses = SERVICES.get(service);
        if (serviceClasses == null || serviceClasses.size() != 1) {
            throw new RuntimeException("不能唯一找到一个服务提供者");
        }

        return serviceClasses.stream()
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
