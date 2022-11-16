package com.lixingyong.images.server.chevereto.service;

import com.lixingyong.common.autoconfigure.service.ApiService;
import com.lixingyong.images.server.chevereto.CheveretoContext;
import com.lixingyong.images.server.chevereto.dao.ImageDao;
import com.lixingyong.images.server.chevereto.model.dto.ImageDTO;
import com.lixingyong.images.server.chevereto.model.dto.base.BaseAlbumDTO;
import com.lixingyong.images.server.chevereto.model.dto.base.BaseCategoryDTO;
import com.lixingyong.images.server.chevereto.model.dto.base.BaseImageDTO;
import com.lixingyong.images.server.chevereto.model.dto.base.BaseUserDTO;
import com.lixingyong.images.server.chevereto.model.entity.AlbumEntity;
import com.lixingyong.images.server.chevereto.model.entity.CategoryEntity;
import com.lixingyong.images.server.chevereto.model.entity.ImageEntity;
import com.lixingyong.images.server.chevereto.model.entity.UserEntity;
import com.lixingyong.images.server.chevereto.model.enums.ImageSizeType;
import com.lixingyong.images.service.AlbumService;
import com.lixingyong.images.service.CategoryService;
import com.lixingyong.images.service.ImageService;
import com.lixingyong.images.service.UserService;
import com.lixingyong.images.utils.ImagesApiException;
import java.util.List;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.Lazy;

/**
 * @author LIlGG
 * @since 2022-10-02
 */
@ApiService
public class CheveretoImageService implements ImageService<ImageEntity, BaseImageDTO> {

    private final static Logger logger = LoggerFactory.getLogger(CheveretoImageService.class);

    private final CheveretoContext context;

    private final ImageDao imageDao;

    private final ImageService<ImageEntity, BaseImageDTO> imageService;

    private final AlbumService<AlbumEntity, BaseAlbumDTO> albumService;

    private final CategoryService<CategoryEntity, BaseCategoryDTO> categoryService;

    private final UserService<UserEntity, BaseUserDTO> userService;

    public CheveretoImageService(CheveretoContext context,
                                 ImageDao imageDao,
                                 AlbumService<AlbumEntity, BaseAlbumDTO> albumService,
                                 @Lazy ImageService<ImageEntity, BaseImageDTO> imageService,
                                 CategoryService<CategoryEntity, BaseCategoryDTO> categoryService,
                                 @Lazy UserService<UserEntity, BaseUserDTO> userService) {
        this.context = context;
        this.imageDao = imageDao;
        this.imageService = imageService;
        this.albumService = albumService;
        this.categoryService = categoryService;
        this.userService = userService;
    }

    @Override
    public List<?> selectImagesUrls(String searchParam) {
        return imageService.getCacheData().stream()
            .map(image -> {
                BaseImageDTO imageDTO = new BaseImageDTO();
                BeanUtils.copyProperties(image, imageDTO);
                return imageDTO;
            }).collect(Collectors.toList());
    }

    @Override
    public BaseImageDTO selectDetailInfo(String code) {
        long id = context.getChangeID().decodeID(code);
        ImageEntity image = imageService.getCacheData().stream()
            .filter(i -> i.getId() == id)
            .findFirst()
            .orElseThrow(() -> new ImagesApiException("图片 " + code + " 不存在"));
        // 根据图片所属相册ID，获取相册信息
        BaseAlbumDTO baseAlbumDTO = albumService.getById(image.getAlbum().getId());
        // 根据图片所属分类ID，获取分类信息
        BaseCategoryDTO baseCategoryDTO = categoryService.getById(image.getCategory().getId());
        // 根据图片所属用户ID，获取用户信息
        BaseUserDTO baseUserDTO = userService.getById(image.getUser().getId());
        // 封装所有数据
        ImageDTO imageDTO = new ImageDTO();
        BeanUtils.copyProperties(image, imageDTO);
        imageDTO.setCode(code);
        imageDTO.setAlbum(baseAlbumDTO);
        imageDTO.setCategory(baseCategoryDTO);
        imageDTO.setUser(baseUserDTO);
        return imageDTO;
    }

    @Override
    public String getImageUrl(String id) {
        return ImageService.super.getImageUrl(id);
    }

    @Override
    @Cacheable(
        value = "image-chevereto-cache",
        key = "T(com.lixingyong.images.server.chevereto.cache.CheveretoCacheConstant).PUBLIC_IMAGE_ALL_CACHE_KEY"
    )
    public List<ImageEntity> getCacheData() {
        return imageService.loadAndCacheData();
    }

    @Override
    public List<ImageEntity> loadAndCacheData() {
        List<ImageEntity> images = imageDao.findPublicImage().stream()
            .sorted((m1, m2) -> m2.getId().compareTo(m1.getId()))
            .peek(image -> {
                image.setCode(context.getChangeID().encodeID(image.getId()));
                image.setUrl(context.createUrl(image, ImageSizeType.ORIGINAL));
                image.setMediumUrl(context.createUrl(image, ImageSizeType.MEDIUM));
                image.setThumbnailUrl(context.createUrl(image, ImageSizeType.THUMBNAIL));
            }).collect(Collectors.toList());
        logger.info("[Image]Chevereto -> 图片缓存数据已更新");
        return images;
    }
}
