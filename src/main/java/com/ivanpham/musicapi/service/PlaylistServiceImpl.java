package com.ivanpham.musicapi.service;

import com.ivanpham.musicapi.model.Album;
import com.ivanpham.musicapi.model.Playlist;
import com.ivanpham.musicapi.model.User;
import com.ivanpham.musicapi.repository.AlbumRepository;
import com.ivanpham.musicapi.repository.PlaylistRepository;
import com.ivanpham.musicapi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;

@Component
public class PlaylistServiceImpl implements PlaylistService{
    @Autowired
    private PlaylistRepository playlistRepository;

    @Autowired
    UserRepository userRepository2; // Để sử dụng hàm check có phải Admin không trong UserRepository2
    @Override
    public List<Playlist> getPublicPlaylists() {
        return playlistRepository.findPublicPlaylists();
    }

    @Override
    public boolean isOwner(String playlistId, String userId) {
        return playlistRepository.isOwner(playlistId, userId);
    }
    @Override
    public Playlist createPlaylist(Playlist playlist, String userId) {
        User user = userRepository2.findById(userId).orElse(null);
        playlist.setUser(user);
        return playlistRepository.save(playlist);
    }

    @Override
    public void deleteById(String playlistId) {
        playlistRepository.deleteById(playlistId);
    }

    @Override
    public Optional<Playlist> findPlaylistById(String playlistId) {
        return playlistRepository.findById(playlistId);
    }

    @Override
    public Optional<Playlist> findPlaylistByIdAdmin(String playlistId) {
        return playlistRepository.findByIdAdmin(playlistId);
    }

    @Override
    public List<Playlist> searchByPlaylistName(String keyword) {
        return playlistRepository.searchByPlaylistName(keyword);
    }

    @Override
    public List<Playlist> searchByPlaylistNameAdmin(String keyword) {
        return playlistRepository.searchByPlaylistNameAdmin(keyword);
    }

    @Override
    public List<Playlist> returnOwnerPlaylist(String userId) {
        return playlistRepository.returnOwnerPlaylist(userId);
    }
}
