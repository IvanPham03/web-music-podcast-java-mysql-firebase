package com.ivanpham.musicapi.service;

import com.ivanpham.musicapi.model.Album;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import com.ivanpham.musicapi.model.Playlist;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public interface PlaylistService {
    public List<Playlist> getPublicPlaylists();

    public Playlist createPlaylist(Playlist playlist, String userId);

    public boolean isOwner(String playlistId, String userId);

    public void deleteById(String playlistId);

    public Optional<Playlist> findAlbumById(String playlistId);

    public List<Playlist> searchByPlaylistName(String keyword);
}
