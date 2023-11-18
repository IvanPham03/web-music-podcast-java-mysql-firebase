package com.ivanpham.musicapi.controller;

import com.ivanpham.musicapi.model.Album;
import com.ivanpham.musicapi.repository.AlbumRepository;
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

    @GetMapping("")
    public List<Album> getAllAlbums(){
        return albumService.getAllAlbums();
    }
    @GetMapping("/{id}")
    public Optional<Album> getAlbumById(@PathVariable Long id) {
        try {
            // Sử dụng findById để lấy đối tượng theo ID
            Optional<Album> optionalTrack = albumRepository.findById(id);

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
    public Album createNewAlbum(@RequestBody Album album){
        albumService.createAlbum(album);
        return album;
    }

    @DeleteMapping("/delete/{id}")
    public void deleteAlbumById(@PathVariable Long id) {
            albumService.deleteById(id);
    }

    @PutMapping("/edit/{id}")
    public ResponseEntity<Album> updateAlbum(@PathVariable Long id,@RequestBody Album albumData){
        Optional<Album> optionalAlbum = albumRepository.findById(id);
        if(optionalAlbum.isPresent()){
            Album upAlbum = optionalAlbum.get();
            if(upAlbum.getAlbumGenre() != null)
                upAlbum.setAlbumGenre(albumData.getAlbumGenre());
            if(upAlbum.getAlbumName() != null)
                upAlbum.setAlbumName(albumData.getAlbumName());
            albumRepository.save(upAlbum);
            return ResponseEntity.ok(upAlbum);
        }
        else
            return ResponseEntity.notFound().build();
    }
}
