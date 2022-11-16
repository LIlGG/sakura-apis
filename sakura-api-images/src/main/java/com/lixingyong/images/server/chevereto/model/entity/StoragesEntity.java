package com.lixingyong.images.server.chevereto.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
/**
 *
 * @author LIlGG
 * @since 2022-10-16
 */
@Entity
@Table(name = "chv_storages")
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class StoragesEntity {
    /**
     * 相册 ID
     */
    @Id
    @Column(name = "storage_id")
    private long id;

    @Column(name = "storage_url")
    private String url;
}
