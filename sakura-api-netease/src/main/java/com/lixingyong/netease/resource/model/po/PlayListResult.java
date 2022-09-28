package com.lixingyong.netease.resource.model.po;

import com.lixingyong.netease.resource.model.entity.playlist.PlayList;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * 请求网易云 API 获取到的实体数据
 *
 * @author LIlGG
 * @since 2021/7/30
 */
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class PlayListResult extends BaseNeteaseResult {
    private PlayList playlist;
}
