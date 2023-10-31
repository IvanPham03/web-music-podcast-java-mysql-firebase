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
@Table(name = "song")
public class Song {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "artistID")
    private String artistId;
    @Column(name = "songName")
    private String songName;
    @Column(name = "description")
    private String description;
    @Column(name = "timestamp")
    private String timestamp;

    // ánh xạ tới bảng user
    @ManyToOne
    @JoinColumn(name = "songFK")
    private User songFK;

    // 1 n tới bảng album_song
    @OneToMany(mappedBy = "albumSongFk")
    private List<AlbumSong> albumSongs = new ArrayList<>();

    // 1 n tới bảng artist
    @OneToMany(mappedBy = "artistSongFk")
    private List<ArtistSong> artistSongs = new ArrayList<>();

}
