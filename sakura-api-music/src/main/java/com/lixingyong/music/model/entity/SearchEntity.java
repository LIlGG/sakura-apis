package com.lixingyong.music.model.entity;

import java.util.List;
import lombok.Data;

/**
 * 搜索相关实体
 *
 * @author LIlGG
 * @since 2022-09-28
 */
@Data
public class SearchEntity<T extends Entity> implements Entity {

    private List<T> searchList;

    private int count;
}
