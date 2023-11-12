package com.ivanpham.musicapi.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "track")
public class Track {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "artistID")
    private String artistId;
    @Column(name = "TrackName")
    private String trackName;
    @Column(name = "description")
    private String description;
    @Column(name = "timestamp")
    private Timestamp timestamp; // Sử dụng kiểu Timestamp

    // ánh xạ tới bảng user
    @ManyToOne
    @JoinColumn(name = "TrackFK")
    private User trackFK;

    // 1 n tới bảng album_Track
    @OneToMany(mappedBy = "albumTrackFk")
    private List<AlbumTrack> albumTracks = new ArrayList<>();

    // 1 n tới bảng artist_Track
    @OneToMany(mappedBy = "artistTrackFk")
    private List<ArtistTrack> artistTracks = new ArrayList<>();

    // 1 n tới bảng playlist_Track
    @OneToMany(mappedBy = "playlistTrackFk")
    private List<PlaylistTrack> playlistTracks = new ArrayList<>();

}
