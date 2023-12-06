package com.ivanpham.musicapi.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.ivanpham.musicapi.model.Album;
import com.ivanpham.musicapi.model.Track;
import com.ivanpham.musicapi.model.View;
import com.ivanpham.musicapi.repository.AlbumRepository;
import com.ivanpham.musicapi.repository.UserRepository;
import com.ivanpham.musicapi.service.AlbumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
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
    private UserRepository userRepository2;

    @GetMapping
    @JsonView(View.BasicAlbum.class)
    public ResponseEntity<List<Album>> getAllPublicAlbums(){
        return ResponseEntity.ok(albumService.findAllPublicAlbums());
    }
    @GetMapping("/getAll/{userId}")
    @JsonView(View.BasicAlbum.class)
    public ResponseEntity<List<Album>> getAllAlbums(@PathVariable String userId) {
        // Admin thì trả hết kể cả private
        if(userRepository2.existsByIdAndRoleAdmin(userId)) // hàm kiểm tra có phải admin không
            return ResponseEntity.ok(albumRepository.findAll());
        // trả về danh sách các Album có policy = public nếu không phải Admin
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
    @JsonView(View.BasicAlbum.class)
    public ResponseEntity<Album> editAlbum(@PathVariable String albumId, @PathVariable String userId,@Valid @RequestBody Album album) {
        Optional<Album> optionalAlbum = albumRepository.findById(albumId);
        if(optionalAlbum.isPresent()) {
            // Kiểm tra xem userId này có phải là admin không?
            // - True : thì được update
//            System.out.println("Album có tồn tại");
            if (userRepository2.existsByIdAndRoleAdmin(userId)) {
//                System.out.println("là admin");
                album.setId(albumId);
                album.setCreateAt(optionalAlbum.get().getCreateAt());
                album.setUser(optionalAlbum.get().getUser());
                Album saveAlbum = albumRepository.save(album);
                return ResponseEntity.ok(saveAlbum);
            }
            // Kiểm tra xem userId này có phải là người sở hữu không?
            // - True : thì được update
            if(albumService.isOwner(albumId, userId)) {
//                System.out.println("không là admin");
                album.setId(albumId);
                album.setCreateAt(optionalAlbum.get().getCreateAt());
                album.setUser(optionalAlbum.get().getUser());
                Album saveAlbum = albumRepository.save(album);
                return ResponseEntity.ok(saveAlbum);
            }
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }

    // THÊM
    @PostMapping("/create/{userId}")
    @JsonView(View.BasicAlbum.class)
    public ResponseEntity<Album> createNewAlbum(@Valid @RequestBody Album album, @PathVariable String userId){
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
    public ResponseEntity<String> deleteAlbumById(@PathVariable String albumId, @PathVariable String userId) {
        // Kiểm tra Album id này có tồn tại
        if(albumRepository.existsById(albumId)) {
            // User là Admin thì xóa
            if (userRepository2.existsByIdAndRoleAdmin(userId)) {// Xem hàm này trong UserRepository2
                albumService.deleteById(albumId);
                return ResponseEntity.ok("Xóa thành công!");
            }
            // User là chủ sở hữu thì xóa
            if (albumService.isOwner(albumId, userId)){ // Xem hàm trong AlbumRepository
                albumService.deleteById(albumId);
                return ResponseEntity.ok("Xóa thành công!");
            }
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        return ResponseEntity.notFound().build();
    }

    //Tìm theo ID
    @GetMapping("/{userId}/getAlbumById/{albumId}")
    @JsonView(View.BasicAlbum.class)
    public ResponseEntity<Album> getAlbumById(@PathVariable String userId,@PathVariable String albumId) {
        try {
            if(userRepository2.existsByIdAndRoleAdmin(userId)) {
                // Admin thì trả về cả Album private
                // Kiểm tra xem đối tượng có tồn tại hay không
                // Nếu tồn tại, trả về đối tượng Album
                Optional<Album> optionalAlbum = albumService.findAlbumByIdAdmin(albumId);
                return optionalAlbum.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
            }
            else{
                // Không phải Admin thì trả về Album public
                // Kiểm tra xem đối tượng có tồn tại hay không
                // Nếu tồn tại, trả về đối tượng Album (Nếu Id là Album private thì sẽ không tìm thấy)
                Optional<Album> optionalAlbum = albumService.findAlbumById(albumId);
                return optionalAlbum.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
            }
            // Nếu không tồn tại, trả về HTTP 404 Not Found
        } catch (Exception e) {
            // Xử lý ngoại lệ và trả về HTTP 500 Internal Server Error
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    //SEARCH
    @GetMapping("/{userId}/search")
    @JsonView(View.BasicAlbum.class)
    public List<Album> searchAlbumsByAlbumName(@PathVariable String userId,@RequestParam String keyword) {
        if (keyword != null){ // keyword không null thì tìm kiếm và trả kết quả dựa theo role
            if(userRepository2.existsByIdAndRoleAdmin(userId)){
                //admin thì trả về Album tìm theo keyword không quan tâm policy
                return albumService.searchByAlbumNameAdmin(keyword);
            }
            else{
                // Không phải admin thì chỉ trả về có policy = public
                return albumService.searchByAlbumName(keyword);
            }
        }
        else{ // keyword null thì trả ra toàn bộ
            if(userRepository2.existsByIdAndRoleAdmin(userId)){
                // Admin thì tất cả
                return albumRepository.findAll();
            }
            else{
                // không phải admin thì Album policy = public
                return albumService.findAllPublicAlbums();
            }
        }
    }
}
