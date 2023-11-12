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
@Table(name = "album")
public class Album {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "albumName")
    private String albumName;
    @Column(name = "albumGenre")
    private String albumGenre;

    // 1 nhiều tới bảng album_track
    @OneToMany(mappedBy = "albumFk")
    private List<AlbumTrack> albumTracks = new ArrayList<>();
}
