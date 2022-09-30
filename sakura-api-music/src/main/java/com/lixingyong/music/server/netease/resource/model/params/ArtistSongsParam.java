package com.lixingyong.music.server.netease.resource.model.params;

import com.lixingyong.music.server.netease.resource.model.params.base.PageParam;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

/**
 * 获取歌手歌曲参数
 *
 * @author LIlGG
 * @since 2021/8/7
 */
@SuperBuilder(toBuilder = true)
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class ArtistSongsParam extends PageParam {
    /**
     * 歌手编号
     */
    private Long id;
    /**
     * 排序方式，默认为热门
     * hot - 热门
     * time - 时间
     */
    @Builder.Default
    private String order = "hot";
}
