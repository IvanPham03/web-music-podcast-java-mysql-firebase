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
@Table(name = "user_playlist")
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")
public class UserPlaylist {
    @Id
    @JsonView(View.BasicUser.class)
    private String id = UUID.randomUUID().toString();

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JsonView(View.BasicUser.class)
    @JoinColumn(name = "playlist_id")
    private Playlist playlist;

    public UserPlaylist(Playlist playlist, User user) {
        this.playlist = playlist;
        this.user = user;
    }
}
