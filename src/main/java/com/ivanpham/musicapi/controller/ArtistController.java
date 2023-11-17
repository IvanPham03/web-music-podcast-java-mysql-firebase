package com.ivanpham.musicapi.controller;

import com.ivanpham.musicapi.model.Artist;
import com.ivanpham.musicapi.service.ArtistServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/artist")
public class ArtistController {
    @Autowired
    ArtistServiceImpl artistServiceImpl;

//    private static final String

//    @GetMapping dành cho những phương thức sử dụng GET, lấy dữ liệu từ server
//    @PostMapping phương thức POST, gửi dữ liệu lên server
//    @PutMapping phương thức PUT, update theo kiểu ghi đè đối tượng
//    @DeleteMapping phương thức Delete, như tên gọi =))

    @GetMapping
    public List<Artist> getAllArtist(){
        return artistServiceImpl.getAllArtist();
    }

//    @GetMapping
    public  List<Artist> getArtistByName(String name){
        return artistServiceImpl.getArtistByName(name);
    }

//    @DeleteMapping
    public void deleteArtistById(long id) {
        artistServiceImpl.deleteArtistById(id);
    }

//    @PutMapping
    public void saveArtist(Artist artist){
        artistServiceImpl.saveArtist(artist);
    }


}
