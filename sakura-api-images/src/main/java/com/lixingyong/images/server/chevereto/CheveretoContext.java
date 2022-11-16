package com.lixingyong.images.server.chevereto;

import com.lixingyong.images.server.chevereto.model.entity.ImageEntity;
import com.lixingyong.images.server.chevereto.model.entity.StoragesEntity;
import com.lixingyong.images.server.chevereto.model.enums.ImageSizeType;
import com.lixingyong.images.server.chevereto.util.ChangeID;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

/**
 * @author LIlGG
 * @since 2022-10-18
 */
public class CheveretoContext {

    private ChangeID changeID;

    private CheveretoProperties properties;

    private String host;

    public ChangeID getChangeID() {
        return changeID;
    }

    public void setChangeID(ChangeID changeID) {
        this.changeID = changeID;
    }

    public CheveretoProperties getProperties() {
        return properties;
    }

    public void setProperties(CheveretoProperties properties) {
        Assert.notNull(properties.getHost(), "host 不得为空");
        this.properties = properties;
        this.host = properties.getHost();
    }

    public String getHost() {
        return this.host;
    }

    public String createUrl(ImageEntity image, ImageSizeType imageSizeType) {
        String imageSizeTypeStr;
        switch (imageSizeType) {
            case MEDIUM:
                imageSizeTypeStr = ".md";
                break;
            case THUMBNAIL:
                imageSizeTypeStr = ".th";
                break;
            default:
                imageSizeTypeStr = "";
                break;
        }
        return createUrl(image, imageSizeTypeStr);
    }

    /**
     * 拼接图片 url
     */
    public String createUrl(ImageEntity image, String imageSizeTypeStr) {
        Assert.notNull(image, "图片属性不能为空");
        Assert.notNull(image.getName(), "图片名称不能为空");
        Assert.notNull(image.getExtension(), "图片类型不能为空");
        String folderPath = "";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        switch (image.getStorageMode()) {
            case DATE_FOLDER:
                Date date = image.getDate();
                Assert.notNull(date, "日期文件格式下，日期必须存在！");
                // 格式化日期
                String[] dateStr = sdf.format(date).split("-");
                folderPath = dateStr[0] + "/" + dateStr[1] + "/" + dateStr[2];
                break;
            case DIRECT:
                break;
        }
        return this.getImageHost(image) + "/" + folderPath + "/" + image.getName()
            + imageSizeTypeStr + "." + image.getExtension();
    }

    public String getImageHost(ImageEntity image) {
        StoragesEntity storage = image.getStorage();
        if (Objects.nonNull(storage) && StringUtils.hasLength(storage.getUrl())) {
            return image.getStorage().getUrl();
        }
        return this.host + this.properties.getImagePath();
    }
}
