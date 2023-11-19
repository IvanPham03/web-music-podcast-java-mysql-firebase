package com.ivanpham.musicapi.service;

import com.ivanpham.musicapi.model.Playlist;
import com.ivanpham.musicapi.repository.AlbumRepository;
import com.ivanpham.musicapi.repository.PlaylistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
public class PlaylistServiceImpl implements PlaylistService{
    @Autowired
    private PlaylistRepository playlistRepository;
    @Override
    public List<Playlist> getAllPlaylists() {
        return playlistRepository.findAll();
    }

    @Override
    public void createPlaylist(Playlist playlist) {
        playlistRepository.save(playlist);
    }

    @Override
    public Playlist getPlaylistById(int id) {
        return null;
    }

    @Override
    public void deleteById(long id) {
        playlistRepository.deleteById(id);
    }
}
