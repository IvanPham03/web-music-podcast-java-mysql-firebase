package com.ivanpham.musicapi.controller;

import com.ivanpham.musicapi.model.Playlist;
import com.ivanpham.musicapi.repository.PlaylistRepository;
import com.ivanpham.musicapi.service.PlaylistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/Playlist")
public class PlaylistController {
    @Autowired
    private PlaylistService playlistService;
    @Autowired
    private PlaylistRepository playlistRepository;

    @GetMapping("")
    public List<Playlist> getAllAlbums(){
        return playlistService.getAllPlaylists();
    }
    @GetMapping("/{id}")
    public Optional<Playlist> getAlbumById(@PathVariable Long id) {
        try {
            // Sử dụng findById để lấy đối tượng theo ID
            Optional<Playlist> optionalTrack = playlistRepository.findById(id);

            // Kiểm tra xem đối tượng có tồn tại hay không
            if (optionalTrack.isPresent()) {
                // Nếu tồn tại, trả về đối tượng Track
                return optionalTrack;
            } else {
                // Nếu không tồn tại, trả về HTTP 404 Not Found
                return null;
            }
        } catch (Exception e) {
            // Xử lý ngoại lệ và trả về HTTP 500 Internal Server Error
            return null;
        }
    }

    @PostMapping("/create")
    public Playlist createNewAlbum(@RequestBody Playlist playlist){
        playlistService.createPlaylist(playlist);
        return playlist;
    }

    @DeleteMapping("/delete/{id}")
    public void deleteAlbumById(@PathVariable Long id) {
        playlistService.deleteById(id);
    }

    @PutMapping("/edit/{id}")
    public ResponseEntity<Playlist> updateAlbum(@PathVariable Long id,@RequestBody Playlist playlistData){
        Optional<Playlist> optionalAlbum = playlistRepository.findById(id);
        if(optionalAlbum.isPresent()){
            Playlist upPlaylist = optionalAlbum.get();
            if(upPlaylist.getPlaylistOwnerId() != null)
                upPlaylist.setPlaylistOwnerId(playlistData.getPlaylistOwnerId());
            if(upPlaylist.getPlaylistName() != null)
                upPlaylist.setPlaylistName(playlistData.getPlaylistName());
            if(upPlaylist.getPlaylistDescription() != null)
                upPlaylist.setPlaylistDescription(playlistData.getPlaylistDescription());
            if(upPlaylist.getImgPlaylist() != null)
                upPlaylist.setImgPlaylist(playlistData.getImgPlaylist());
            if(upPlaylist.getTimestamp() != null)
                upPlaylist.setTimestamp(playlistData.getTimestamp());
            playlistRepository.save(upPlaylist);
            return ResponseEntity.ok(upPlaylist);
        }
        else
            return ResponseEntity.notFound().build();
    }
}
