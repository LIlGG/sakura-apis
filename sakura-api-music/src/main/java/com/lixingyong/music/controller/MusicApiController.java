package com.lixingyong.music.controller;

import com.lixingyong.common.autoconfigure.ApiApplicationContext;
import com.lixingyong.music.model.entity.AudioEntity;
import com.lixingyong.music.model.entity.SearchEntity;
import com.lixingyong.music.model.param.BaseParam;
import com.lixingyong.music.model.param.IdParam;
import com.lixingyong.music.model.param.SearchListParam;
import com.lixingyong.music.process.FormatProcess;
import com.lixingyong.music.process.FormatProcessFactory;
import com.lixingyong.music.utils.MusicApiException;
import com.lixingyong.music.service.MusicApiService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.io.IOException;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 音乐接口，使用此控制层将根据请求条件获取各种服务的音乐并按要求返回特定数据<br><br>
 *
 * 例如：
 * <ol>
 *  <li>从歌单中获取音乐</li>
 *  <li>从歌手中获取音乐</li>
 *  <li>使用搜索功能获取音乐</li>
 *  <li>使用精品歌单分类获取音乐</li>
 * </ol>
 *
 * <p>返回的格式根据请求参数不同，需要符合各种需求。
 * 默认返回 <a href="https://aplayer.js.org/#/zh-Hans/">aplayer</a>要求的格式
 * </p>
 *
 * @see BaseParam
 * @author LIlGG
 * @since 2022-09-22
 */
@RestController
@RequestMapping("/music")
@Api(tags = "音乐 API 接口")
public class MusicApiController<PARAMS extends BaseParam> {

    private static final Logger logger = LoggerFactory.getLogger(MusicApiController.class);

    /**
     * 此处 Request 是线程安全的，实际为代理类，但多线程环境下仅保证父子线程安全。若要使用线程池则建议使用方法体内部变量
     */
    private final HttpServletRequest request;

    private final ApiApplicationContext applicationContext;

    public MusicApiController(HttpServletRequest request,
                              ApiApplicationContext applicationContext) {
        this.request = request;
        this.applicationContext = applicationContext;
    }

    @GetMapping(params={ "type=playlist" })
    @ApiOperation("获取歌单列表下的音乐")
    public List<?> getPlaylist(@Valid IdParam idParam) {
        List<AudioEntity> audios = applicationContext.getServiceBean(MusicApiService.class).playlistAudio(idParam.getId());
        FormatProcess formatProcess = FormatProcessFactory.getFormatProcess(idParam.getRType());
        return (List<?>) formatProcess.process(audios, request.getRequestURL().toString(), idParam.getServer());
    }

    /**
     * 获取搜索列表下搜索到的音频
     */
    @GetMapping(params={ "type=search" })
    @ApiOperation("搜索功能")
    public List<?> search(@Valid SearchListParam params) {
        SearchEntity<?> search = applicationContext.getServiceBean(MusicApiService.class).search(params);
        FormatProcess formatProcess = FormatProcessFactory.getFormatProcess(params.getRType());
        return (List<?>) formatProcess.process(search, request.getRequestURL().toString(), params.getServer());
    }

    /**
     * 根据歌曲链接获取实际歌曲
     *
     * @param param param
     */
    @GetMapping(params={ "type=url" })
    @ApiOperation("获取歌曲播放链接")
    public void url(@Valid IdParam param, HttpServletResponse response) {
        String audioUrl = applicationContext.getServiceBean(MusicApiService.class).audioUrl(param.getId());
        if (!StringUtils.hasLength(audioUrl)) {
            throw new MusicApiException("获取歌曲播放链接失败");
        }
        try {
            response.sendRedirect(audioUrl);
        } catch (IOException e) {
            e.printStackTrace();
            logger.error("audio sendRedirect error. id: {}, audioUrl: {}", param.getId(), audioUrl);
        }
    }

    /**
     * 获取歌曲封面
     */
    @GetMapping(params={ "type=pic" })
    @ApiOperation("获取歌曲封面")
    public void pic(@Valid IdParam param, HttpServletResponse response) {
        String audioPic = applicationContext.getServiceBean(MusicApiService.class).audioPic(param.getId());
        if (!StringUtils.hasLength(audioPic)) {
            throw new MusicApiException("获取歌曲封面失败");
        }
        try {
            response.sendRedirect(audioPic);
        } catch (IOException e) {
            e.printStackTrace();
            logger.error("audio pic sendRedirect error. id: {}, audioPic: {}", param.getId(), audioPic);
        }
    }

    /**
     * 获取歌词
     */
    @GetMapping(params={ "type=lrc" })
    @ApiOperation("获取歌词")
    public String lrc(@Valid IdParam param) {
        return applicationContext.getServiceBean(MusicApiService.class).audioLrc(param.getId());
    }

    /**
     * 默认功能设置为搜索
     *
     * <p>默认搜索时，参数为 hello</p>
     *
     * @see SearchListParam
     */
    @GetMapping
    public List<?> defaultAudio(@Valid SearchListParam params) {
        return search(params);
    }
}
