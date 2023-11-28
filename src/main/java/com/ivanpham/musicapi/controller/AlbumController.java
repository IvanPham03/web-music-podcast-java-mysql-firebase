package com.ivanpham.musicapi.controller;

import com.ivanpham.musicapi.model.Album;
import com.ivanpham.musicapi.model.Track;
import com.ivanpham.musicapi.repository.AlbumRepository;
import com.ivanpham.musicapi.repository.UserRepository2;
import com.ivanpham.musicapi.service.AlbumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/Album")
public class AlbumController {
    @Autowired
    private AlbumService albumService;

    @Autowired
    private AlbumRepository albumRepository;

    @Autowired
    private UserRepository2 userRepository2;
    @GetMapping("/public")
    public ResponseEntity<List<Album>> getAllPublicAlbums() {
        // Hàm trả về danh sách các Album có policy = public
        return ResponseEntity.ok(albumService.findAllPublicAlbums());
    }

    // Hàm test - đừng quan tâm
    @GetMapping("/{userId}/isAdmin")
    // kiểm tra userId này có role là ADMIN hay không (true - false)
    public boolean isUserAdmin(@PathVariable String userId) {
        return userRepository2.existsByIdAndRoleAdmin(userId);
    }

    // CHỈNH SỬA
    @PutMapping("/update/{albumId}/by/{userId}")
    public ResponseEntity<Album> editAlbum(@PathVariable String albumId, @PathVariable String userId, @RequestBody Album album) {
        Optional<Album> optionalAlbum = albumRepository.findById(albumId);
        if(optionalAlbum.isPresent()) {
            // Kiểm tra xem userId này có phải là admin không?
            // - True : thì được update
            if (userRepository2.existsByIdAndRoleAdmin(userId)) {
                album.setId(albumId);
                album.setUser(optionalAlbum.get().getUser());
                Album saveAlbum = albumRepository.save(album);
                return ResponseEntity.ok(saveAlbum);
            }
            // Kiểm tra xem userId này có phải là người sở hữu không?
            // - True : thì được update
            if(albumService.isOwner(albumId, userId)) {
                album.setId(albumId);
                album.setUser(optionalAlbum.get().getUser());
                Album saveAlbum = albumRepository.save(album);
                return ResponseEntity.ok(saveAlbum);
            }
        }
        return ResponseEntity.notFound().build();
    }

    // THÊM
    @PostMapping("/create/{userId}")
    public ResponseEntity<Album> createNewAlbum(@RequestBody Album album, @PathVariable String userId){
        // tạo một Album mới đính kèm theo userId của người tạo ra Album đó
        try {
            Album saveAlbum = albumService.createAlbum(album,userId); // xem hàm này trong AlbumServiceImpl
            return ResponseEntity.status(HttpStatus.CREATED).body(saveAlbum);
        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // Hàm test - đừng quan tâm
    @GetMapping("/{albumId}/isOwner/{userId}")
    public boolean isAlbumOwner(@PathVariable String albumId, @PathVariable String userId) {
        // Kiểm tra Album có id này có phải do User id này tạo ra hay không
        return albumService.isOwner(albumId, userId);
    }

    // XÓA
    @DeleteMapping("/{albumId}/deleteBy/{userId}")
    public ResponseEntity<Void> deleteAlbumById(@PathVariable String albumId, @PathVariable String userId) {
        // Kiểm tra Album id này có tồn tại
        if(albumRepository.existsById(albumId)) {
            // User là Admin thì xóa
            if (userRepository2.existsByIdAndRoleAdmin(userId)) // Xem hàm này trong UserRepository2
                albumService.deleteById(albumId);
            // User là chủ sở hữu thì xóa
            if (albumService.isOwner(albumId, userId)) // Xem hàm trong AlbumRepository
                albumService.deleteById(albumId);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.noContent().build();
    }

    //Tìm theo ID
    @GetMapping("/{albumId}")
    public ResponseEntity<Album> getAlbumById(@PathVariable String albumId) {
        try {
            // Sử dụng findById để lấy đối tượng theo ID
            Optional<Album> optionalAlbum = albumService.findAlbumById(albumId);
            // Kiểm tra xem đối tượng có tồn tại hay không
            // Nếu tồn tại, trả về đối tượng Album
            // Nếu không tồn tại, trả về HTTP 404 Not Found
            return optionalAlbum.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
        } catch (Exception e) {
            // Xử lý ngoại lệ và trả về HTTP 500 Internal Server Error
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    //SEARCH
    @GetMapping("/search")
    public List<Album> searchAlbumsByAlbumName(@RequestParam String keyword) {
        if (keyword != null)
            return albumService.searchByAlbumName(keyword);
        else{
            return albumService.findAllPublicAlbums();
        }
    }
}
