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
@Table(name = "user_track")
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")
public class UserTrack {
    @Id
    @JsonView({View.BasicTrack.class, View.BasicUser.class})
    private String id = UUID.randomUUID().toString();
    @JsonView(View.BasicTrack.class)
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JsonView(View.BasicUser.class)
    @JoinColumn(name = "track_id")
    private Track track;

    public UserTrack(User user, Track track){
        this.user = user;
        this.track = track;
    }
}
