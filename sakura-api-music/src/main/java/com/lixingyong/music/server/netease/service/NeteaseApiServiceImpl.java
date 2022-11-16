package com.lixingyong.music.server.netease.service;

import com.lixingyong.common.autoconfigure.service.ApiService;
import com.lixingyong.music.model.entity.AudioEntity;
import com.lixingyong.music.model.entity.SearchEntity;
import com.lixingyong.music.model.enums.SearchParamType;
import com.lixingyong.music.model.param.SearchListParam;
import com.lixingyong.music.server.netease.process.SearchConvertProcess;
import com.lixingyong.music.server.netease.resource.NeteaseNodeJs;
import com.lixingyong.music.server.netease.resource.model.entity.music.Music;
import com.lixingyong.music.server.netease.resource.model.entity.playlist.Track;
import com.lixingyong.music.server.netease.resource.model.entity.search.SearchList;
import com.lixingyong.music.server.netease.resource.model.entity.song.Song;
import com.lixingyong.music.server.netease.resource.model.enums.SearchType;
import com.lixingyong.music.server.netease.resource.model.params.SearchParam;
import com.lixingyong.music.utils.MusicApiException;
import com.lixingyong.music.service.MusicApiService;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.util.CollectionUtils;
import com.lixingyong.common.utils.ValueEnum;

/**
 * 网易云 API 服务实现类
 *
 * @author LIlGG
 * @since 2022-09-27
 */
@ApiService
public class NeteaseApiServiceImpl implements MusicApiService {

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
        List<Music> musics = resource.getMusicUrl(Collections.singleton(audioId));
        if (CollectionUtils.isEmpty(musics)) {
            throw new MusicApiException("未获取到歌曲播放链接");
        }
        return musics.get(0).getUrl();
    }

    @Override
    public String audioPic(Long audioId) {
        // 使用查询到的 track 编号，获取歌曲详细信息
        List<Song> songs = resource.songByMusicId(Collections.singleton(audioId));
        if (CollectionUtils.isEmpty(songs)) {
            throw new MusicApiException("未获取到歌曲封面");
        }
        return songs.get(0).getAl().getPicUrl();
    }

    @Override
    public String audioLrc(Long audioId) {
        return resource.getLyric(audioId).getLyric();
    }

    @Override
    public SearchEntity<?> search(SearchListParam params) {
        SearchParamType searchParamType
            = ValueEnum.valueToEnum(SearchParamType.class, params.getSearchType());
        SearchParam searchParam = SearchParam.builder()
            .keywords(params.getId())
            .type(SearchType.getValueByParamType(searchParamType))
            .limit(params.getLimit())
            .offset(params.getOffset())
            .build();
        SearchList searchList = resource.search(searchParam);
        return SearchConvertProcess.convertSearch(searchList);
    }
}
