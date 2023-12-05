package com.ivanpham.musicapi.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "album_track")
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")
public class AlbumTrack {
    @Id
    private String id = UUID.randomUUID().toString();

    @ManyToOne
    @JoinColumn(name = "album_id")
    @JsonView({View.BasicTrack.class})
    private Album album;

    @ManyToOne
    @JsonView(View.BasicAlbum.class)
    @JoinColumn(name = "track_id")
    private Track track;

    public AlbumTrack(Album album, Track track){
        this.album = album;
        this.track = track;
    }
}
