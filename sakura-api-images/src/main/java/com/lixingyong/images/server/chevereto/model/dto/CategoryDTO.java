package com.lixingyong.images.server.chevereto.model.dto;

import com.lixingyong.images.server.chevereto.model.dto.base.BaseCategoryDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author LIlGG
 * @since 2020/11/25
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class CategoryDTO extends BaseCategoryDTO {
    /**
     * 分类描述
     */
    private String description;
}
