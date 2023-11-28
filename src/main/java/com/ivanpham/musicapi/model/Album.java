package com.ivanpham.musicapi.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.text.SimpleDateFormat;
import java.util.*;

@Getter
@Setter
@AllArgsConstructor
@Entity
@Table(name = "album")
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")
public class Album {
    @Id
    private String id = UUID.randomUUID().toString();
    @Column(name = "album_name")
    private String albumName;
    @Column(name = "album_genre")
    private String albumGenre;
    @Column(name = "createAt")
    private String createAt; // Sử dụng kiểu Timestamp
    @Column(name = "updateOn")
    private String updateOn;
    @Column(name = "policy")
    private String albumPolicy;
    @OneToMany(mappedBy = "album")
    private List<AlbumTrack> albumTracks = new ArrayList<>();
    @ManyToOne
    @JoinColumn(name = "user_id")
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


