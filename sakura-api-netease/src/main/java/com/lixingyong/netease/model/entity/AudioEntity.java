package com.lixingyong.netease.model.entity;

import com.lixingyong.netease.resource.model.entity.Artist;
import com.lixingyong.netease.resource.model.entity.song.Song;
import java.util.Arrays;
import java.util.stream.Collectors;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

/**
 * 音频实体
 *
 * @author LIlGG
 * @since 2022-09-27
 */
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class AudioEntity extends BaseEntity {
    /**
     * 艺术家，多个艺术家之间使用 , 号分隔
     */
    private String artistName;

    /**
     * 音频播放链接
     */
    private String url;

    /**
     * 歌曲封面链接
     */
    private String picUrl;

    /**
     * 歌词
     */
    private String lrc;

    public static AudioEntity getAudio(Song song) {
        AudioEntity audio = new AudioEntity();
        BeanUtils.copyProperties(song, audio);
        audio.setArtistName(
            Arrays.stream(song.getAr()).map(Artist::getName)
                .collect(Collectors.joining(","))
        );
        audio.setPicUrl(song.getAl().getPicUrl());
        return audio;
    }
}
