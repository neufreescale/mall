package org.diwayou.jdbc.datasource;

import com.google.common.base.Preconditions;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * 强制路由规则，例如强制走主库
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class HintManager implements AutoCloseable {

    public static final int MASTER_INDEX = 0;

    private static final ThreadLocal<HintManager> HINT_MANAGER_HOLDER = new ThreadLocal<>();

    private static final int DEFAULT_INDEX = -1;

    private int dataSourceIndex = DEFAULT_INDEX;

    public static HintManager getInstance() {
        Preconditions.checkState(null == HINT_MANAGER_HOLDER.get(), "未清除ThreadLocal数据");
        HintManager result = new HintManager();
        HINT_MANAGER_HOLDER.set(result);
        return result;
    }

    /**
     * 强制走主库
     */
    public void setDataSourceIndex(int dataSourceIndex) {
        this.dataSourceIndex = dataSourceIndex;
    }

    public static int getDataSourceIndex() {
        if (HINT_MANAGER_HOLDER.get() == null) {
            return DEFAULT_INDEX;
        }

        return HINT_MANAGER_HOLDER.get().dataSourceIndex;
    }

    public static boolean autoRoute() {
        return getDataSourceIndex() == DEFAULT_INDEX;
    }

    public static void clear() {
        HINT_MANAGER_HOLDER.remove();
    }

    public static boolean isInstantiated() {
        return null != HINT_MANAGER_HOLDER.get();
    }

    @Override
    public void close() {
        clear();
    }
}
