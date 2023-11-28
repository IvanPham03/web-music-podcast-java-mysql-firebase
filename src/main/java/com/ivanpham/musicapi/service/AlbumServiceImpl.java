package com.ivanpham.musicapi.service;

import com.ivanpham.musicapi.model.Album;
import com.ivanpham.musicapi.model.User;
import com.ivanpham.musicapi.repository.AlbumRepository;
import com.ivanpham.musicapi.repository.UserRepository2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;

@Component
public class AlbumServiceImpl implements AlbumService{
    @Autowired
    private AlbumRepository albumRepository;

    @Autowired
    private UserRepository2 userRepository2;
//    @Override
//    public List<Album> getAllAlbums() {
//        return albumRepository.findAll();
//    }
//
    @Override
    public Album createAlbum(Album album, String userId) {
        album.setCreateAt(new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime()));
        album.setUpdateOn(new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime()));
        User user = userRepository2.findById(userId).orElse(null);
        album.setUser(user);
        return albumRepository.save(album);
    }
//
//    @Override
//    public Album getAlbumById(int id) {
//        return null;
//    }
//
    @Override
    public void deleteById(String albumId) {
        albumRepository.deleteById(albumId);
    }
    @Override
    public List<Album> findAllPublicAlbums() {
    return albumRepository.findAllPublicAlbums();
}

//    @Override
//    public void editAlbum(String albumId, String userId, String albumName, String albumGenre, String albumPolicy) {
//        albumRepository.editAlbum(albumId, userId, albumName, albumGenre, albumPolicy);
//    }
//
//    @Override
//    public void editAlbumByAdmin(String albumId, String albumName, String albumGenre, String albumPolicy) {
//        albumRepository.editAlbumByAdmin(albumId, albumName, albumGenre, albumPolicy);
//    }

    @Override
    public boolean isOwner(String albumId, String userId) {
        return albumRepository.isOwner(albumId, userId);
    }

    @Override
    public Optional<Album> findAlbumById(String albumId) {
        return albumRepository.findById(albumId);
    }

    @Override
    public List<Album> searchByAlbumName(String keyword) {
        return albumRepository.searchByAlbumName(keyword);
    }
}
