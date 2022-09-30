package com.lixingyong.music.service;

import com.lixingyong.music.model.entity.AudioEntity;
import com.lixingyong.music.model.entity.PlaylistEntity;
import com.lixingyong.music.model.entity.SearchEntity;
import com.lixingyong.music.model.param.SearchListParam;
import com.lixingyong.music.process.FormatProcess;
import com.lixingyong.music.utils.MusicApiException;
import java.util.List;
import org.springframework.lang.Nullable;

/**
 * 音乐服务接口
 *
 * <p>此接口提供包括但不限于如下功能</p>
 * <ul>
 *     <li>获取歌单列表中的粗略歌曲信息</li>
 *     <li>获取歌单内音乐数据</li>
 *     <li>获取详细歌曲数据</li>
 *     <li>获取歌曲播放链接</li>
 * </ul>
 *
 * <p>通用数据需要经过 {@link FormatProcess} 转换为播放器格式。</p>
 *
 * @see AudioEntity
 * @see PlaylistEntity
 * @see FormatProcess
 *
 * @author LIlGG
 * @since 2021/9/27
 */
public interface MusicApiService {
    /**
     * 获取歌单内的音乐列表
     *
     * @see AudioEntity
     *
     * @param id 歌单编号
     * @return 歌单音乐
     */
    default List<AudioEntity> playlistAudio(long id) {
        throw new MusicApiException("该服务不支持此接口");
    }

    /**
     * 获取艺术家的音乐列表
     *
     * @see AudioEntity
     *
     */
    default List<AudioEntity> artistAudios(long id) {
        throw new MusicApiException("该服务不支持此接口");
    }

    /**
     * 根据音频编号获取音频真实 URL
     *
     * @param audioId 音频编号
     * @return 真实音频地址
     */
    @Nullable
    default String audioUrl(Long audioId) {
        throw new MusicApiException("该服务不支持此接口");
    }

    /**
     * 根据音频编号获取音频封面
     *
     * @param audioId 音频编号
     * @return 音频封面图
     */
    @Nullable
    default String audioPic(Long audioId) {
        throw new MusicApiException("该服务不支持此接口");
    }

    /**
     * 根据音频编号获取音频歌词
     *
     * @param audioId 音频编号
     * @return 音频歌词
     */
    default String audioLrc(Long audioId) {
        throw new MusicApiException("该服务不支持此接口");
    }

    /**
     * 网易云音乐搜索功能
     *
     * @param params 搜索入参
     * @return 搜索到的列表
     */
    default SearchEntity<?> search(SearchListParam params) {
        throw new MusicApiException("该服务不支持此接口");
    }
}
