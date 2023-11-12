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
@Table(name = "album_track")
public class AlbumTrack {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    // ánh xạ tới bảng track
    @ManyToOne
    @JoinColumn(name = "albumTrackFk")
    private Track albumTrackFk;

    // ánh xạ tới bảng album
    @ManyToOne
    @JoinColumn(name = "albumFk")
    private Album albumFk;
}
