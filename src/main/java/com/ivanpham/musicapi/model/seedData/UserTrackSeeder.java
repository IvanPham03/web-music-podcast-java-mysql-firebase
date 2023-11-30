package com.ivanpham.musicapi.model.seedData;

import com.ivanpham.musicapi.model.Track;
import com.ivanpham.musicapi.model.User;
import com.ivanpham.musicapi.model.UserTrack;
import com.ivanpham.musicapi.repository.TrackRepository;
import com.ivanpham.musicapi.repository.UserRepository;
import com.ivanpham.musicapi.repository.UserTrackRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
@Component
public class UserTrackSeeder {
    private final UserTrackRepository userTrackRepository;
    private final UserRepository userRepository;
    private final TrackRepository trackRepository;
    @Autowired
    public UserTrackSeeder(UserTrackRepository userTrackRepository, UserRepository userRepository, TrackRepository trackRepository) {
        this.userTrackRepository = userTrackRepository;
        this.trackRepository = trackRepository;
        this.userRepository = userRepository;
    }

    public void seedUserTracks() {
        if (userTrackRepository.count() == 0) {
            List<UserTrack> userTrackSeeders = createUserTracks();

            // Lưu danh sách tracks vào cơ sở dữ liệu
            userTrackRepository.saveAll(userTrackSeeders);
        }
    }

    private List<UserTrack> createUserTracks() {
        List<UserTrack> userTracks = new ArrayList<>();

        // Lấy track
        Track track1 = trackRepository.getByUrl("AnhDaYenBinhToiBietThuongMinh-PhamQuynhAnh-9010380.mp3");
        Track track2 = trackRepository.getByUrl("AnhSeDuaEmVe-NQP-6309479.mp3");


        // user
        User artist1 = userRepository.findByEmail("artist1@gmail.com");
        User artist2 = userRepository.findByEmail("artist2@gmail.com");
        if(track1 != null && track2 != null && artist1 != null && artist2 != null){
           UserTrack userTrack1 = new UserTrack(artist2, track1);
           UserTrack userTrack2 = new UserTrack(artist1, track1);
           UserTrack userTrack3 = new UserTrack(artist1, track2);

           userTracks.add(userTrack1);
           userTracks.add(userTrack2);
           userTracks.add(userTrack3);
        }

        return userTracks;
    }
}
