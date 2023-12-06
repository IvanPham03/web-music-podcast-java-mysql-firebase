package com.ivanpham.musicapi.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import jakarta.validation.constraints.NotBlank;
import java.text.SimpleDateFormat;
import java.util.*;

@Getter
@Setter
@AllArgsConstructor
@Entity
@Table(name = "album")
//@JsonIdentityInfo(
//        generator = ObjectIdGenerators.PropertyGenerator.class,
//        property = "id")
public class Album {
    @Id
    @JsonView({View.BasicAlbum.class, View.BasicTrack.class})
    private String id = UUID.randomUUID().toString();
    @Column(name = "album_name")
    @JsonView({View.BasicAlbum.class, View.BasicTrack.class})
    @NotBlank(message = "Album Name không được null hoặc trống")
    private String albumName;
    @Column(name = "album_genre")
    @JsonView({View.BasicTrack.class, View.BasicAlbum.class})
    @NotBlank(message = "Album Genre không được null hoặc trống")
    private String albumGenre;
    @Column(name = "createAt")
    @JsonView({View.BasicAlbum.class, View.BasicTrack.class})
    private String createAt; // Sử dụng kiểu Timestamp
    @Column(name = "updateOn")
    @JsonView({View.BasicAlbum.class, View.BasicTrack.class})
    private String updateOn;
    @Column(name = "policy")
    @JsonView({View.BasicAlbum.class, View.BasicTrack.class})
    private String albumPolicy;
    @OneToMany(mappedBy = "album",cascade=CascadeType.ALL)
    @JsonView({View.BasicAlbum.class})
    private List<AlbumTrack> albumTracks = new ArrayList<>();
    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonView({View.BasicTrack.class, View.BasicAlbum.class})
    private User user;
    public Album()
    {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());
        this.createAt = timeStamp;
        this.updateOn = timeStamp;
        this.albumPolicy = "public";
    }

    //
    public Album(String albumName, String albumGenre, User user, String albumPolicy){
        this.albumGenre = albumGenre;
        this.albumName = albumName;
        this.user = user;
        this.albumPolicy = albumPolicy;
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());
        this.createAt = timeStamp;
        this.updateOn = timeStamp;
    }

}


