package com.lixingyong.netease.service;

import com.lixingyong.netease.model.entity.AudioEntity;
import com.lixingyong.netease.model.entity.SearchEntity;
import com.lixingyong.netease.model.param.SearchListParam;
import java.util.List;
import org.springframework.lang.Nullable;

/**
 * 网易云音乐服务接口
 *
 * <p>此接口提供包括但不限于如下功能</p>
 * <ul>
 *     <li>获取歌单列表中的粗略歌曲信息</li>
 *     <li>获取歌单内音乐数据</li>
 *     <li>获取详细歌曲数据</li>
 *     <li>获取歌曲播放链接</li>
 * </ul>
 *
 * <p>此服务依托于网易云音乐 NodeJS 版本进行，并将部分数据其转换为特定格式。
 * 后续需要经过 {@link com.lixingyong.netease.process.FormatProcess}
 * 转换成播放器格式。</p>
 *
 * @see com.lixingyong.netease.model.entity.AudioEntity
 * @see com.lixingyong.netease.model.entity.PlaylistEntity
 * @see com.lixingyong.netease.process.FormatProcess
 *
 * @author LIlGG
 * @since 2021/9/27
 */
public interface NeteaseApiService {
    /**
     * 获取歌单内的音乐列表
     *
     * @see AudioEntity
     *
     * @param id 歌单编号
     * @return 歌单音乐
     */
    List<AudioEntity> playlistAudio(long id);

    /**
     * 获取艺术家的音乐列表
     *
     * @see AudioEntity
     *
     */
    List<AudioEntity> artistAudios(long id);

    /**
     * 根据音频编号获取音频真实 URL
     *
     * @param audioId 音频编号
     * @return 真实音频地址
     */
    @Nullable
    String audioUrl(Long audioId);

    /**
     * 根据音频编号获取音频封面
     *
     * @param audioId 音频编号
     * @return 音频封面图
     */
    @Nullable
    String audioPic(Long audioId);

    /**
     * 根据音频编号获取音频歌词
     *
     * @param audioId 音频编号
     * @return 音频歌词
     */
    String audioLrc(Long audioId);

    /**
     * 网易云音乐搜索功能
     *
     * @param params 搜索入参
     * @return 搜索到的列表
     */
    SearchEntity<?> search(SearchListParam params);
}
