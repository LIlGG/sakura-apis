package com.lixingyong.music.model.param;

import com.lixingyong.music.model.enums.SearchParamType;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import validator.EnumValid;

/**
 * @author LIlGG
 * @since  2022-05-19
 */
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class SearchListParam extends BasePageParam {
    /**
     * 搜索关键词
     */
    private String id = "hello";

    /**
     * 搜索类型，默认为音频搜索
     */
    @EnumValid(message = "searchType 类型不正确", target = SearchParamType.class)
    private String searchType = SearchParamType.AUDIO.getType();
}
