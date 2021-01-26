package org.diwayou.jdbc.key;

import lombok.Builder;
import lombok.Getter;

/**
 * @author gaopeng 2021/1/20
 */
@Getter
@Builder
public class KeyGeneratorConfig {

    private static final int MIN_STEP = 1;
    private static final int MAX_STEP = 100000;
    private static final int DEFAULT_STEP = 1000;
    private static final int DEFAULT_RETRY_TIMES = 16;

    private static final String DEFAULT_TABLE_NAME = "sequence";
    private static final String DEFAULT_NAME_COLUMN_NAME = "name";
    private static final String DEFAULT_VALUE_COLUMN_NAME = "value";
    private static final String DEFAULT_GMT_MODIFIED_COLUMN_NAME = "gmt_modified";

    /**
     * 重试次数
     */
    @Builder.Default
    private int retryTimes = DEFAULT_RETRY_TIMES;

    /**
     * 步长
     */
    @Builder.Default
    private int step = DEFAULT_STEP;

    /**
     * 序列所在的表名
     */
    @Builder.Default
    private String tableName = DEFAULT_TABLE_NAME;

    /**
     * 存储序列名称的列名
     */
    @Builder.Default
    private String nameColumnName = DEFAULT_NAME_COLUMN_NAME;

    /**
     * 存储序列值的列名
     */
    @Builder.Default
    private String valueColumnName = DEFAULT_VALUE_COLUMN_NAME;

    /**
     * 存储序列最后更新时间的列名
     */
    @Builder.Default
    private String gmtModifiedColumnName = DEFAULT_GMT_MODIFIED_COLUMN_NAME;

}
