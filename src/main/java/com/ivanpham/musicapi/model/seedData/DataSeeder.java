package com.ivanpham.musicapi.model.seedData;

import com.ivanpham.musicapi.model.Playlist;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataSeeder implements CommandLineRunner {

    private final TrackSeeder trackSeeder;
    private final UserSeeder userSeeder;
    private final UserTrackSeeder userTrackSeeder;
    private final AlbumSeeder albumSeeder;
    private final PlaylistSeeder playlistSeeder;
    public DataSeeder( TrackSeeder trackSeeder, UserSeeder userSeeder, UserTrackSeeder userTrackSeeder, AlbumSeeder albumSeeder, PlaylistSeeder playlistSeeder) {
        this.userSeeder = userSeeder;
        this.trackSeeder = trackSeeder;
        this.userTrackSeeder = userTrackSeeder;
        this.albumSeeder = albumSeeder;
        this.playlistSeeder = playlistSeeder;
    }

    @Override
    public void run(String... args) {
        userSeeder.seedUsers();
        trackSeeder.seedTracks();
        userTrackSeeder.seedUserTracks();
        albumSeeder.seedAlbums();
        playlistSeeder.seedPlaylist();
    }
}
