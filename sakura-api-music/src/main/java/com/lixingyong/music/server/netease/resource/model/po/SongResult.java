package com.lixingyong.music.server.netease.resource.model.po;

import com.lixingyong.music.server.netease.resource.model.entity.song.Song;
import java.util.List;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * @author LIlGG
 * @since 2021/8/7
 */
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class SongResult extends BaseNeteaseResult {
    private List<Song> songs;
}
