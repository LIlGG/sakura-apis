package com.lixingyong.images.server.chevereto.jdbc;

import com.alibaba.druid.pool.DruidDataSource;
import com.lixingyong.images.server.chevereto.CheveretoProperties;
import java.sql.SQLException;
import javax.sql.DataSource;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * CheveretoConfiguration 实际导入的 DataSource 配置
 *
 * @author LIlGG
 * @since 2022-10-08
 */
@ConditionalOnClass({ com.mysql.cj.jdbc.Driver.class, com.mysql.jdbc.Driver.class })
@ConditionalOnProperty(prefix = "api.images.chevereto.datasource", name = "url")
@ConditionalOnMissingBean(name = "cheveretoDataSource")
public class DataSourceConfiguration {

    private final static String DEFAULT_DRIVER = "com.mysql.cj.jdbc.Driver";

    @Bean
    @Primary
    @ConditionalOnClass(DruidDataSource.class)
    @Order(Ordered.LOWEST_PRECEDENCE - 10)
    public DataSource cheveretoDataSource(CheveretoProperties cheveretoProperties) {
        CheveretoDataSourceProperties properties = cheveretoProperties.getDatasource();
        DruidDataSource datasource = new DruidDataSource();
        datasource.setUrl(properties.getUrl());
        datasource.setUsername(properties.getUsername());
        datasource.setPassword(properties.getPassword());
        datasource.setDriverClassName(DEFAULT_DRIVER);

        // configuration
        datasource.setInitialSize(properties.getInitialSize());
        datasource.setMinIdle(properties.getMinIdle());
        datasource.setMaxActive(properties.getMaxActive());
        datasource.setMaxWait(properties.getMaxWait());
        datasource.setTimeBetweenEvictionRunsMillis(properties.getTimeBetweenEvictionRunsMillis());
        datasource.setMinEvictableIdleTimeMillis(properties.getMinEvictableIdleTimeMillis());
        datasource.setValidationQuery(properties.getValidationQuery());
        datasource.setTestWhileIdle(properties.isTestWhileIdle());
        datasource.setTestOnBorrow(properties.isTestOnBorrow());
        datasource.setTestOnReturn(properties.isTestOnReturn());
        datasource.setPoolPreparedStatements(properties.isPoolPreparedStatements());
        datasource.setMaxPoolPreparedStatementPerConnectionSize(properties.getMaxPoolPreparedStatementPerConnectionSize());
        try {
            datasource.setFilters(properties.getFilters());
        } catch (SQLException e) {
            System.err.println("druid configuration initialization filter: " + e);
        }
        datasource.setConnectionProperties(properties.getConnectionProperties());
        return datasource;
    }

    @Bean
    @Primary
    public JdbcTemplate cheveretoJdbcTemplate(DataSource cheveretoDataSource) {
        return new JdbcTemplate(cheveretoDataSource);
    }
}
