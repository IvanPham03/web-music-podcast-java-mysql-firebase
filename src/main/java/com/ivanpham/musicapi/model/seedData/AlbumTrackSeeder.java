package com.ivanpham.musicapi.model.seedData;

import com.ivanpham.musicapi.model.*;
import com.ivanpham.musicapi.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
@Component
public class AlbumTrackSeeder {
    private final AlbumRepository albumRepository;
    private final AlbumTrackRepository albumTrackRepository;
    private final TrackRepository trackRepository;

    @Autowired
    public AlbumTrackSeeder(AlbumRepository albumRepository, AlbumTrackRepository albumTrackRepository, TrackRepository trackRepository) {
        this.albumRepository = albumRepository;
        this.albumTrackRepository = albumTrackRepository;
        this.trackRepository = trackRepository;
    }

    public void seedAlbumTracks() {
        if (albumTrackRepository.count() == 0) {
            List<AlbumTrack> albumTracks = createAlbumTracks();
            // Lưu danh sách ArtistTrack vào cơ sở dữ liệu
            albumTrackRepository.saveAll(albumTracks);
        }
    }

    private List<AlbumTrack> createAlbumTracks() {
        List<AlbumTrack> albumTracks = new ArrayList<>();
        Album album1 = albumRepository.findByAlbumName("album1");
        Album album2 = albumRepository.findByAlbumName("album2");
        Track track1 = trackRepository.getByUrl("AnhDaYenBinhToiBietThuongMinh-PhamQuynhAnh-9010380.mp3");
        Track track2 = trackRepository.getByUrl("AnhSeDuaEmVe-NQP-6309479.mp3");
        // Tạo các đối tượng artistTracks

        if(album1 != null && album2 != null && track1 != null && track2 != null){
            AlbumTrack albumTrack1 = new AlbumTrack(album1, track1);
            AlbumTrack albumTrack2 = new AlbumTrack(album1, track2);
            AlbumTrack albumTrack3 = new AlbumTrack(album2, track1);

            albumTracks.add(albumTrack1);
            albumTracks.add(albumTrack2);
            albumTracks.add(albumTrack3);
        }


        return albumTracks;
    }
}
