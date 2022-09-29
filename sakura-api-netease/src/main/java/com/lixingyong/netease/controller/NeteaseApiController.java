package com.lixingyong.netease.controller;

import com.lixingyong.netease.model.entity.AudioEntity;
import com.lixingyong.netease.model.entity.SearchEntity;
import com.lixingyong.netease.model.enums.FormatProcessType;
import com.lixingyong.netease.model.param.BaseParam;
import com.lixingyong.netease.model.param.IdParam;
import com.lixingyong.netease.model.param.SearchListParam;
import com.lixingyong.netease.process.FormatProcess;
import com.lixingyong.netease.process.FormatProcessFactory;
import com.lixingyong.netease.resource.NeteaseNodeJs;
import com.lixingyong.netease.service.NeteaseApiService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 查询网易云音频的接口，使用此控制层将根据请求条件获取网易云音乐并按要求返回特定数据<br><br>
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
@RequestMapping("/netease")
@Api(tags = "网易云音乐 API 接口")
public class NeteaseApiController<PARAMS extends BaseParam> {

    private static final Logger logger = LoggerFactory.getLogger(NeteaseApiController.class);

    /**
     * 此处 Request 是线程安全的，实际为代理类，但多线程环境下仅保证父子线程安全。若要使用线程池则建议使用方法体内部变量
     */
    private final HttpServletRequest request;

    private final NeteaseApiService apiService;

    public NeteaseApiController(HttpServletRequest request, NeteaseApiService apiService) {
        this.request = request;
        this.apiService = apiService;
    }

    @GetMapping(params={ "type=playlist" })
    @ApiOperation("获取歌单列表下的音乐")
    public List<?> getPlaylist(@Valid IdParam idParam) {
        List<AudioEntity> audios = apiService.playlistAudio(idParam.getId());
        FormatProcess formatProcess = FormatProcessFactory.getFormatProcess(idParam.getRType());
        return (List<?>) formatProcess.process(audios, request.getRequestURL().toString());
    }

    /**
     * 获取搜索列表下搜索到的音频
     */
    @GetMapping(params={ "type=search" })
    @ApiOperation("搜索功能")
    public List<?> search(@Valid SearchListParam params, HttpServletRequest request) {
        SearchEntity<?> search = apiService.search(params);
        FormatProcess formatProcess = FormatProcessFactory.getFormatProcess(params.getRType());
        return (List<?>) formatProcess.process(search, request.getRequestURL().toString());
    }
}
