package com.ivanpham.musicapi.model.seedData;

import com.ivanpham.musicapi.model.Album;
import com.ivanpham.musicapi.model.Track;
import com.ivanpham.musicapi.model.User;
import com.ivanpham.musicapi.repository.AlbumRepository;
import com.ivanpham.musicapi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
@Component
public class AlbumSeeder {
    private final AlbumRepository albumRepository;
    private final UserRepository userRepository2;

    @Autowired
    public AlbumSeeder(AlbumRepository albumRepository, UserRepository userRepository2) {
        this.albumRepository = albumRepository;
        this.userRepository2 = userRepository2;
    }

    public void seedAlbums() {
        if (albumRepository.count() == 0) {
            List<Album> albums = createUsers();

            // Lưu danh sách tracks vào cơ sở dữ liệu
            albumRepository.saveAll(albums);
        }
    }

    private List<Album> createUsers() {
        List<Album> albums = new ArrayList<>();

        User user = userRepository2.findByEmail("admin1@gmail.com");
        if(user != null){
            // Tạo các đối tượng album
            Album album1 = new Album("album1", "pop", user, "public");
            Album album2 = new Album("album2", "ballad", user, "public");
            Album album3 = new Album("album3", "hiphop", user, "public");
            Album album4 = new Album("album4", "jazz", user, "privacy");
            albums.add(album1);
            albums.add(album2);
            albums.add(album3);
            albums.add(album4);
        }

        return albums;
    }
}
