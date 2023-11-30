package com.ivanpham.musicapi.model.seedData;

import com.ivanpham.musicapi.model.Track;
import com.ivanpham.musicapi.model.User;
import com.ivanpham.musicapi.repository.TrackRepository;
import com.ivanpham.musicapi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class TrackSeeder {

    private final TrackRepository trackRepository;
    private final UserRepository userRepository2;

    @Autowired
    public TrackSeeder(TrackRepository trackRepository, UserRepository userRepository2) {
        this.trackRepository = trackRepository;
        this.userRepository2 = userRepository2;
    }

    public void seedTracks() {
        if (trackRepository.count() == 0) {
            List<Track> tracks = createTracks();
            // Lưu danh sách tracks vào cơ sở dữ liệu
            trackRepository.saveAll(tracks);
        }
    }

    private List<Track> createTracks() {
        List<Track> tracks = new ArrayList<>();
        User user = userRepository2.findByEmail("admin1@gmail.com");

        if(user != null){
            // Tạo các đối tượng Track và thêm vào danh sách tracks
            Track track1 = new Track("Anh đã yên bình, tôi biết thương mình", "AnhDaYenBinhToiBietThuongMinh-PhamQuynhAnh-9010380.mp3","ballad", user);
            Track track2 = new Track("Anh sẽ đưa em về", "AnhSeDuaEmVe-NQP-6309479.mp3", "ballad", user);

            tracks.add(track1);
            tracks.add(track2);
        }


        return tracks;
    }
}
