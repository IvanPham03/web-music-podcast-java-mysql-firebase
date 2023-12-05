package com.ivanpham.musicapi.model;

import com.fasterxml.jackson.annotation.*;
import jakarta.persistence.*;
import lombok.*;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Getter
@Setter
@AllArgsConstructor
@Entity
@Data
@Builder
@Table(name= "user")
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")
public class User {
    @Id
    @JsonView({View.BasicUser.class, View.BasicPlaylist.class, View.BasicAlbum.class, View.BasicTrack.class})
    private String id = UUID.randomUUID().toString();
    @JsonView({View.BasicUser.class, View.BasicPlaylist.class, View.BasicAlbum.class, View.BasicTrack.class})
    @Column(name = "username")
    @NonNull
    private String username;
    @Column(name = "email")
    @JsonView({View.BasicTrack.class, View.BasicUser.class})
    @NonNull
    private String email;
    @Column(name = "phoneNumber")
    @JsonView({View.BasicUser.class, View.BasicTrack.class})
    private String phoneNumber;
    @Column(name = "password")
    @NonNull
    private String password;
    @Column(name = "imgUser")
    @JsonView({View.BasicUser.class, View.BasicTrack.class})
    private String imgUser;
    @Column(name = "state")
    @JsonView({View.BasicUser.class, View.BasicTrack.class})
    private State state;
    @Column(name = "createAt")
    @JsonView({View.BasicUser.class, View.BasicTrack.class})
    private String createAt; // Sử dụng kiểu Timestamp
    @Column(name = "updateOn")
    @JsonView({View.BasicUser.class, View.BasicTrack.class})
    private String updateOn;
    @ManyToMany(fetch = FetchType.EAGER, cascade=CascadeType.ALL)
    @JsonView({View.BasicUser.class, View.BasicTrack.class})
    @Column(name="role")
    @JoinTable(
            name="users_roles",
            joinColumns={@JoinColumn(name="USER_ID", referencedColumnName="ID")},
            inverseJoinColumns={@JoinColumn(name="ROLE_ID", referencedColumnName="ID")})
    private List<Role> roles = new ArrayList<>();


    public User() {
        this.imgUser = null;
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());
        this.createAt = timeStamp;
        this.updateOn = timeStamp;

    }


    // nhiều nhièu bằng bảng trung gian có nghĩa là nhiều nghệ sĩ 1 bài, 1 bài nhiều nghệ sĩ
    @OneToMany(mappedBy = "user")
    @JsonView(View.BasicUser.class)
    private List<UserTrack> userTracks = new ArrayList<>();

    // 1 nhiều user - track (có nghĩa là người upload)
    @OneToMany(mappedBy = "user")
    private List<Track> tracks = new ArrayList<>();

    //
    @OneToMany(mappedBy = "user")
    @JsonView(View.BasicUser.class)
    private List<UserPlaylist> userPlaylists = new ArrayList<>();
    // 1 nhiều user - album
    @OneToMany(mappedBy = "user")
    private List<Album> albums = new ArrayList<>();
    // 1 nhieu play list
    @OneToMany(mappedBy = "user")
    private List<Playlist> playlists = new ArrayList<>();
    public User(String email, String username, String password, List<Role> roles) {
        this.email = email;
        this.username = username;
        this.password = password;
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());
        this.createAt = timeStamp;
        this.updateOn = timeStamp;
        this.roles = roles;
    }
}
