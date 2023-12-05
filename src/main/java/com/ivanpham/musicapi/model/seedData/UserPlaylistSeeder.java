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

    // Hàm kiểm tra null và tạo đối tượng UserPlayList
    private static UserPlaylist createUserPlaylist(Playlist playlist, User user){
        if (playlist != null && user != null)
            return new UserPlaylist(playlist, user);
        return null;
    }

    private List<UserPlaylist> createUserPlaylists() {
        List<UserPlaylist> userPlaylists = new ArrayList<>();

        //Lấy playlist từ repo
        Playlist playlist1 = playlistRepository.findByplaylistName("playlist1");
        Playlist playlist2 = playlistRepository.findByplaylistName("playlist2");

        Playlist playlist3 = playlistRepository.findByplaylistName("playlist3");
        Playlist playlist4 = playlistRepository.findByplaylistName("playlist4");
        Playlist playlist5 = playlistRepository.findByplaylistName("playlist5");
        Playlist playlist6 = playlistRepository.findByplaylistName("playlist6");


        //Lấy user từ repo
        User user = userRepository2.findByEmail("admin1@gmail.com");
        User user1 = userRepository2.findByEmail("user1@gmail.com");

        User user2 = userRepository2.findByUsername("user2");
        User user3 = userRepository2.findByUsername("user3");
        User user4 = userRepository2.findByUsername("user4");

        // Tạo các đối tượng artistTracks

//        if(playlist1 != null && playlist2 != null && user != null && user1 != null ){
//            UserPlaylist userPlaylist1 = new UserPlaylist(playlist1, user);
////            UserPlaylist userPlaylist2 = new UserPlaylist(playlist1, user);
//            UserPlaylist userPlaylist3 = new UserPlaylist(playlist2, user1);
//
//            userPlaylists.add(userPlaylist1);
////            userPlaylists.add(userPlaylist2);
//            userPlaylists.add(userPlaylist3);
//        }

        try {
            // Phương thức createUserPlayList dưới đây sẽ kiểm tra null với các tham số playlist và user
            // sau đó trả về đối tượng UserPlaylist

            // Đối tượng sau đó được đẩy vào ArrayList userPlayLists
            // đặt vào try catch phòng trường hợp đối tượng UserPlayList được trả về vẫn null
            userPlaylists.add(createUserPlaylist(playlist1, user));
            userPlaylists.add(createUserPlaylist(playlist2, user));
            userPlaylists.add(createUserPlaylist(playlist3, user));

            userPlaylists.add(createUserPlaylist(playlist4, user2));
            userPlaylists.add(createUserPlaylist(playlist5, user3));
            userPlaylists.add(createUserPlaylist(playlist6, user4));

        }catch (NullPointerException e){
            System.out.println("Null pointer");
        }

        return userPlaylists;
    }
}
