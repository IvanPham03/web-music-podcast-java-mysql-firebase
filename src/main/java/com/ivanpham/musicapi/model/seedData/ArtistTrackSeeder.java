package com.ivanpham.musicapi.model.seedData;

import com.ivanpham.musicapi.model.ArtistTrack;
import com.ivanpham.musicapi.model.Track;
import com.ivanpham.musicapi.model.User;
import com.ivanpham.musicapi.repository.ArtistTrackRepository;
import com.ivanpham.musicapi.repository.TrackRepository;
import com.ivanpham.musicapi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
@Component
public class ArtistTrackSeeder {
    private final UserRepository userRepository;
    private final ArtistTrackRepository artistTrackRepository;
    private final TrackRepository trackRepository;

    @Autowired
    public ArtistTrackSeeder(UserRepository userRepository, ArtistTrackRepository artistTrackRepository, TrackRepository trackRepository) {
        this.userRepository = userRepository;
        this.artistTrackRepository = artistTrackRepository;
        this.trackRepository = trackRepository;
    }

    public void seedArtistTracks() {
        if (artistTrackRepository.count() == 0) {
            List<ArtistTrack> artistTracks = createArtistTracks();
            // Lưu danh sách ArtistTrack vào cơ sở dữ liệu
            artistTrackRepository.saveAll(artistTracks);
        }
    }

    private List<ArtistTrack> createArtistTracks() {
        List<ArtistTrack> artistTracks = new ArrayList<>();
        User artist1 = userRepository.findByEmail("artist1@gmail.com");
        User artist2 = userRepository.findByEmail("artist2@gmail.com");
        User artist3 = userRepository.findByEmail("user1@gmail.com");
        User artist4 = userRepository.findByEmail("user2@gmail.com");
        Track track1 = trackRepository.getByUrl("AnhDaYenBinhToiBietThuongMinh-PhamQuynhAnh-9010380.mp3");
        Track track2 = trackRepository.getByUrl("AnhSeDuaEmVe-NQP-6309479.mp3");
        Track track3 = trackRepository.getByUrl("AnTinhSangTrang-ChauKhaiPhongLeCuong-7976352.mp3");
        Track track4 = trackRepository.getByUrl("ChiemBaoCuoi-Keyo-5992899.mp3");

        // Tạo các đối tượng artistTracks

//        if(artist1 != null && artist2 != null && track1 != null && track2 != null && track2 != null && track2 != null){
            ArtistTrack artistTrack1 = new ArtistTrack(artist1, track1);
            ArtistTrack artistTrack2 = new ArtistTrack(artist2, track2);
//            ArtistTrack artistTrack3 = new ArtistTrack(artist2, track2);
            ArtistTrack artistTrack4 = new ArtistTrack(artist3, track3);
            ArtistTrack artistTrack5 = new ArtistTrack(artist4, track4);
            artistTracks.add(artistTrack1);
            artistTracks.add(artistTrack2);
//            artistTracks.add(artistTrack3);
            artistTracks.add(artistTrack4);
            artistTracks.add(artistTrack5);
//        }


        return artistTracks;
    }
}
