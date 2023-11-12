package service;

import com.ivanpham.musicapi.model.Artist;

import java.util.List;

public interface ArtistService {
    List<Artist> getAllArtist();
    Artist addArtist()
    Artist getAllArtistById(long id);
    void deleteArtistById(long id);
    void saveArtist(Artist artist);
}
