package com.lixingyong.images.server.chevereto.service;

import com.lixingyong.common.autoconfigure.service.ApiService;
import com.lixingyong.images.server.chevereto.CheveretoContext;
import com.lixingyong.images.server.chevereto.dao.CategoryDao;
import com.lixingyong.images.server.chevereto.model.dto.CategoryDTO;
import com.lixingyong.images.server.chevereto.model.dto.ImageDTO;
import com.lixingyong.images.server.chevereto.model.dto.base.BaseCategoryDTO;
import com.lixingyong.images.server.chevereto.model.dto.base.BaseImageDTO;
import com.lixingyong.images.server.chevereto.model.entity.CategoryEntity;
import com.lixingyong.images.server.chevereto.model.entity.ImageEntity;
import com.lixingyong.images.service.CategoryService;
import com.lixingyong.images.service.ImageService;
import com.lixingyong.images.utils.ImagesApiException;
import java.util.List;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.Lazy;
import org.springframework.util.Assert;

/**
 * @author LIlGG
 * @since 2022-10-02
 */
@ApiService
public class CheveretoCategoryService implements CategoryService<CategoryEntity, BaseCategoryDTO> {

    private final static Logger logger = LoggerFactory.getLogger(CheveretoCategoryService.class);

    private final CheveretoContext context;

    private final CategoryDao categoryDao;

    private final CategoryService<CategoryEntity, BaseCategoryDTO> categoryService;

    private final ImageService<ImageEntity, ImageDTO> imageService;

    public CheveretoCategoryService(CheveretoContext context,
                                    CategoryDao categoryDao,
                                    @Lazy CategoryService<CategoryEntity, BaseCategoryDTO> categoryService,
                                    @Lazy ImageService<ImageEntity, ImageDTO> imageService) {
        this.context = context;
        this.categoryDao = categoryDao;
        this.categoryService = categoryService;
        this.imageService = imageService;
    }

    @Override
    public List<?> selectImagesUrls(String urlKey) {
        Assert.notNull(urlKey, "urlKey 不能为空");
        return imageService.getCacheData().stream()
            .filter(image -> urlKey.equals(image.getCategory().getUrlKey()))
            .map(image -> {
                BaseImageDTO imageDTO = new BaseImageDTO();
                BeanUtils.copyProperties(image, imageDTO);
                return imageDTO;
            }).collect(Collectors.toList());
    }

    @Override
    public List<BaseCategoryDTO> selectList() {
        return categoryService.getCacheData().stream()
            .map(category -> {
                BaseCategoryDTO baseCategoryDTO = new BaseCategoryDTO();
                BeanUtils.copyProperties(category, baseCategoryDTO);
                return baseCategoryDTO;
            }).collect(Collectors.toList());
    }

    @Override
    public CategoryDTO selectDetailInfo(String urlKey) {
        CategoryEntity category = categoryService.getCacheData().stream()
            .filter(c -> c.getUrlKey().equals(urlKey))
            .findFirst()
            .orElseThrow(() -> new ImagesApiException("分类 " + urlKey + " 不存在"));
        CategoryDTO categoryDTO = new CategoryDTO();
        BeanUtils.copyProperties(category, categoryDTO);
        return categoryDTO;
    }

    @Override
    public BaseCategoryDTO getById(Number id) {
        CategoryEntity category = categoryService.getCacheData().stream()
            .filter(c -> c.getId() == id.longValue())
            .findFirst()
            .orElseThrow(() -> new ImagesApiException("分类 " + id + " 不存在"));
        BaseCategoryDTO baseCategoryDTO = new BaseCategoryDTO();
        BeanUtils.copyProperties(category, baseCategoryDTO);
        return baseCategoryDTO;
    }

    @Override
    @Cacheable(
        value = "image-chevereto-cache",
        key = "T(com.lixingyong.images.server.chevereto.cache.CheveretoCacheConstant).PUBLIC_CATEGORY_ALL_CACHE_KEY"
    )
    public List<CategoryEntity> getCacheData() {
        return categoryService.loadAndCacheData();
    }

    @Override
    public List<CategoryEntity> loadAndCacheData() {
        List<CategoryEntity> categoryList = this.categoryDao.findAll();
        categoryList.forEach(category -> {
            category.setUrl(context.getHost() + context.getProperties().getCategoryPath() + "/" + category.getUrlKey());
        });
        logger.info("[Image]Chevereto -> 分类缓存数据已更新");
        return categoryList;
    }
}
