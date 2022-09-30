package com.lixingyong.music.process;

import com.lixingyong.music.model.enums.FormatProcessType;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import lombok.SneakyThrows;
import utils.ValueEnum;

/**
 * @author LIlGG
 * @since 2022-09-28
 */
public class FormatProcessFactory {

    private static final Map<Class<? extends FormatProcess>, FormatProcess> cacheInstance
        = new HashMap<>();

    public static FormatProcess getFormatProcess(String type) {
        return getFormatProcess(ValueEnum.valueToEnum(FormatProcessType.class, type));
    }

    public static FormatProcess getFormatProcess(FormatProcessType type) {
        Class<? extends FormatProcess> typeClass = type.getAClass();
        FormatProcess formatProcess = FormatProcessFactory.getCacheFormatProcess(typeClass);
        if (Objects.nonNull(formatProcess)) {
            return formatProcess;
        }
        return FormatProcessFactory.addCacheInstance(typeClass);
    }

    private static FormatProcess getCacheFormatProcess(
        Class<? extends FormatProcess> clazz) {
        return cacheInstance.getOrDefault(clazz, null);
    }

    @SneakyThrows
    private static synchronized FormatProcess addCacheInstance(Class<? extends FormatProcess> clazz) {
        // 写入之前再次检查是否已经存在，存在则不写入
        if (cacheInstance.containsKey(clazz)) {
            return cacheInstance.get(clazz);
        }
        FormatProcess formatProcess = clazz.getDeclaredConstructor().newInstance();
        cacheInstance.put(clazz, formatProcess);
        return formatProcess;
    }
}
