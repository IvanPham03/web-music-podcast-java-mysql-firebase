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
@Table(name = "playlist_track")
public class PlaylistTrack {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column (name = "isOwner")
    private boolean isOwner;

    // ánh xạ tới bảng Track
    @ManyToOne
    @JoinColumn (name = "playlistTrackFk")
    private Track playlistTrackFk;

    // ánh xạ tới bảng playlist
    @ManyToOne
    @JoinColumn (name = "playlist2Fk")
    private Playlist playlist2Fk;
}
