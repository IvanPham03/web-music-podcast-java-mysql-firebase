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
    private final ArtistTrackSeeder artistTrackSeeder;
    private final AlbumTrackSeeder albumTrackSeeder;
    private final PlaylistTrackSeeder playlistTrackSeeder;
    private final UserPlaylistSeeder userPlaylistSeeder;
    public DataSeeder( TrackSeeder trackSeeder, UserSeeder userSeeder, UserTrackSeeder userTrackSeeder, AlbumSeeder albumSeeder, PlaylistSeeder playlistSeeder, ArtistTrackSeeder artistTrackSeeder, AlbumTrackSeeder albumTrackSeeder, PlaylistTrackSeeder playlistTrackSeeder, UserPlaylistSeeder userPlaylistSeeder) {
        this.userSeeder = userSeeder;
        this.trackSeeder = trackSeeder;
        this.userTrackSeeder = userTrackSeeder;
        this.albumSeeder = albumSeeder;
        this.playlistSeeder = playlistSeeder;
        this.artistTrackSeeder = artistTrackSeeder;
        this.albumTrackSeeder = albumTrackSeeder;
        this.playlistTrackSeeder = playlistTrackSeeder;
        this.userPlaylistSeeder = userPlaylistSeeder;
    }

    @Override
    public void run(String... args) {
        userSeeder.seedUsers();
        trackSeeder.seedTracks();
        userTrackSeeder.seedUserTracks();
        albumSeeder.seedAlbums();
        playlistSeeder.seedPlaylist();
        artistTrackSeeder.seedArtistTracks();
        albumTrackSeeder.seedAlbumTracks();
        playlistTrackSeeder.seedPlaylistTracks();
        userPlaylistSeeder.seedUserPlaylists();
    }
}
