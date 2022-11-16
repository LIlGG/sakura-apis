package com.lixingyong.images.service;

import com.lixingyong.images.utils.ImagesApiException;
import java.util.List;

/**
 * 随机图 API 功能服务类，用于定义公共接口.
 *
 * <p>随机图将有可能从各种渠道中获取，例如从相册中获取某图片，从用户所有图片中获取某图片等等。
 * 基于此原因，在 {@code ImagesApiService} 中只定义各图片渠道的公共接口，而具体的功能由各
 * 子类负责实现</p>
 *
 * @see AlbumService
 * @see CategoryService
 * @see ImageService
 * @see UserService
 *
 * @author LIlGG
 * @since 2022-10-02
 */
public interface ImagesApiService<T, DTO> extends CacheService<T> {

    /**
     * 获取图片访问链接的集合
     *
     * @param id 需要查询的编号
     * @return 查询到的图片链接集合
     */
    default List<?> selectImagesUrls(String id) {
        throw new ImagesApiException("该服务不支持此接口");
    }

    /**
     * 获取列表，根据业务不同，获取方式也不同
     *
     * @return 获取到的列表数据
     */
    default List<DTO> selectList() {
        throw new ImagesApiException("该服务不支持此接口");
    }

    /**
     * 获取详细信息，包括图片、用户、相册等
     *
     * @param param 需要获取详细信息的参数，例如相册名等
     * @return 查询到的详细信息
     */
    default DTO selectDetailInfo(String param) {
        throw new ImagesApiException("该服务不支持此接口");
    }

    /**
     * 根据数据库主键 id 获取基础信息
     *
     * @param id 数据库主键 ID
     * @return 查询到的基础信息
     */
    default DTO getById(Number id) {
        throw new ImagesApiException("该服务不支持此接口");
    }
}
