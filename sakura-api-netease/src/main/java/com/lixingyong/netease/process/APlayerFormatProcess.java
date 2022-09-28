package com.lixingyong.netease.process;

import com.lixingyong.netease.utils.NeteaseException;
import com.lixingyong.netease.model.entity.AudioEntity;
import com.lixingyong.netease.model.entity.Entity;
import com.lixingyong.netease.model.entity.PlaylistEntity;
import com.lixingyong.netease.model.entity.SearchEntity;
import com.lixingyong.netease.model.enums.ApiRequestContentType;
import java.text.MessageFormat;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Builder;
import lombok.Data;
import org.springframework.util.Assert;

/**
 * @author LIlGG
 * @since 2022-09-22
 */
public class APlayerFormatProcess implements FormatProcess {

    private String serviceUrl; // serviceUrl 基本属于固定的，因此不受到线程影响，可以作为公共数据

    private final static String REQUEST_PARAM_URL_FORMAT = "?type={0}&id={1,number,#}";

    @Override
    public <T extends Entity> Object process(T source, String serviceUrl) {
        Assert.notNull(source, "需要转换的数据为空");
        Assert.notNull(serviceUrl, "服务器 URL 为空");
        this.serviceUrl = serviceUrl;
        if (source instanceof AudioEntity) {
            return convertAudio((AudioEntity)source);
        }
        if (source instanceof PlaylistEntity) {
            return convertPlaylist((PlaylistEntity)source);
        }
        if (source instanceof SearchEntity) {
            return convertSearch((SearchEntity<? extends Entity>)source);
        }
        throw new NeteaseException("未识别的转换格式");
    }

    private Object convertSearch(SearchEntity<? extends Entity> source) {
        return process(source.getSearchList(), this.serviceUrl);
    }

    private Object convertPlaylist(PlaylistEntity source) {
        return APlayerPlaylist.builder()
            .name(source.getName())
            .creator(source.getCreator())
            .count(source.getTrackCount())
            .url(splitRequestUrl(ApiRequestContentType.URL, source.getId()))
            .build();
    }

    private Object convertAudio(AudioEntity source) {
        return APlayerAudio.builder()
            .name(source.getName())
            .artist(source.getArtistName())
            .cover(splitRequestUrl(ApiRequestContentType.PIC, source.getId()))
            .lrc(splitRequestUrl(ApiRequestContentType.LRC, source.getId()))
            .url(splitRequestUrl(ApiRequestContentType.URL, source.getId()))
            .build();
    }

    @Override
    public <T extends Entity> Object process(List<T> sources, String serviceUrl) {
        Assert.notNull(sources, "需要转换的数据为空");
        return sources.stream()
            .map(source -> process(source, serviceUrl))
            .collect(Collectors.toList());
    }

    @Data
    @Builder
    static class APlayerAudio {
        /**
         * 音频名称
         */
        private String name;
        /**
         * 音频艺术家，多个艺术家之间用 , 号分隔
         */
        private String artist;
        /**
         * 音频链接
         */
        private String url;
        /**
         * 音频封面
         */
        private String cover;
        /**
         * 音频歌词
         */
        private String lrc;
        /**
         * 音频主题
         */
        private String theme;
        /**
         * 音频类型
         * ‘auto’, 'hls', 'normal' 等
         */
        @Builder.Default
        private String type = "auto";
    }

    @Data
    @Builder
    static class APlayerPlaylist {
        /**
         * 歌单名称
         */
        private String name;

        /**
         * 歌单创建者
         */
        private String creator;

        /**
         * 歌单地址
         */
        private String url;

        /**
         * 歌单内的音乐数量
         */
        private int count;
    }

    /**
     * 拼接请求地址
     *
     * <p>用于拼接二次请求的链接，其中 host 为 {@code serviceUrl},
     * type 为 {@link com.lixingyong.netease.model.enums.ApiRequestContentType} 参数类型范围</p>
     * <p>例：http://127.0.0.1/netease?type=playlist&id=123</p>
     *
     * @see com.lixingyong.netease.model.enums.ApiRequestContentType
     *
     * @param type ApiRequestContentType 查询类型
     * @param id 查询数据编号
     * @return 拼接成功的地址
     */
    public String splitRequestUrl(ApiRequestContentType type, Object id) {
        return this.serviceUrl + MessageFormat.format(REQUEST_PARAM_URL_FORMAT, type.getType(), id);
    }
}
