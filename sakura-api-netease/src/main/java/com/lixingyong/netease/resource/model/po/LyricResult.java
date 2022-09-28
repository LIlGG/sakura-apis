package com.lixingyong.netease.resource.model.po;

import com.lixingyong.netease.resource.model.entity.lyric.Lyric;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * 歌词查询返回实体
 *
 * @author LIlGG
 * @since 2021/8/3
 */
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class LyricResult extends BaseNeteaseResult {
    /**
     * 普通歌词
     */
    private Lyric lrc;
    /**
     * 滚动歌词
     */
    private Lyric klyric;
    /**
     * 未知歌词
     */
    private Lyric tlyric;
}
