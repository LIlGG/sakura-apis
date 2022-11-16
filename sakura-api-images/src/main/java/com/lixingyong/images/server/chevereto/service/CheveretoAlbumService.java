package com.lixingyong.images.server.chevereto.service;

import com.lixingyong.common.autoconfigure.service.ApiService;
import com.lixingyong.images.server.chevereto.CheveretoContext;
import com.lixingyong.images.server.chevereto.dao.AlbumDao;
import com.lixingyong.images.server.chevereto.model.dto.AlbumDTO;
import com.lixingyong.images.server.chevereto.model.dto.base.BaseAlbumDTO;
import com.lixingyong.images.server.chevereto.model.dto.base.BaseImageDTO;
import com.lixingyong.images.server.chevereto.model.dto.base.BaseUserDTO;
import com.lixingyong.images.server.chevereto.model.entity.AlbumEntity;
import com.lixingyong.images.server.chevereto.model.entity.ImageEntity;
import com.lixingyong.images.server.chevereto.model.entity.UserEntity;
import com.lixingyong.images.service.AlbumService;
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
import org.springframework.util.Assert;

/**
 * @author LIlGG
 * @since 2022-10-02
 */
@ApiService
public class CheveretoAlbumService implements AlbumService<AlbumEntity, BaseAlbumDTO> {

    private final static Logger logger = LoggerFactory.getLogger(CheveretoAlbumService.class);

    private final CheveretoContext context;

    private final AlbumDao albumDao;

    private final AlbumService<AlbumEntity, BaseAlbumDTO> albumService; // 注入自身，AOP 自调用

    private final UserService<UserEntity, BaseUserDTO> userService;

    private final ImageService<ImageEntity, BaseImageDTO> imageService;

    public CheveretoAlbumService(CheveretoContext context,
                                 AlbumDao albumDao,
                                 @Lazy AlbumService<AlbumEntity, BaseAlbumDTO> albumService,
                                 @Lazy UserService<UserEntity, BaseUserDTO> userService,
                                 @Lazy ImageService<ImageEntity, BaseImageDTO> imageService) {
        this.context = context;
        this.albumDao = albumDao;
        this.albumService = albumService;
        this.userService = userService;
        this.imageService = imageService;
    }

    @Override
    public List<?> selectImagesUrls(String albumCode) {
        Assert.notNull(albumCode, "albumCode 不能为空");
        // 将相册 code 转为数据库编号
        long id = context.getChangeID().decodeID(albumCode);
        return imageService.getCacheData().stream()
            .filter(image -> image.getAlbum().getId() == id)
            .map(image -> {
                BaseImageDTO imageDTO = new BaseImageDTO();
                BeanUtils.copyProperties(image, imageDTO);
                return imageDTO;
            }).collect(Collectors.toList());
    }

    @Override
    public List<BaseAlbumDTO> selectList() {
        return albumService.getCacheData().stream().map(album -> {
            BaseAlbumDTO baseAlbumDTO = new BaseAlbumDTO();
            BeanUtils.copyProperties(album, baseAlbumDTO);
            return baseAlbumDTO;
        }).collect(Collectors.toList());
    }

    @Override
    public AlbumDTO selectDetailInfo(String code) {
        long id = context.getChangeID().decodeID(code);
        AlbumEntity album = albumService.getCacheData().stream()
            .filter(a -> a.getId() == id)
            .findFirst()
            .orElseThrow(() -> new ImagesApiException("相册" + code + " 对应的相册不存在"));
        // 根据相册所属用户 ID，获取相册用户信息（只需要获取到基础的用户信息即可）
        BaseUserDTO userDTO = userService.getById(album.getUser().getId());
        // 转换数据格式
        AlbumDTO albumDTO = new AlbumDTO();
        BeanUtils.copyProperties(album, albumDTO);
        albumDTO.setCode(code);
        albumDTO.setUser(userDTO);
        return albumDTO;
    }

    @Override
    public List<BaseAlbumDTO> getPublicAlbumsByUserId(Number userId) {
        List<AlbumEntity> albums = albumService.getCacheData();
        return albums.stream()
            .filter(album -> album.getUser().getId() == userId.longValue())
            .map(album -> {
                BaseAlbumDTO baseAlbumDTO = new BaseAlbumDTO();
                BeanUtils.copyProperties(album, baseAlbumDTO);
                return baseAlbumDTO;
            }).collect(Collectors.toList());
    }

    @Override
    public BaseAlbumDTO getById(Number id) {
        AlbumEntity album = albumService.getCacheData().stream()
            .filter(a -> a.getId() == id.longValue())
            .findFirst()
            .orElseThrow(() -> new ImagesApiException("相册编号:" + id + " 对应的相册不存在"));
        BaseAlbumDTO albumDTO = new BaseAlbumDTO();
        BeanUtils.copyProperties(album, albumDTO);
        return albumDTO;
    }

    @Override
    @Cacheable(
        value = "image-chevereto-cache",
        key = "T(com.lixingyong.images.server.chevereto.cache.CheveretoCacheConstant).PUBLIC_ALBUM_ALL_CACHE_KEY"
    )
    public List<AlbumEntity> getCacheData() {
        return albumService.loadAndCacheData();
    }

    @Override
    public List<AlbumEntity> loadAndCacheData() {
        List<AlbumEntity> albumList = albumDao.findPublicAlbum();
        albumList.forEach(album -> {
            String code = context.getChangeID().encodeID(album.getId());
            album.setCode(code);
            album.setUrl(context.getHost() + context.getProperties().getAlbumPath() + "/" + code);
        });
        logger.info("[Image]Chevereto -> 相册缓存数据已更新");
        return albumList;
    }
}
