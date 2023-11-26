package com.ivanpham.musicapi.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.*;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;

@Getter
@Setter
@AllArgsConstructor
@Entity
@Table(name = "track")
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")
public class Track {
    @Id
    private String id = UUID.randomUUID().toString();
    @Column(name = "TrackName")
    private String trackName;
    @Column(name = "description")
    private String description;
    @Column(name = "url")
    @NonNull
    private String url;
    @Column(name = "createAt")
    private String createAt; // Sử dụng kiểu Timestamp
    @Column(name = "updateOn")
    private String updateOn;
    @Column(name = "genre")
    private String genre;
    @OneToMany(mappedBy = "track")
    private List<UserTrack> userTracks = new ArrayList<>();


    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "track")
    private List<AlbumTrack> albumTracks = new ArrayList<>();

    @OneToMany(mappedBy = "track")
    private List<PlaylistTrack> playlistTracks = new ArrayList<>();

    public Track(){
        this.description = null;
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());
        this.createAt = timeStamp;
        this.updateOn = timeStamp;
    }

    public Track(String trackName, String url, String genre,User user) {
        this.trackName = trackName;
        this.url = url;
        this.user = user;
        this.genre = genre;
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());
        this.createAt = timeStamp;
        this.updateOn = timeStamp;
    }
}
