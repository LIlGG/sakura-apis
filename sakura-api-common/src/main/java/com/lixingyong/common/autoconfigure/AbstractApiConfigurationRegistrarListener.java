package com.lixingyong.common.autoconfigure;

import com.lixingyong.common.exception.ApiException;
import java.util.LinkedList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.type.AnnotationMetadata;

/**
 * @author LIlGG
 * @since 2022-11-08
 */
public abstract class AbstractApiConfigurationRegistrarListener implements ApiConfigurationRegistrarListener {

    private static final Logger logger = LoggerFactory.getLogger(AbstractApiConfigurationRegistrarListener.class);

    private static final String MODULE_ANNOTATED_NAME = "com.lixingyong.common.autoconfigure.module.ModuleApplication";

    private static final String CHANNEL_ANNOTATED_NAME = "com.lixingyong.common.autoconfigure.channel.ChannelApplication";

    protected final List<AutoConfigurationEntry> rootAutoConfiguration = new LinkedList<>();

    @Override
    public void registerConfiguration(AnnotationMetadata metadata, List<String> configuration) {
        addAutoConfigurationEntry(metadata, configuration);
    }

    protected void addAutoConfigurationEntry(AnnotationMetadata metadata, List<String> configuration) {
        AnnotationAttributes attributes = null;
        if (metadata.isAnnotated(MODULE_ANNOTATED_NAME)) {
            attributes = getAnnotationAttributes(metadata, MODULE_ANNOTATED_NAME);
            addModuleEntry(createAutoConfigurationEntry(attributes,  metadata, configuration));
            return;
        }
        if (metadata.isAnnotated(CHANNEL_ANNOTATED_NAME)) {
            attributes = getAnnotationAttributes(metadata, CHANNEL_ANNOTATED_NAME);
            mountChannelEntry(createAutoConfigurationEntry(attributes, metadata, configuration));
            return;
        }
        throw new IllegalArgumentException("请检查配置类 [" + metadata.getClassName() + "] 上是否有注解 " + MODULE_ANNOTATED_NAME + " 或 " + CHANNEL_ANNOTATED_NAME);
    }

    protected synchronized void addModuleEntry(AutoConfigurationEntry module) {
        long count = this.rootAutoConfiguration.stream().filter(item -> item.equals(module)).count();
        if (count > 0) {
            throw new ApiException("[" + module.getClassName() + "] 自动配置文件重复加载");
        }
        logger.info("模块 [" + module.getName() + "] 注册完成");
        this.rootAutoConfiguration.add(module);
    }

    protected synchronized void mountChannelEntry(AutoConfigurationEntry channel) {
        for (AutoConfigurationEntry moduleEntry : this.rootAutoConfiguration) {
            for (int index = 0; index < moduleEntry.getChild().size(); index++) {
                if (channel.equals(moduleEntry.getChild().get(index))) {
                    channel.setParent(moduleEntry);
                    moduleEntry.getChild().set(index, channel);
                    logger.info("模块 [" + moduleEntry.getName() + "] 下的渠道 [" + channel.getName() + "] 注册完成");
                    return;
                }
            }
        }
        throw new ApiException("渠道 [" + channel.getClassName() + "] 无法确定所属模块，请检查");
    }

    protected static AnnotationAttributes getAnnotationAttributes(AnnotationMetadata metadata, String annotationClassName) {
        return AnnotationAttributes.fromMap(metadata.getAnnotationAttributes(annotationClassName, true));
    }

    protected AutoConfigurationEntry createAutoConfigurationEntry(AnnotationAttributes attributes,
                                                                  AnnotationMetadata metadata,
                                                                  List<String> configuration) {
        AutoConfigurationEntry autoConfigurationEntry = new AutoConfigurationEntry(metadata.getClassName(), attributes.getString("value"));
        autoConfigurationEntry.setAutoConfigurationChild(configuration);
        if (attributes.containsKey("primary")) {
            autoConfigurationEntry.setDefault(attributes.getBoolean("primary"));
        }
        return autoConfigurationEntry;
    }
}
