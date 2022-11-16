package com.lixingyong.images.server.chevereto.service;

import com.lixingyong.common.autoconfigure.service.ApiService;
import com.lixingyong.images.server.chevereto.CheveretoContext;
import com.lixingyong.images.server.chevereto.dao.UserDao;
import com.lixingyong.images.server.chevereto.model.dto.UserDTO;
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
public class CheveretoUserService implements UserService<UserEntity, BaseUserDTO> {

    private final static Logger logger = LoggerFactory.getLogger(CheveretoUserService.class);

    private final CheveretoContext context;

    private final UserDao userDao;

    private final UserService<UserEntity, BaseUserDTO> userService;

    private final AlbumService<AlbumEntity, BaseAlbumDTO> albumService;

    private final ImageService<ImageEntity, BaseImageDTO> imageService;

    public CheveretoUserService(CheveretoContext context,
                                UserDao userDao,
                                @Lazy UserService<UserEntity, BaseUserDTO> userService,
                                AlbumService<AlbumEntity, BaseAlbumDTO> albumService,
                                @Lazy ImageService<ImageEntity, BaseImageDTO> imageService) {
        this.context = context;
        this.userDao = userDao;
        this.userService = userService;
        this.albumService = albumService;
        this.imageService = imageService;
    }

    @Override
    public List<?> selectImagesUrls(String userName) {
        Assert.notNull(userName, "userName 不能为空");
        return imageService.getCacheData().stream()
            .filter(image -> userName.equals(image.getUser().getUsername()))
            .map(image -> {
                BaseImageDTO imageDTO = new BaseImageDTO();
                BeanUtils.copyProperties(image, imageDTO);
                return imageDTO;
            }).collect(Collectors.toList());
    }

    @Override
    public List<BaseUserDTO> selectList() {
        List<UserEntity> users = userService.getCacheData();
        return users.stream().map(user -> {
            BaseUserDTO baseUser = new BaseUserDTO();
            BeanUtils.copyProperties(user, baseUser);
            return baseUser;
        }).collect(Collectors.toList());
    }

    @Override
    public UserDTO selectDetailInfo(String userName) {
        List<UserEntity> users = userService.getCacheData();
        UserEntity user = users.stream()
            .filter(u -> u.getUsername().equals(userName))
            .findFirst()
            .orElseThrow(() -> new ImagesApiException("获取用户 " + userName + " 失败，用户不存在"));
        // 获取当前用户下所有公开的相册
        List<BaseAlbumDTO> baseAlbums = albumService.getPublicAlbumsByUserId(user.getId());

        UserDTO userDTO = new UserDTO();
        BeanUtils.copyProperties(user, userDTO);
        userDTO.setAlbums(baseAlbums);
        return userDTO;
    }

    @Override
    public BaseUserDTO getById(Number id) {
        List<UserEntity> users = userService.getCacheData();
        UserEntity user = users.stream()
            .filter(u -> u.getId() == id.longValue())
            .findFirst()
            .orElseThrow(() -> new ImagesApiException("获取用户编号： " + id + " 失败，用户不存在"));
        BaseUserDTO baseUserDTO = new BaseUserDTO();
        BeanUtils.copyProperties(user, baseUserDTO);
        return baseUserDTO;
    }

    @Override
    @Cacheable(
        value = "image-chevereto-cache",
        key = "T(com.lixingyong.images.server.chevereto.cache.CheveretoCacheConstant).PUBLIC_USER_ALL_CACHE_KEY"
    )
    public List<UserEntity> getCacheData() {
        return userService.loadAndCacheData();
    }

    @Override
    public List<UserEntity> loadAndCacheData() {
        List<UserEntity> users = userDao.findPublicUser();
        users.forEach(user -> {
            user.setUrl(context.getHost() + context.getProperties().getUserPath() + "/" + user.getUsername());
        });
        logger.info("[Image]Chevereto -> 用户缓存数据已更新");
        return users;
    }
}
