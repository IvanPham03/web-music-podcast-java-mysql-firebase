package com.ivanpham.musicapi.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonView;
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
//@JsonIdentityInfo(
//        generator = ObjectIdGenerators.PropertyGenerator.class,
//        property = "id")
public class Track {
    @Id

    @JsonView({View.BasicTrack.class, View.BasicPlaylist.class, View.BasicAlbum.class, View.BasicUser.class})
    private String id = UUID.randomUUID().toString();
    @JsonView({View.BasicPlaylist.class, View.BasicAlbum.class, View.BasicUser.class,View.BasicTrack.class})
    @Column(name = "TrackName")
    private String trackName;
    @Column(name = "description")
    @JsonView({View.BasicPlaylist.class, View.BasicAlbum.class, View.BasicUser.class, View.BasicTrack.class})
    private String description;
    @Column(name = "url")
    @NonNull
    @JsonView({View.BasicPlaylist.class, View.BasicAlbum.class, View.BasicUser.class, View.BasicTrack.class})
    private String url;
    @JsonView({View.BasicTrack.class, View.BasicPlaylist.class, View.BasicAlbum.class, View.BasicUser.class, View.BasicTrack.class})
    @Column(name = "createAt")

    private String createAt; // Sử dụng kiểu Timestamp
    @JsonView({View.BasicTrack.class, View.BasicPlaylist.class, View.BasicAlbum.class, View.BasicUser.class, View.BasicTrack.class})
    @Column(name = "updateOn")

    private String updateOn;
    @Column(name = "genre")
    @JsonView({View.BasicPlaylist.class, View.BasicAlbum.class, View.BasicUser.class, View.BasicTrack.class})
    private String genre;
    @JsonView({View.BasicPlaylist.class, View.BasicAlbum.class, View.BasicUser.class, View.BasicTrack.class})
    @OneToMany(mappedBy = "track",cascade=CascadeType.ALL)
    private List<UserTrack> userTracks = new ArrayList<>();


    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonView({View.BasicTrack.class,View.BasicAlbum.class,View.BasicPlaylist.class})
    private User user;


    @OneToMany(mappedBy = "track",cascade=CascadeType.ALL)
    @JsonView({View.BasicTrack.class})
    private List<AlbumTrack> albumTracks = new ArrayList<>();

    @OneToMany(mappedBy = "track",cascade=CascadeType.ALL)
    @JsonView({View.BasicTrack.class})
    private List<PlaylistTrack> playlistTracks = new ArrayList<>();

    @OneToMany(mappedBy = "track", cascade = CascadeType.ALL)
    private List<ArtistTrack> artistTracks;

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
