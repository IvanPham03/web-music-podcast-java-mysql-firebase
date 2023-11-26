package com.ivanpham.musicapi.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Data
@Builder
@Table(name= "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable=false)
    private String name;

    @Column(nullable=false, unique=true)
    private String email;

    @Column(nullable=false)
    private String password;

    @ManyToMany(fetch = FetchType.EAGER, cascade=CascadeType.ALL)
    @JoinTable(
            name="users_roles",
            joinColumns={@JoinColumn(name="USER_ID", referencedColumnName="ID")},
            inverseJoinColumns={@JoinColumn(name="ROLE_ID", referencedColumnName="ID")})
    private List<Role> roles = new ArrayList<>();

    // mối quan hệ một nhiều với bảng track
    @OneToMany (mappedBy = "trackFK")
    private List<Track> tracks = new ArrayList<>();

    // mối quan hệ một nhiều với bảng user_playlist
    @OneToMany(mappedBy = "userFK", cascade = CascadeType.ALL)
    private List<UserPlaylist> userPlaylists = new ArrayList<>();


    //

}
