package com.ivanpham.musicapi.service;

import com.ivanpham.musicapi.model.Album;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public interface AlbumService {
    public List<Album> getAllAlbums();

    void createAlbum(Album album);

    public Album getAlbumById(int id);

    public void deleteById(long id);
}
