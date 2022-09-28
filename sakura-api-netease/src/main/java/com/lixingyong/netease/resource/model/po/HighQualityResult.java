package com.lixingyong.netease.resource.model.po;

import com.lixingyong.netease.resource.model.entity.playlist.PlayList;
import java.util.List;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * @author LIlGG
 * @since 2021/8/3
 */
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class HighQualityResult extends BaseNeteaseResult {
    /**
     * 精品歌单内包含的歌单
     */
    private List<PlayList> playlists;
}
