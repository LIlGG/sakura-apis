package com.lixingyong.netease.resource.model.params;

import com.alibaba.fastjson.annotation.JSONField;
import com.lixingyong.netease.resource.model.enums.SearchType;
import com.lixingyong.netease.resource.model.enums.serializer.SearchTypeSerializer;
import com.lixingyong.netease.resource.model.params.base.PageParam;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

/**
 * 搜索所需参数
 *
 * @author LIlGG
 * @since 2021/8/3
 */
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@SuperBuilder(toBuilder = true)
public class SearchParam extends PageParam {
    /**
     * 搜索关键词
     */
    @Builder.Default
    private String keywords = "hello";
    /**
     * 搜索类型，根据搜索类型不同，搜索到的内容格式也不同
     */
    @JSONField(serializeUsing = SearchTypeSerializer.class, deserializeUsing = SearchTypeSerializer.class)
    @Builder.Default
    private SearchType type = SearchType.SONG;
}
