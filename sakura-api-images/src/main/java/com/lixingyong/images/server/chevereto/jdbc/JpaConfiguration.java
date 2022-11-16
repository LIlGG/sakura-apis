package com.lixingyong.images.server.chevereto.jdbc;

import java.util.HashMap;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

/**
 * @author LIlGG
 * @since 2022-10-09
 */
@EnableJpaRepositories(
    basePackages = JpaConfiguration.REPOSITORY_PACKAGE,
    entityManagerFactoryRef = "cheveretoEntityManagerFactory",
    transactionManagerRef = "cheveretoTransactionManager"
)
@EnableConfigurationProperties(JpaProperties.class)
@ConditionalOnClass(JpaVendorAdapter.class)
public class JpaConfiguration {

    /**
     * 资源路径
     */
    static final String REPOSITORY_PACKAGE = "com.lixingyong.images.server.chevereto.dao";

    /**
     * 实体包路径
     */
    private static final String ENTITY_PACKAGE = "com.lixingyong.images.server.chevereto.model.entity";

    @Bean
    public PlatformTransactionManager cheveretoTransactionManager(
        EntityManagerFactory cheveretoEntityManagerFactory
    ) {
        return new JpaTransactionManager(cheveretoEntityManagerFactory);
    }

    @Bean
    @ConditionalOnBean(name = "cheveretoDataSource")
    public LocalContainerEntityManagerFactoryBean cheveretoEntityManagerFactory(
        DataSource cheveretoDataSource,
        JpaProperties jpaProperties,
        EntityManagerFactoryBuilder builder
    ) {
        return builder
            .dataSource(cheveretoDataSource)
            .properties(jpaProperties.getProperties())
            .packages(ENTITY_PACKAGE)
            .persistenceUnit("cheveretoPersistenceUnit")
            .build();
    }

    @Bean
    public EntityManagerFactoryBuilder entityManagerFactoryBuilder() {
        return new EntityManagerFactoryBuilder(new HibernateJpaVendorAdapter(), new HashMap<>(), null);
    }
}
