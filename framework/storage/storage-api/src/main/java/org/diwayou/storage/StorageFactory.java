package org.diwayou.storage;

import org.diwayou.core.spi.ServiceRegister;

import java.util.Collection;

/**
 * @author gaopeng 2021/2/5
 */
public class StorageFactory {

    static {
        ServiceRegister.registerSingleton(Storage.class);
    }

    private static final Collection<Storage> storageAll = ServiceRegister.getSingletons(Storage.class);

    public static Storage get(String name) {
        for (Storage storage : storageAll) {
            if (storage.name().equals(name)) {
                return storage;
            }
        }

        throw new RuntimeException("找不到Storage " + name);
    }
}
