package com.ivanpham.musicapi.service;

import com.ivanpham.musicapi.model.Artist;
import com.ivanpham.musicapi.repository.ArtistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArtistServiceImpl implements ArtistService{
    @Autowired
    ArtistRepository artistRepository;

    /**
     * Get all Artists from database then return
     * @return List of Artists
     */
    @Override
    public List<Artist> getAllArtist() {
        return artistRepository.findAll();
    }

    /**
     * Get all Artists from database with the given name
     * @param name - must not be empty
     * @return List of Artists containing the given name
     */
    @Override
    public List<Artist> getArtistByName(String name) {
        return null;
    }

    /**
     * Delete an Artist with the given id
     * If the Artist cannot be found, action will be canceled
     * @param id - must not be empty
     */
    @Override
    public void deleteArtistById(long id) {
        artistRepository.deleteById(id);
    }

    /**
     *  Add a new Artist or change an exist Artist in database
     * @param artist - must not be empty
     */
    @Override
    public void saveArtist(Artist artist) {
        artistRepository.save(artist);
    }
}
