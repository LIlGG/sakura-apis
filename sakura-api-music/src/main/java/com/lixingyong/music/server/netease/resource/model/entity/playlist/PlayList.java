package com.lixingyong.music.server.netease.resource.model.entity.playlist;

import java.util.List;
import lombok.Data;

/**
 * 歌单（playlist） 信息，<span>非完整信息，只取一般会用到的数据</span>
 *
 * <p>用户“我喜欢”歌单需要登录才可查看</p>
 *
 * <p>无法直接获取到对应歌曲真实 URL</p>
 *
 * @author LIlGG
 * @since 2021/7/29
 */
@Data
public class PlayList {
    /**
     * 歌单编号
     */
    private long id;
    /**
     * 歌单名
     */
    private String name;
    /**
     * 歌单封面图片地址
     */
    private String coverImgUrl;
    /**
     * 歌单创建者编号
     */
    private long userId;
    /**
     * 歌单创建者信息
     * @see PlayListCreator
     */
    private PlayListCreator creator;
    /**
     * 歌单创建时间（时间戳）
     */
    private Long createTime;
    /**
     * 歌单更新时间（时间戳）
     */
    private Long updateTime;
    /**
     * 歌单描述
     */
    private String description;
    /**
     * 歌单标签
     */
    private String[] tags;
    /**
     * 歌单歌曲列表（仅包含编号，因此需要根据编号再次解析）
     * @see Track
     */
    private List<Track> trackIds;
    /**
     * 播放次数
     */
    private int playCount;
    /**
     * 歌曲数量
     */
    private int trackCount;
}
