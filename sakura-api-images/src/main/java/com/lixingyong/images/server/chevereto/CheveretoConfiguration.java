package com.lixingyong.images.server.chevereto;

import com.lixingyong.common.autoconfigure.channel.ChannelApplication;
import com.lixingyong.images.server.chevereto.cache.CheveretoCacheConfig;
import com.lixingyong.images.server.chevereto.jdbc.DataSourceConfiguration;
import com.lixingyong.images.server.chevereto.jdbc.JpaConfiguration;
import com.lixingyong.images.server.chevereto.util.ChangeID;
import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;

/**
 * @author LIlGG
 * @since 2022-09-28
 */
@ChannelApplication(value = "Chevereto", primary = true)
@ConditionalOnProperty(
    prefix = "api.images.chevereto", value = "enable", havingValue = "true", matchIfMissing = true
)
@EnableConfigurationProperties(CheveretoProperties.class)
@Import({ CheveretoConfiguration.CheveretoContextConfiguration.class,
    CheveretoConfiguration.CheveretoDataSourceConfiguration.class,
    CheveretoConfiguration.CacheConfiguration.class
})
public class CheveretoConfiguration {
    private final static Logger logger = LoggerFactory.getLogger(CheveretoConfiguration.class);

    @EnableConfigurationProperties(CheveretoProperties.class)
    protected static class CheveretoContextConfiguration {
        @Bean
        @ConditionalOnProperty(prefix = "api.images.chevereto", value = "crypt-salt")
        public CheveretoContext cheveretoContext(CheveretoProperties properties) {
            CheveretoContext cheveretoContext = new CheveretoContext();
            cheveretoContext.setChangeID(new ChangeID(properties.getCryptSalt()));
            cheveretoContext.setProperties(properties);
            return cheveretoContext;
        }
    }

    @ConditionalOnClass(DataSource.class)
    @ConditionalOnMissingBean(name = "cheveretoDataSource")
    @Import({ DataSourceConfiguration.class, JpaConfiguration.class })
    protected static class CheveretoDataSourceConfiguration {
        @PostConstruct
        public void init() {
            logger.info("[Image]Chevereto -> 数据库及 JPA 加载完成");
        }
    }

    @ConditionalOnMissingBean(name = "cacheLoader")
    @Import({ CheveretoCacheConfig.class })
    protected static class CacheConfiguration {

    }
}
