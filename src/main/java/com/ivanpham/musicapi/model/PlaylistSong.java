package com.ivanpham.musicapi.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "playlist_song")
public class PlaylistSong {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column (name = "isOwner")
    private boolean isOwner;

    // ánh xạ tới bảng song
    @ManyToOne
    @JoinColumn (name = "playlistSongFk")
    private Song playlistSongFk;

    // ánh xạ tới bảng playlist
    @ManyToOne
    @JoinColumn (name = "playlist2Fk")
    private Playlist playlist2Fk;
}
