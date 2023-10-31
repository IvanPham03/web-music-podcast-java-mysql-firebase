package com.ivanpham.musicapi.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "album_song")
public class AlbumSong {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    // ánh xạ tới bảng song
    @ManyToOne
    @JoinColumn(name = "albumSongFk")
    private Song albumSongFk;

    // ánh xạ tới bảng album
    @ManyToOne
    @JoinColumn(name = "albumFk")
    private Song albumFk;
}
