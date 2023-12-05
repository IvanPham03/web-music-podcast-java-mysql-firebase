package com.ivanpham.musicapi.model.seedData;

import com.ivanpham.musicapi.model.Playlist;
import com.ivanpham.musicapi.model.User;
import com.ivanpham.musicapi.repository.PlaylistRepository;
import com.ivanpham.musicapi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
@Component
public class PlaylistSeeder {
    private final PlaylistRepository playlistRepository;
    private final UserRepository userRepository2;

    @Autowired
    public PlaylistSeeder(PlaylistRepository playlistRepository, UserRepository userRepository2) {
        this.playlistRepository = playlistRepository;
        this.userRepository2 = userRepository2;
    }

    public void seedPlaylist() {
        if (playlistRepository.count() == 0) {
            List<Playlist> playlists = createPlaylist();

            // Lưu danh sách tracks vào cơ sở dữ liệu
            playlistRepository.saveAll(playlists);
        }
    }

    private List<Playlist> createPlaylist() {
        List<Playlist> playlists = new ArrayList<>();

        User user1 = userRepository2.findByEmail("admin1@gmail.com");
        User user2 = userRepository2.findByUsername("user2");
        User user3 = userRepository2.findByUsername("user3");
        User user4 = userRepository2.findByUsername("user4");


        if(user1 != null && user2 != null && user3 != null && user4 != null){
            // Tạo các đối tượng Playlist
            Playlist playlist1 = new Playlist("playlist1", user1 , "public");
            Playlist playlist2 = new Playlist("playlist2", user1 , "public");
            Playlist playlist3 = new Playlist("playlist3", user1, "privacy");

            Playlist playlist4 = new Playlist("playlist4", user2 , "private");
            Playlist playlist5 = new Playlist("playlist5", user3 , "private");
            Playlist playlist6 = new Playlist("playlist6", user4, "privacy");

            playlists.add(playlist1);
            playlists.add(playlist2);
            playlists.add(playlist3);

            playlists.add(playlist4);
            playlists.add(playlist5);
            playlists.add(playlist6);

        }

        return playlists;
    }
}
