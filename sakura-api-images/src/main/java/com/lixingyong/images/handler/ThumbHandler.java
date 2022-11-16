package com.lixingyong.images.handler;

/**
 * 缩略图处理类。用于处理原始图片链接并由第三方系统来根据构造的参数来生成对应的缩略图，常用于 CDN 相关图片处理
 * 功能。
 *
 * <p>
 * 也可用于服务器本地处理图片后返回处理后的图片链接。
 * </p>
 *
 * @author LIlGG
 * @since 2022-11-13
 */
public interface ThumbHandler {
    /**
     * 创建一个缩略图属性构建器
     *
     * @param url 原始图片链接
     * @return 构造器
     */
    ThumbBuild createBuild(String url);

    interface ThumbBuild {
        /**
         * 转换图片格式，例如由 jpg 转为 png，或者转为 webp 等。
         *
         * @param type 需要转换的类型
         * @return 构造器
         */
        ThumbBuild format(String type);

        /**
         * 设置图片质量，通常为 大于 0 小于等于 1 的浮点数
         *
         * @param quality 图片质量
         * @return 构造器
         */
        ThumbBuild quality(Float quality);

        /**
         * 缩放图片。
         *
         * @param width 缩放后的宽度，需大于 0
         * @param height 缩放后的高度，需大于 0
         * @return 构造器
         */
        ThumbBuild resize(Integer width, Integer height);

        String build();
    }
}
