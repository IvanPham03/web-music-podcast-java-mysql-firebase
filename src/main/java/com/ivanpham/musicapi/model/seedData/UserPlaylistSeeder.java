package com.ivanpham.musicapi.model.seedData;

import com.ivanpham.musicapi.model.*;
import com.ivanpham.musicapi.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
@Component
public class UserPlaylistSeeder {
    private final PlaylistRepository playlistRepository;
    private final UserPlaylistRepository userPlaylistRepository;
    private final UserRepository userRepository2;

    @Autowired
    public UserPlaylistSeeder(PlaylistRepository playlistRepository, UserPlaylistRepository userPlaylistRepository, UserRepository userRepository2) {
        this.playlistRepository = playlistRepository;
        this.userPlaylistRepository = userPlaylistRepository;
        this.userRepository2 = userRepository2;
    }

    public void seedUserPlaylists() {
        if (userPlaylistRepository.count() == 0) {
            List<UserPlaylist> userPlaylists = createUserPlaylists();
            // Lưu danh sách playlistTrack vào cơ sở dữ liệu
            userPlaylistRepository.saveAll(userPlaylists);
        }
    }

    private List<UserPlaylist> createUserPlaylists() {
        List<UserPlaylist> userPlaylists = new ArrayList<>();
        Playlist playlist1 = playlistRepository.findByplaylistName("playlist1");
        Playlist playlist2 = playlistRepository.findByplaylistName("playlist2");
        User user = userRepository2.findByEmail("admin1@gmail.com");
        User user1 = userRepository2.findByEmail("user1@gmail.com");
        // Tạo các đối tượng artistTracks

        if(playlist1 != null && playlist2 != null && user != null && user1 != null ){
            UserPlaylist userPlaylist1 = new UserPlaylist(playlist1, user);
//            UserPlaylist userPlaylist2 = new UserPlaylist(playlist1, user);
            UserPlaylist userPlaylist3 = new UserPlaylist(playlist2, user1);

            userPlaylists.add(userPlaylist1);
//            userPlaylists.add(userPlaylist2);
            userPlaylists.add(userPlaylist3);
        }


        return userPlaylists;
    }
}
