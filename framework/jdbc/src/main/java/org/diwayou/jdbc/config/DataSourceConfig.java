package org.diwayou.jdbc.config;

import lombok.Data;

/**
 * @author gaopeng 2021/12/1
 */
@Data
public class DataSourceConfig {
    private String ip;
    private String port;
    private String dbName;
    private String userName;
    private String password;
    private String minimumIdle;
    private String maximumPoolSize;
}
