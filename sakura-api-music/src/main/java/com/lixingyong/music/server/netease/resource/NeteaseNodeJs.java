package com.lixingyong.music.server.netease.resource;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.lixingyong.music.server.netease.resource.constant.NeteaseNodeApiConstant;
import com.lixingyong.music.server.netease.resource.model.entity.lyric.Lyric;
import com.lixingyong.music.server.netease.resource.model.entity.music.Music;
import com.lixingyong.music.server.netease.resource.model.entity.playlist.PlayList;
import com.lixingyong.music.server.netease.resource.model.entity.search.AlbumSearchList;
import com.lixingyong.music.server.netease.resource.model.entity.search.ArtistSearchList;
import com.lixingyong.music.server.netease.resource.model.entity.search.PlaylistSearchList;
import com.lixingyong.music.server.netease.resource.model.entity.search.SearchList;
import com.lixingyong.music.server.netease.resource.model.entity.search.SongSearchList;
import com.lixingyong.music.server.netease.resource.model.entity.song.Song;
import com.lixingyong.music.server.netease.resource.model.enums.NeteaseCodeStatus;
import com.lixingyong.music.server.netease.resource.model.enums.SearchType;
import com.lixingyong.music.server.netease.resource.model.params.ArtistSongsParam;
import com.lixingyong.music.server.netease.resource.model.params.HighQualityParam;
import com.lixingyong.music.server.netease.resource.model.params.SearchParam;
import com.lixingyong.music.server.netease.resource.model.po.HighQualityResult;
import com.lixingyong.music.server.netease.resource.model.po.LyricResult;
import com.lixingyong.music.server.netease.resource.model.po.MusicResult;
import com.lixingyong.music.server.netease.resource.model.po.PlayListResult;
import com.lixingyong.music.server.netease.resource.model.po.SearchListResult;
import com.lixingyong.music.server.netease.resource.model.po.SongResult;
import com.lixingyong.music.utils.MusicApiException;
import java.net.http.HttpResponse;
import java.text.MessageFormat;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import org.springframework.util.StringUtils;
import utils.HttpRequestUtil;
import utils.RequestItem;

/**
 * 通过网易云 API 接口，获取网易云音乐列表，此方法仅实现调用网易云 API，不做其他任何处理。接口如下所示
 * <ul>
 *     <li>获取歌单 {@link #playListDetail(long)} </li>
 *     <li>获取搜索列表 {@link #search(SearchParam)}</li>
 *     <li>获取精品歌单列表 {@link #highQuality(HighQualityParam)}</li>
 *     <li>获取音乐链接 {@link #getMusicUrl(Collection)}</li>
 * </ul>
 *
 * @see <a href="https://binaryify.github.io/NeteaseCloudMusicApi">网易云接口文档</a>
 *
 * @author LIlGG
 * @since 2022-09-27
 */
public class NeteaseNodeJs {

    private final String cloudHost;

    private HttpRequestUtil httpRequestUtil = new HttpRequestUtil();

    public NeteaseNodeJs(String cloudHost) {
        if (!StringUtils.hasLength(cloudHost)) {
            throw new IllegalArgumentException("cloudHost 为空，请检查配置文件 api.netease.upstream-api");
        }
        this.cloudHost = cloudHost;
    }

    public void setHttpClient(HttpRequestUtil httpRequestUtil) {
        this.httpRequestUtil = httpRequestUtil;
    }

    /**
     * 使用网易云音乐接口获取歌单详情。
     * <p>
     *     此接口中只能获取到歌曲 trackId，需要根据 trackId 二次调用 {@link #songByMusicId(Collection)}
     *     来获取歌曲详细信息
     * </p>
     *
     *
     * @see <a href="https://binaryify.github.io/NeteaseCloudMusicApi/#/?id=%e8%8e%b7%e5%8f%96%e6%ad%8c%e5%8d%95%e8%af%a6%e6%83%85">获取歌单详情</a>
     * @see PlayList
     *
     * @param id 歌单编号
     * @return 歌单信息列表 PlayList
     */
    public PlayList playListDetail(long id) {
        RequestItem requestItem = RequestItem
            .newBuilder(cloudHost + NeteaseNodeApiConstant.PLAYLIST)
            .form("id", id).build();
        HttpResponse<String> response = httpRequestUtil
            .sendAsync(requestItem, HttpResponse.BodyHandlers.ofString())
            .join();
        PlayListResult playListResult = JSONObject.parseObject(response.body(), PlayListResult.class);
        if (Objects.isNull(playListResult)) {
            throw new MusicApiException("未查询到歌单列表");
        }
        if (playListResult.getCode() == NeteaseCodeStatus.OK) {
            return playListResult.getPlaylist();
        }
        throw new MusicApiException(playListResult.getCode().getMsg());
    }

    /**
     * 搜索单曲或列表。根据参数不同，搜索结果也不同。目前支持如下搜索。
     * <ul>
     *     <li>单曲搜索</li>
     *     <li>专辑搜索</li>
     *     <li>歌手搜索</li>
     *     <li>歌单搜索</li>
     * </ul>
     *
     * <p>搜索支持列表参见 {@link SearchType}</p>
     *
     * @see SongSearchList
     * @see AlbumSearchList
     * @see ArtistSearchList
     * @see PlaylistSearchList
     *
     * @param searchParam 搜索功能所需要的参数
     * @return 搜索到的列表/单曲
     */
    public SearchList search(SearchParam searchParam) {
        Map<String, Object> param = JSON.parseObject(JSON.toJSONString(searchParam));
        RequestItem requestItem = RequestItem
            .newBuilder(cloudHost + NeteaseNodeApiConstant.SEARCH)
            .form(param).build();
        HttpResponse<String> response = httpRequestUtil
            .sendAsync(requestItem, HttpResponse.BodyHandlers.ofString())
            .join();
        SearchListResult searchListResult
            = JSONObject.parseObject(response.body(), SearchListResult.class);
        if (Objects.isNull(searchListResult)) {
            throw new MusicApiException("网易云音乐搜索失败，未获取到数据");
        }
        if (searchListResult.getCode() != NeteaseCodeStatus.OK) {
            throw new MusicApiException(searchListResult.getCode().getMsg());
        }
        SearchList searchList = JSONObject.parseObject(searchListResult.getResult(),
            searchParam.getType().getSearchListClass());
        if (Objects.isNull(searchList)) {
            throw new MusicApiException("搜索列表为空");
        }
        return searchList;
    }

    /**
     * 获取精品歌单
     *
     * <p>仅包含歌单列表，如需再查询歌单内歌曲，则需要使用 {@link #playListDetail(long)} 二次获取</p>
     *
     * @see NeteaseNodeJs#playListDetail(long)
     *
     * @param highQualityParam 搜索精品菜单所需要的参数
     * @return 推荐的精品歌单列表，但不包含音乐
     */
    public List<PlayList> highQuality(HighQualityParam highQualityParam) {
        Map<String, Object> param = JSON.parseObject(JSON.toJSONString(highQualityParam));
        RequestItem requestItem = RequestItem
            .newBuilder(cloudHost + NeteaseNodeApiConstant.HIGH_QUALITY_PLAYLIST)
            .form(param).build();
        HttpResponse<String> response = httpRequestUtil
            .sendAsync(requestItem, HttpResponse.BodyHandlers.ofString())
            .join();
        HighQualityResult highQualityResult
            = JSONObject.parseObject(response.body(), HighQualityResult.class);
        if (Objects.isNull(highQualityResult)) {
            throw new MusicApiException("精品歌单获取失败");
        }
        if (highQualityResult.getCode() != NeteaseCodeStatus.OK) {
            throw new MusicApiException(highQualityResult.getCode().getMsg());
        }
        return highQualityResult.getPlaylists();
    }

    /**
     * 根据艺术家获取其单曲列表列表
     *
     * @param artistSongsParam 需要获取的艺术家信息
     * @return 包含歌曲信息的列表
     */
    public List<Song> artistSongs(ArtistSongsParam artistSongsParam) {
        Map<String, Object> param = JSON.parseObject(JSON.toJSONString(artistSongsParam));
        RequestItem requestItem = RequestItem
            .newBuilder(cloudHost + NeteaseNodeApiConstant.ARTIST_SONGS)
            .form(param).build();
        HttpResponse<String> response = httpRequestUtil
            .sendAsync(requestItem, HttpResponse.BodyHandlers.ofString())
            .join();
        SongResult songResult = JSONObject.parseObject(response.body(), SongResult.class);
        if (Objects.isNull(songResult)) {
            throw new MusicApiException("艺术家" + artistSongsParam.getId() + "的歌曲获取失败");
        }
        if (songResult.getCode() != NeteaseCodeStatus.OK) {
            throw new MusicApiException(songResult.getCode().getMsg());
        }
        return songResult.getSongs();
    }

    /**
     * 根据音乐编号，获取音乐详细信息（不包括歌曲播放链接）。支持同时查询多个音乐编号
     *
     * <p>如需获取歌曲播放链接，则使用获取到的歌曲编号，再次请求 {@link #getMusicUrl(Collection)}</p>
     *
     * @param trackIds 音乐 trackId
     * @return 包含歌曲信息的列表
     */
    public List<Song> songByMusicId(Collection<Long> trackIds) {
        String idsStr = trackIds.stream()
            .map(String::valueOf).collect(Collectors.joining(",")); // 多个编号使用 , 号分割
        RequestItem requestItem = RequestItem
            .newBuilder(cloudHost + NeteaseNodeApiConstant.SONG_DETAIL)
            .form("ids", idsStr).build();
        HttpResponse<String> response = httpRequestUtil
            .sendAsync(requestItem, HttpResponse.BodyHandlers.ofString())
            .join();
        SongResult songResult = JSONObject.parseObject(response.body(), SongResult.class);
        if (Objects.isNull(songResult)) {
            throw new MusicApiException("歌曲详情获取失败");
        }
        if (songResult.getCode() != NeteaseCodeStatus.OK) {
            throw new MusicApiException(songResult.getCode().getMsg());
        }
        return songResult.getSongs();
    }

    /**
     * 获取音乐播放链接，支持同时获取多个歌曲播放链接
     *
     * @param ids 需要查询的 id， 可以为一个也可以为多个
     * @return 音乐链接列表
     */
    public List<Music> getMusicUrl(Collection<Long> ids) {
        String idsStr = ids.stream().map(String::valueOf).collect(Collectors.joining(","));
        RequestItem requestItem = RequestItem
            .newBuilder(cloudHost + NeteaseNodeApiConstant.AUDIO_URL)
            .form("id", idsStr).build();
        HttpResponse<String> response = httpRequestUtil
            .sendAsync(requestItem, HttpResponse.BodyHandlers.ofString())
            .join();
        MusicResult musicResult = JSONObject.parseObject(response.body(), MusicResult.class);
        List<Music> musicList = null;
        if (musicResult.getCode() == NeteaseCodeStatus.OK) {
            musicList = musicResult.getData();
        }
        // 使用此接口获取可能会出现 403 的情况，需手动拼接 URL
        // https://binaryify.github.io/NeteaseCloudMusicApi/#/?id=%e8%8e%b7%e5%8f%96%e9%9f%b3%e4%b9%90-url
        // 当获取的音乐列表为空时，则手动拼接 URL
        if (Objects.isNull(musicList)) {
            musicList = ids.stream().map(id -> {
                Music music = new Music();
                music.setId(id);
                music.setUrl(
                    MessageFormat.format(NeteaseNodeApiConstant.AUDIO_URL_STR_FORMAT, music.getId())
                );
                return music;
            }).collect(Collectors.toList());
        } else {
            // 部分 URL 不存在
            musicList = musicResult.getData().stream().peek(music -> {
                if (!StringUtils.hasLength(music.getUrl())) {
                    music.setUrl(
                        MessageFormat.format(NeteaseNodeApiConstant.AUDIO_URL_STR_FORMAT, music.getId())
                    );
                }}).collect(Collectors.toList());
        }
        return musicList;
    }

    /**
     * 根据音乐 ID ，获取其对应的音乐歌词
     * <p>未查询到的歌词以 <pre>[00:00.000]暂无歌词</pre> 替代</p>
     *
     * @param id 音乐编号
     * @return 音乐歌词
     */
    public Lyric getLyric(Long id) {
        RequestItem requestItem = RequestItem
            .newBuilder(cloudHost + NeteaseNodeApiConstant.LYRIC)
            .form("id", id).build();
        HttpResponse<String> response = httpRequestUtil
            .sendAsync(requestItem, HttpResponse.BodyHandlers.ofString())
            .join();
        LyricResult lyricResult = JSONObject.parseObject(response.body(), LyricResult.class);
        if (lyricResult.getCode() != NeteaseCodeStatus.OK) {
            throw new MusicApiException(lyricResult.getCode().getMsg());
        }
        if (!StringUtils.hasLength(lyricResult.getLrc().getLyric())) {
            lyricResult.getLrc().setLyric("[00:00.000]暂无歌词");
        }
        return lyricResult.getLrc();
    }
}
