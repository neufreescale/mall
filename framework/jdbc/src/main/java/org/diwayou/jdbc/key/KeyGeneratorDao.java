package org.diwayou.jdbc.key;

import lombok.extern.slf4j.Slf4j;
import org.diwayou.jdbc.exception.GenerateKeyException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author gaopeng 2021/1/20
 */
@Slf4j
public class KeyGeneratorDao {

    private static final String SELECT_SQL_TEMPLATE = "select %s from sequence where %s = ?";

    private static final String UPDATE_SQL_TEMPLATE = "update %s set %s = ?, gmt_modified = NOW() where %s = ? and %s = ?";

    private KeyGeneratorConfig config;

    private String selectSql;

    private String updateSql;

    private JdbcTemplate jdbcTemplate;

    public KeyGeneratorDao(JdbcTemplate jdbcTemplate) {
        this(jdbcTemplate, KeyGeneratorConfig.builder().build());
    }

    public KeyGeneratorDao(JdbcTemplate jdbcTemplate, KeyGeneratorConfig config) {
        this.jdbcTemplate = jdbcTemplate;
        this.config = config;

        this.selectSql = String.format(SELECT_SQL_TEMPLATE, config.getValueColumnName(), config.getNameColumnName());
        this.updateSql = String.format(UPDATE_SQL_TEMPLATE, config.getTableName(), config.getValueColumnName(),
                config.getNameColumnName(), config.getValueColumnName());
    }

    @Transactional(propagation = Propagation.NOT_SUPPORTED, rollbackFor = Exception.class)
    public KeyRange nextRange(String name) {
        if (name == null) {
            throw new IllegalArgumentException("名称不能为空");
        }

        int retryTimes = config.getRetryTimes();
        for (int i = 0; i < retryTimes; i++) {
            Long oldValue = jdbcTemplate.queryForObject(selectSql, new Object[]{name}, Long.class);

            if (oldValue == null) {
                throw new GenerateKeyException("value不能为空!");
            }

            long unBoxOldValue = oldValue;
            if (unBoxOldValue < 0) {
                throw new GenerateKeyException("value不能小于0!");
            }
            if (unBoxOldValue > Long.MAX_VALUE - 100000000L) {
                throw new GenerateKeyException("value溢出!");
            }

            long newValue = unBoxOldValue + config.getStep();

            int affected = jdbcTemplate.update(updateSql, newValue, name, oldValue);

            if (affected == 0) {
                log.info("更新失败，重试name={}", name);

                continue;
            }

            return new KeyRange(unBoxOldValue + 1, newValue, config.getStep());
        }

        throw new GenerateKeyException("重试太多次, retryTimes = " + retryTimes);
    }
}
