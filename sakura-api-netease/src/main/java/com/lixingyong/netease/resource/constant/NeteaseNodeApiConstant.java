package com.lixingyong.netease.resource.constant;

/**
 * 网易云音乐 NodeJS API 请求地址
 *
 * @see <a href="https://binaryify.github.io/NeteaseCloudMusicApi/">网易云音乐 NodeJs</a>
 *
 * @author LIlGG
 * @since 2021/7/29
 */
public class NeteaseNodeApiConstant {
    /***********************
     *     音乐接口
     ***********************/
    /**
     * 歌单详情
     */
    public final static String PLAYLIST = "/playlist/detail";
    /**
     * 精品歌单链接
     */
    public final static String HIGH_QUALITY_PLAYLIST = "/top/playlist/highquality";
    /**
     * 获取音乐详情
     */
    public final static String SONG_DETAIL = "/song/detail";
    /**
     * 获取音乐 URL
     */
    public final static String AUDIO_URL = "/song/url";
    /**
     * 获取音乐直链模板
     */
    public final static String AUDIO_URL_STR_FORMAT = "https://music.163.com/song/media/outer/url?id={0}.mp3";
    /**
     * 获取音乐歌词
     */
    public final static String LYRIC = "/lyric";
    /**
     * 搜索
     */
    public final static String SEARCH = "/cloudsearch";
    /**
     * 获取歌手全部歌曲
     */
    public final static String ARTIST_SONGS = "/artist/songs";

    /***********************
     *     非音乐接口
     ***********************/
    /**
     * 手机号登录
     */
    public final static String LOGIN_CELL_PHONE = "/login/cellphone";
    /**
     * 邮箱号登录
     */
    public final static String LOGIN_EMAIL = "/login";
    /**
     * 二维码登录 - 生成 key
     */
    public final static String LOGIN_QR_KEY = "/login/qr/key";
    /**
     * 二维码登录 - 二维码生成接口
     */
    public final static String LOGIN_QR_CREATE = "/login/qr/create";
    /**
     * 二维码登录 - 检测扫码状态
     */
    public final static String LOGIN_QR_CHECK = "/login/qr/check";
    /**
     * 发送验证码
     */
    public final static String CAPTCHA_SEND = "/captcha/sent";
    /**
     * 验证验证码
     */
    public final static String CAPTCHA_VERIFY = "/captcha/verify";
    /**
     * 获取登录状态
     */
    public final static String LOGIN_STATUS = "/login/status";
    /**
     * 获取用户详情
     */
    public final static String USER_DETAIL = "/user/detail";
}
