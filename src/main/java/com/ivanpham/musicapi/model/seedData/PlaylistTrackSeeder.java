package com.ivanpham.musicapi.model.seedData;

import com.ivanpham.musicapi.model.*;
import com.ivanpham.musicapi.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
@Component
public class PlaylistTrackSeeder {
    private final PlaylistRepository playlistRepository;
    private final PlaylistTrackRepository playlistTrackRepository;
    private final TrackRepository trackRepository;

    @Autowired
    public PlaylistTrackSeeder(PlaylistRepository playlistRepository, PlaylistTrackRepository playlistTrackRepository, TrackRepository trackRepository) {
        this.playlistRepository = playlistRepository;
        this.playlistTrackRepository = playlistTrackRepository;
        this.trackRepository = trackRepository;
    }

    public void seedPlaylistTracks() {
        if (playlistTrackRepository.count() == 0) {
            List<PlaylistTrack> playlistTracks = createPlaylistTracks();
            // Lưu danh sách playlistTrack vào cơ sở dữ liệu
            playlistTrackRepository.saveAll(playlistTracks);
        }
    }

    private List<PlaylistTrack> createPlaylistTracks() {
        List<PlaylistTrack> playlistTracks = new ArrayList<>();
        Playlist playlist1 = playlistRepository.findByplaylistName("playlist1");
        Playlist playlist2 = playlistRepository.findByplaylistName("playlist2");
        Track track1 = trackRepository.getByUrl("AnhDaYenBinhToiBietThuongMinh-PhamQuynhAnh-9010380.mp3");
        Track track2 = trackRepository.getByUrl("AnhSeDuaEmVe-NQP-6309479.mp3");
        // Tạo các đối tượng artistTracks

        if(playlist1 != null && playlist2 != null && track1 != null && track2 != null){
            PlaylistTrack playlistTrack1 = new PlaylistTrack(playlist1, track1);
            PlaylistTrack playlistTrack2 = new PlaylistTrack(playlist1, track2);
            PlaylistTrack playlistTrack3 = new PlaylistTrack(playlist2, track1);

            playlistTracks.add(playlistTrack1);
            playlistTracks.add(playlistTrack2);
            playlistTracks.add(playlistTrack3);
        }


        return playlistTracks;
    }
}
