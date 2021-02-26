package org.diwayou.cache.manager;

import org.apache.commons.lang3.BooleanUtils;
import org.diwayou.cache.KvCache;
import org.diwayou.core.exception.CustomException;

import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

/**
 * @author gaopeng 2021/2/24
 */
public class LockManager {

    private KvCache kvCache;

    public LockManager(KvCache kvCache) {
        this.kvCache = kvCache;
    }

    public <R> R executeWithLock(String key, long seconds, Supplier<R> callback) {
        return executeWithLock(key, seconds, TimeUnit.SECONDS, callback);
    }

    public <R> R executeWithLock(String key, long timeout, TimeUnit unit, Supplier<R> callback) {
        Boolean lockResult = kvCache.setIfAbsent(key, "", timeout, unit);
        if (!BooleanUtils.isTrue(lockResult)) {
            throw new CustomException("操作冲突，请稍后重试!");
        }

        try {
            return callback.get();
        } finally {
            kvCache.delete(key);
        }
    }

    public <T> void runWithLock(String key, long seconds, Runnable callback) {
        runWithLock(key, seconds, TimeUnit.SECONDS, callback);
    }

    public <T> void runWithLock(String key, long timeout, TimeUnit unit, Runnable callback) {
        executeWithLock(key, timeout, unit, () -> {
            callback.run();
            return null;
        });
    }
}
