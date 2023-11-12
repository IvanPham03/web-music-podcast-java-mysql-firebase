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
@Table(name = "artist_track")
public class ArtistTrack {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    // ánh xạ tới artist
    @ManyToOne
    @JoinColumn(name = "artistFk")
    private Artist artistFk;

    // ánh xạ tới track
    @ManyToOne
    @JoinColumn(name = "artistTrackFk")
    private Track artistTrackFk;
}
