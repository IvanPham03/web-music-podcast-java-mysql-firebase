package com.ivanpham.musicapi.controller;

import com.ivanpham.musicapi.model.Album;
import com.ivanpham.musicapi.model.Playlist;
import com.ivanpham.musicapi.repository.PlaylistRepository;
import com.ivanpham.musicapi.repository.UserRepository2;
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

    @Autowired
    private UserRepository2 userRepository2; // Để sử dụng hàm check có phải Admin không trong UserRepository2

    // Trả về danh sách các Playlist có policy là public
    @GetMapping("/public")
    public List<Playlist> getPublicPlaylists() {
        return playlistService.getPublicPlaylists();
    }

    // THÊM
    @PostMapping("/create/{userId}")
    public ResponseEntity<Playlist> createNewPlaylist(@RequestBody Playlist playlist, @PathVariable String userId){
        // tạo một Playlist mới đính kèm theo userId của người tạo ra Playlist đó
        try {
            Playlist savePlaylist = playlistService.createPlaylist(playlist,userId); // xem hàm này trong AlbumServiceImpl
            return ResponseEntity.status(HttpStatus.CREATED).body(savePlaylist);
        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // UPDATE
    @PutMapping("/update/{playlistId}/by/{userId}")
    public ResponseEntity<Playlist> updatePlaylist(@PathVariable String playlistId, @PathVariable String userId, @RequestBody Playlist playlist) {
        Optional<Playlist> optionalPlaylist = playlistRepository.findById(playlistId);
        if(optionalPlaylist.isPresent()) {
            // Kiểm tra xem userId này có phải là admin không?
            // - True : thì được update
            if (userRepository2.existsByIdAndRoleAdmin(userId)) {
                playlist.setId(playlistId);
                playlist.setUser(optionalPlaylist.get().getUser());
                Playlist savePlaylist = playlistRepository.save(playlist);
                return ResponseEntity.ok(savePlaylist);
            }
            // Kiểm tra xem userId này có phải là người sở hữu không?
            // - True : thì được update
            if(playlistService.isOwner(playlistId, userId)) {
                playlist.setId(playlistId);
                playlist.setUser(optionalPlaylist.get().getUser());
                Playlist savePlaylist = playlistRepository.save(playlist);
                return ResponseEntity.ok(savePlaylist);
            }
        }
        return ResponseEntity.notFound().build();
    }

    // XÓA
    @DeleteMapping("/{playlistId}/deleteBy/{userId}")
    public ResponseEntity<Void> deleteAlbumById(@PathVariable String playlistId, @PathVariable String userId) {
        // Kiểm tra Album id này có tồn tại
        if(playlistRepository.existsById(playlistId)) {
            // User là Admin thì xóa
            if (userRepository2.existsByIdAndRoleAdmin(userId)) // Xem hàm này trong UserRepository2
                playlistService.deleteById(playlistId);
            // User là chủ sở hữu thì xóa
            if (playlistService.isOwner(playlistId, userId)) // Xem hàm trong PlaylistRepository
                playlistService.deleteById(playlistId);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.noContent().build();
    }

    //Tìm theo ID
    @GetMapping("/{playlistId}")
    public ResponseEntity<Playlist> getPlaylistById(@PathVariable String playlistId) {
        try {
            // Sử dụng findById để lấy đối tượng theo ID
            Optional<Playlist> optionalPlaylist = playlistService.findAlbumById(playlistId);
            // Kiểm tra xem đối tượng có tồn tại hay không
            // Nếu tồn tại, trả về đối tượng Album
            // Nếu không tồn tại, trả về HTTP 404 Not Found
            return optionalPlaylist.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
        } catch (Exception e) {
            // Xử lý ngoại lệ và trả về HTTP 500 Internal Server Error
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    //SEARCH
    @GetMapping("/search")
    public List<Playlist> searchPlaylistByName(@RequestParam String keyword) {
        //Nếu Keyword rỗng thì trả các playlist public không thì tìm Playlist có tên trừng với keyword
        if (keyword != null)
            return playlistService.searchByPlaylistName(keyword);
        else{
            return playlistService.getPublicPlaylists();
        }
    }
}
