package org.diwayou.jdbc;

import org.mybatis.spring.SqlSessionFactoryBean;

/**
 * @author gaopeng 2021/4/7
 */
public interface SqlSessionFactoryBeanCustomizer {

    void customize(SqlSessionFactoryBean sqlSessionFactoryBean);
}
