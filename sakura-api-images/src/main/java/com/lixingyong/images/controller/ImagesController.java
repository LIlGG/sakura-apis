package com.lixingyong.images.controller;

import com.lixingyong.common.autoconfigure.ApiApplicationContext;
import com.lixingyong.images.model.enums.ImageSearchKind;
import com.lixingyong.images.model.enums.ImageSizeType;
import com.lixingyong.images.model.enums.ServiceType;
import com.lixingyong.images.model.param.ImageSearchParam;
import com.lixingyong.images.model.param.ImageUrlParam;
import com.lixingyong.images.model.param.BaseParam;
import com.lixingyong.images.model.param.IdParam;
import com.lixingyong.images.server.chevereto.model.dto.base.BaseImageDTO;
import com.lixingyong.images.service.ImageService;
import com.lixingyong.images.service.ImagesApiService;
import com.lixingyong.images.utils.ImagesApiException;
import com.lixingyong.common.exception.ApiException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import javax.validation.Valid;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.lixingyong.common.utils.ValueEnum;

/**
 * 随机图接口，此接口需要根据用户输入条件来检索图库中的数据，并按照特定格式返回给用户。
 *
 * <p>此接口包括如下功能</p>
 * <ul>
 *     <li>获取图片列表（任何图库都应支持）</li>
 *     <li>获取其他列表，例如用户、相册、分类等列表（部分图库可能不支持）</li>
 *     <li>获取对应图片的详细信息（部分图库可能不支持）</li>
 *     <li>根据链接或编号获取某个图片（任何图库都应支持）</li>
 * </ul>
 *
 * 此随机图返回映射格式应固定不变
 *
 * @author LIlGG
 * @since 2022-10-02
 */
@RestController
@RequestMapping("/images")
@Api(tags = "随机图 API 接口")
public class ImagesController {

    private final ApiApplicationContext applicationContext;

    public ImagesController(ApiApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @GetMapping(params={ "type=urls" })
    @ApiOperation("获取图片列表")
    public List<?> getUrls(@Valid ImageSearchParam searchParam) {
        List<?> images = getServiceInterface(searchParam.getIType()).selectImagesUrls(searchParam.getId());
        switch (ValueEnum.valueToEnum(ImageSearchKind.class, searchParam.getKind())) {
            case RANDOM:
                Collections.shuffle(images);
                int count = Math.min(searchParam.getCount(), images.size());
                images = images.subList(0, count);
                break;
            case ORDER:
                // 取得 图片起始 start 的值（有可能与size 一致）
                int start = Math.min(images.size(), Math.max(searchParam.getStart(), 0));
                start = images.size() == start ? start - searchParam.getCount() : start;
                images = images.subList(start, start + searchParam.getCount());
                break;
        }
        return images;
    }

    @GetMapping(params = { "type=url" })
    @ApiOperation("获取图片链接")
    public String getUrlPath(@Valid ImageUrlParam urlParam) {
        ImagesApiService<?, ?> apiService = getServiceInterface(urlParam.getIType());
        if (!StringUtils.hasLength(urlParam.getId())) {
            if (!(apiService instanceof ImageService)) {
                throw new ImagesApiException("参数不正确，查询非 image 服务时， id 必须存在");
            }
        }
        BaseImageDTO imageDTO;
        if (apiService instanceof ImageService) {
            imageDTO = (BaseImageDTO)apiService.selectDetailInfo(urlParam.getId());
        } else {
            List<?> images = apiService.selectImagesUrls(urlParam.getId());
            Collections.shuffle(images);
            imageDTO = (BaseImageDTO) images.get(0);
        }
        if (Objects.isNull(imageDTO)) {
            throw new ApiException("未找到图片，请更换查询条件");
        }
        ImageSizeType imageSizeType = ValueEnum.valueToEnum(ImageSizeType.class, urlParam.getQn());
        switch (imageSizeType) {
            case MEDIUM:
                return imageDTO.getMediumUrl();
            case THUMBNAIL:
                return imageDTO.getThumbnailUrl();
            default:
                return imageDTO.getUrl();
        }
    }

    @GetMapping(params = { "type=list" })
    @ApiOperation("获取除图片之外的其他数据列表")
    public List<?> getOtherList(@Valid BaseParam param) {
        return getServiceInterface(param.getIType()).selectList();
    }

    @GetMapping(params = { "type=detail" })
    @ApiOperation("获取详细信息")
    public Object findDetail(@Valid IdParam idParam) {
        return getServiceInterface(idParam.getIType()).selectDetailInfo(idParam.getId());
    }

    private ImagesApiService<?, ?> getServiceInterface(String iType) {
        ServiceType serviceType = ValueEnum.valueToEnum(ServiceType.class, iType);
        return applicationContext.getServiceBean(serviceType.getServiceClass());
    }
}
