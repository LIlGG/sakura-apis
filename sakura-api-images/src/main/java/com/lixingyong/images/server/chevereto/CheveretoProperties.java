package com.lixingyong.images.server.chevereto;

import com.lixingyong.images.server.chevereto.jdbc.CheveretoDataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author LIlGG
 * @since 2022-10-02
 */
@ConfigurationProperties(prefix = "api.images.chevereto")
public class CheveretoProperties {
    /**
     * 相册默认地址
     */
    private static final String ALBUM_URL_PATH_DEFAULT = "/album";

    /**
     * 分类默认地址
     */
    private static final String CATEGORY_URL_PATH_DEFAULT = "/category";

    /**
     * 图片默认地址
     */
    private static final String IMAGE_URL_PATH_DEFAULT = "/images";

    /**
     * 用户默认地址
     */
    private static final String USER_URL_PATH_DEFAULT = "/";

    /**
     * chevereto 数据加密盐，在 chevereto 仪表盘中设置
     */
    private String cryptSalt;

    /**
     * 自建的 chevereto 图库地址，用于拼接图片访问地址
     */
    private String host;

    /**
     * 相册路径
     */
    private String albumPath;

    /**
     * 分类路径
     */
    private String categoryPath;

    /**
     * 图片路径
     */
    private String imagePath;

    /**
     * 用户路径
     */
    private String userPath;

    /**
     * chevereto 所在数据源
     */
    private CheveretoDataSourceProperties datasource;

    public CheveretoProperties() {
        this.datasource = new CheveretoDataSourceProperties();
        this.albumPath = ALBUM_URL_PATH_DEFAULT;
        this.categoryPath = CATEGORY_URL_PATH_DEFAULT;
        this.imagePath = IMAGE_URL_PATH_DEFAULT;
        this.userPath = USER_URL_PATH_DEFAULT;
    }

    public CheveretoDataSourceProperties getDatasource() {
        return datasource;
    }

    public void setDatasource(CheveretoDataSourceProperties datasource) {
        this.datasource = datasource;
    }

    public String getCryptSalt() {
        return cryptSalt;
    }

    public void setCryptSalt(String cryptSalt) {
        this.cryptSalt = cryptSalt;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getAlbumPath() {
        return albumPath;
    }

    public void setAlbumPath(String albumPath) {
        this.albumPath = albumPath;
    }

    public String getCategoryPath() {
        return categoryPath;
    }

    public void setCategoryPath(String categoryPath) {
        this.categoryPath = categoryPath;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getUserPath() {
        return userPath;
    }

    public void setUserPath(String userPath) {
        this.userPath = userPath;
    }
}
