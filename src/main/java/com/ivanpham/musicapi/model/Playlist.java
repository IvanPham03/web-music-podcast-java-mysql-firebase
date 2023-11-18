package com.ivanpham.musicapi.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "playlist")
public class Playlist {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "playlistOwnerId")
    private String playlistOwnerId;
    @Column(name = "playlistName")
    private String playlistName;
    @Column(name = "playlistDescription")
    private String playlistDescription;
    @Column(name = "imgPlaylist")
    private String imgPlaylist;
    @Column(name = "timestamp")
    private String timestamp;


    // 1 n tới bảng user_playlist
    @OneToMany(mappedBy = "playlistFk", cascade = CascadeType.ALL)
    private List<UserPlaylist> userPlaylists = new ArrayList<>();

    // 1 n tới bảng playlist_track
    @OneToMany(mappedBy = "playlist2Fk", cascade = CascadeType.ALL)
    private List<PlaylistTrack> playlistTracks = new ArrayList<>();

}
