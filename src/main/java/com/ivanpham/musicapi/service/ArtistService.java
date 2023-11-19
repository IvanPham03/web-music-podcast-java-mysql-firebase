package com.ivanpham.musicapi.service;

import com.ivanpham.musicapi.model.Artist;

import java.util.List;

public interface ArtistService {
    List<Artist> getAllArtist();
    List<Artist> getArtistByName(String name);
    void deleteArtistById(long id);
    void saveArtist(Artist artist);
}
