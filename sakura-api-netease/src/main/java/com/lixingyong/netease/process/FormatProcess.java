package com.lixingyong.netease.process;

import com.lixingyong.netease.model.entity.Entity;
import java.util.List;

/**
 * 网易云音乐数据格式处理器。
 *
 * @author LIlGG
 * @since 2022-09-22
 */
public interface FormatProcess {
    /**
     * 将原始数据转换为特定数据并输出
     *
     * @param source 原始数据
     * @param serviceUrl 服务器 URL
     * @return 按照特定格式转换后的数据
     */
    <T extends Entity> Object process(T source, String serviceUrl);

    /**
     * 将原始数据转换为特定数据并输出
     *
     * @param sources 原始数据列表
     * @param serviceUrl 服务器 URL
     * @return 按照特定格式转换后的数据
     */
    <T extends Entity> Object process(List<T> sources, String serviceUrl);
}
