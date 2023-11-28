package com.ivanpham.musicapi.model.seedData;

import com.ivanpham.musicapi.model.Album;
import com.ivanpham.musicapi.model.Playlist;
import com.ivanpham.musicapi.model.User;
import com.ivanpham.musicapi.repository.AlbumRepository;
import com.ivanpham.musicapi.repository.PlaylistRepository;
import com.ivanpham.musicapi.repository.UserRepository2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
@Component
public class PlaylistSeeder {
    private final PlaylistRepository playlistRepository;
    private final UserRepository2 userRepository2;

    @Autowired
    public PlaylistSeeder(PlaylistRepository playlistRepository, UserRepository2 userRepository2) {
        this.playlistRepository = playlistRepository;
        this.userRepository2 = userRepository2;
    }

    public void seedPlaylist() {
        if (playlistRepository.count() == 0) {
            List<Playlist> playlists = createUsers();

            // Lưu danh sách tracks vào cơ sở dữ liệu
            playlistRepository.saveAll(playlists);
        }
    }

    private List<Playlist> createUsers() {
        List<Playlist> playlists = new ArrayList<>();

        User user = userRepository2.findByEmail("admin1@gmail.com");
        if(user != null){
            // Tạo các đối tượng Playlist
            Playlist playlist1 = new Playlist("playlist1", user , "public");
            Playlist playlist2 = new Playlist("playlist2", user , "public");
            Playlist playlist3 = new Playlist("playlist3", user, "privacy");
            playlists.add(playlist1);
            playlists.add(playlist2);
            playlists.add(playlist3);

        }

        return playlists;
    }
}
