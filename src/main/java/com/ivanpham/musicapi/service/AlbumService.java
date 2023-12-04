package com.ivanpham.musicapi.service;

import com.ivanpham.musicapi.model.Album;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface AlbumService {
    //    public List<Album> getAllAlbums();
//
    Album createAlbum(Album album,String userId);
    //
//    public Album getAlbumById(int id);
//
    public void deleteById(String albumId);
    public List<Album> findAllPublicAlbums();
    //    public void editAlbum(String albumId, String userId, String albumName, String albumGenre, String albumPolicy);
//    public void editAlbumByAdmin(String albumId, String albumName, String albumGenre, String albumPolicy);
    public boolean isOwner(String albumId, String userId);
    public Optional<Album> findAlbumById(String albumId);

    public List<Album> searchByAlbumName(String keyword);

    public List<Album> searchByAlbumNameAdmin(String keyword);

    public Optional<Album> findAlbumByIdAdmin(String albumId);
}
