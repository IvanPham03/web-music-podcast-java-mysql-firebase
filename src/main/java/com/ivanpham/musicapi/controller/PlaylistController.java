package com.ivanpham.musicapi.controller;

import com.ivanpham.musicapi.model.Album;
import com.ivanpham.musicapi.model.Playlist;
import com.ivanpham.musicapi.repository.PlaylistRepository;
import com.ivanpham.musicapi.repository.UserPlaylistRepository;
import com.ivanpham.musicapi.repository.UserRepository;
import com.ivanpham.musicapi.service.PlaylistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/Playlist")
public class PlaylistController {
    @Autowired
    private PlaylistService playlistService;

    @Autowired
    private PlaylistRepository playlistRepository;

    @Autowired
    private UserRepository userRepository2; // Để sử dụng hàm check có phải Admin không trong UserRepository2

    @Autowired
    private UserPlaylistRepository userPlaylistRepository;
    // Trả về danh sách các Playlist có policy là public
    @GetMapping("/getAll/{userId}")
    public List<Playlist> getPublicPlaylists(@PathVariable String userId) {
        if(userRepository2.existsByIdAndRoleAdmin(userId)) {
            //Admin thì trả hết
            return playlistRepository.findAll();
        }
        // không phải Admin thì trả về Playlist public
        return playlistService.getPublicPlaylists();
    }

    // Trả về PlayList của người dùng (Tạo hoặc Follow)
    @GetMapping("/getAllPlaylistByUserId/{userId}")
    public List<Playlist> getAllUserPlaylists(@PathVariable String userId){

        List<Playlist> list1 = userPlaylistRepository.findPlaylistsByUserId(userId);
        Set<Playlist> uniquePlaylists = new HashSet<>(list1);

        List<Playlist> list2 = playlistService.returnOwnerPlaylist(userId);
        uniquePlaylists.addAll(list2);

        return new ArrayList<>(uniquePlaylists);
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
    @GetMapping("/{userId}/getPlaylistById/{playlistId}")
    public ResponseEntity<Playlist> getPlaylistById(@PathVariable String userId,@PathVariable String playlistId) {
        try {
            if(userRepository2.existsByIdAndRoleAdmin(userId)) {
                // Admin thì trả về cả Playlist private
                // Kiểm tra xem đối tượng có tồn tại hay không
                // Nếu tồn tại, trả về đối tượng Playlist
                Optional<Playlist> optionalPlaylist = playlistService.findPlaylistByIdAdmin(playlistId);
                return optionalPlaylist.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
            }
            else{
                // Không phải Admin thì trả về Playlist public
                // Kiểm tra xem đối tượng có tồn tại hay không
                // Nếu tồn tại, trả về đối tượng Playlist (Nếu Id là Playlist private thì sẽ không tìm thấy)
                Optional<Playlist> optionalPlaylist = playlistService.findPlaylistById(playlistId);
                return optionalPlaylist.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
            }
            // Nếu không tồn tại, trả về HTTP 404 Not Found
        } catch (Exception e) {
            // Xử lý ngoại lệ và trả về HTTP 500 Internal Server Error
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    //SEARCH
    @GetMapping("/{userId}/search")
    public List<Playlist> searchPlaylistByName(@PathVariable String userId,@RequestParam String keyword) {
        //Nếu Keyword rỗng thì trả các playlist public đối với không phải admin
        // Admin thì tìm Playlist có tên trừng với keyword kể cả có là private
        if (keyword != null) {
            if(userRepository2.existsByIdAndRoleAdmin(userId)) {
                return playlistService.searchByPlaylistNameAdmin(keyword);
            }
            else{
                return playlistService.searchByPlaylistName(keyword);
            }
        }
        else{
            if(userRepository2.existsByIdAndRoleAdmin(userId)){
                return playlistRepository.findAll();
            }
            return playlistService.getPublicPlaylists();
        }
    }
}
