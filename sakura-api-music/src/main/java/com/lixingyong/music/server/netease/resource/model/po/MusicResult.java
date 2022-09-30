package com.lixingyong.music.server.netease.resource.model.po;

import com.lixingyong.music.server.netease.resource.model.entity.music.Music;
import java.util.List;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * 音乐详情查询实体
 *
 * @author LIlGG
 * @since 2021/8/3
 */
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class MusicResult extends BaseNeteaseResult {
    /**
     * 查询到的音乐详情列表
     */
    private List<Music> data;
}
