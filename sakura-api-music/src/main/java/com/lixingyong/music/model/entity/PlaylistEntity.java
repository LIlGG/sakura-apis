package com.lixingyong.music.model.entity;

import com.lixingyong.music.server.netease.resource.model.entity.playlist.PlayList;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

/**
 * 歌单实体
 *
 * @author LIlGG
 * @since 2022-09-27
 */
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class PlaylistEntity extends BaseEntity {
    /**
     * 歌单封面图片地址
     */
    private String coverImgUrl;

    /**
     * 歌单创建者昵称
     */
    private String creator;

    /**
     * 歌单描述
     */
    private String description;

    /**
     * 歌单歌曲数量
     */
    private int trackCount;

    public static PlaylistEntity getPlaylist(PlayList playList) {
        PlaylistEntity playlistEntity = new PlaylistEntity();
        BeanUtils.copyProperties(playList, playlistEntity);
        playlistEntity.setCreator(playList.getCreator().getNickname());
        playlistEntity.setTrackCount(playList.getTrackCount());
        return playlistEntity;
    }
}
