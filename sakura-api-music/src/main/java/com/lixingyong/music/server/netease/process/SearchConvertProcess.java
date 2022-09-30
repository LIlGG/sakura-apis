package com.lixingyong.music.server.netease.process;

import com.lixingyong.music.server.netease.resource.model.entity.search.AlbumSearchList;
import com.lixingyong.music.server.netease.resource.model.entity.search.ArtistSearchList;
import com.lixingyong.music.server.netease.resource.model.entity.search.PlaylistSearchList;
import com.lixingyong.music.server.netease.resource.model.entity.search.SearchList;
import com.lixingyong.music.server.netease.resource.model.entity.search.SongSearchList;
import com.lixingyong.music.model.entity.AudioEntity;
import com.lixingyong.music.model.entity.PlaylistEntity;
import com.lixingyong.music.model.entity.SearchEntity;
import com.lixingyong.music.utils.MusicApiException;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.util.Assert;

/**
 * 转换 SearchList -> SearchEntity
 *
 * @see SearchList
 * @see AlbumSearchList
 * @see ArtistSearchList
 * @see PlaylistSearchList
 * @see SongSearchList
 * @see SearchEntity
 *
 * @author LIlGG
 * @since 2022-09-29
 */
public class SearchConvertProcess {

    public static SearchEntity<?> convertSearch(SearchList searchList) {
        Assert.notNull(searchList, "搜索失败，数据为空");
        if (searchList instanceof AlbumSearchList) {
            return convertAlbumSearch((AlbumSearchList)searchList);
        }
        if (searchList instanceof ArtistSearchList) {
            return convertArtistSearch((ArtistSearchList)searchList);
        }
        if (searchList instanceof PlaylistSearchList) {
            return convertPlaylistSearch((PlaylistSearchList)searchList);
        }
        if (searchList instanceof SongSearchList) {
            return convertSongSearch((SongSearchList)searchList);
        }
        throw new MusicApiException("暂不支持当前类型的搜索");
    }

    private static SearchEntity<?> convertSongSearch(SongSearchList searchList) {
        SearchEntity<AudioEntity> search = new SearchEntity<>();
        List<AudioEntity> audios = searchList.getSongs()
            .stream()
            .map(AudioEntity::getAudio)
            .collect(Collectors.toList());
        search.setSearchList(audios);
        search.setCount(searchList.getSongCount());
        return search;
    }

    private static SearchEntity<?> convertPlaylistSearch(PlaylistSearchList searchList) {
        SearchEntity<PlaylistEntity> search = new SearchEntity<>();
        List<PlaylistEntity> playlistEntities = searchList.getPlaylists()
            .stream()
            .map(PlaylistEntity::getPlaylist)
            .collect(Collectors.toList());
        search.setSearchList(playlistEntities);
        search.setCount(searchList.getPlaylistCount());
        return search;
    }

    private static SearchEntity<?> convertArtistSearch(ArtistSearchList searchList) {
        throw new MusicApiException("暂不支持当前类型的搜索");
    }

    private static SearchEntity<?> convertAlbumSearch(AlbumSearchList searchList) {
        throw new MusicApiException("暂不支持当前类型的搜索");
    }
}
