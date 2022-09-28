package com.lixingyong.netease.service.impl;

import com.lixingyong.netease.utils.NeteaseException;
import com.lixingyong.netease.model.entity.AudioEntity;
import com.lixingyong.netease.resource.NeteaseNodeJs;
import com.lixingyong.netease.resource.model.entity.playlist.Track;
import com.lixingyong.netease.resource.model.entity.song.Song;
import com.lixingyong.netease.service.NeteaseApiService;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

/**
 * 网易云 API 服务实现类
 *
 * @author LIlGG
 * @since 2022-09-27
 */
@Service
public class NeteaseApiServiceImpl implements NeteaseApiService {

    private final NeteaseNodeJs resource;

    public NeteaseApiServiceImpl(NeteaseNodeJs neteaseNodeJs) {
        this.resource = neteaseNodeJs;
    }

    @Override
    public List<AudioEntity> playlistAudio(long id) {
        List<Track> tracks = resource.playListDetail(id).getTrackIds();
        // https://binaryify.github.io/NeteaseCloudMusicApi/#/?id=%e8%8e%b7%e5%8f%96%e6%ad%8c%e5%8d%95%e8%af%a6%e6%83%85
        // 使用查询到的 track 编号，获取歌曲详细信息
        List<Song> songs = resource.songByMusicId(
            tracks.stream().map(Track::getId).collect(Collectors.toList())
        );
        return songs.stream().map(AudioEntity::getAudio).collect(Collectors.toList());
    }

    @Override
    public List<AudioEntity> artistAudios(long id) {
        return null;
    }

    @Override
    public String audioUrl(Long audioId) {
        // 使用查询到的 track 编号，获取歌曲详细信息
        List<Song> songs = resource.songByMusicId(Collections.singleton(audioId));
        if (CollectionUtils.isEmpty(songs)) {
            throw new NeteaseException("未获取到歌曲播放链接");
        }
        return songs.get(0).getAl().getPicUrl();
    }

    @Override
    public String audioPic(Long audioId) {
        // 使用查询到的 track 编号，获取歌曲详细信息
        List<Song> songs = resource.songByMusicId(Collections.singleton(audioId));
        if (CollectionUtils.isEmpty(songs)) {
            throw new NeteaseException("未获取到歌曲封面");
        }
        return songs.get(0).getAl().getPicUrl();
    }

    @Override
    public String audioLrc(Long audioId) {
        return resource.getLyric(audioId).getLyric();
    }
}
