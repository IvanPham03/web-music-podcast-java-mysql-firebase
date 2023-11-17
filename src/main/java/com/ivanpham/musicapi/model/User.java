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
    @GeneratedValue(strategy = GenerationType.IDENTITY) // id được đánh dấu là khóa chính và giá trị của nó sẽ được tạo ra tự động bằng cách sử dụng trường tự tăng của cơ sở dữ liệu.
    private String id;
    @Column(name = "username")
    private String username;
    @Column(name = "email")
    private String email;
    @Column(name = "phoneNumber")
    private String phoneNumber;
    @Column(name = "password")
    private String password;
    @Column(name = "imgUser")
    private String imgUser;
    @Column(name = "state")
    private State state;
    @Column(name = "timestamp")
    private String timestamp;
    @Column(name = "role")
    private String role;

    // mối quan hệ một nhiều với bảng track
    @OneToMany (mappedBy = "trackFK")
    private List<Track> tracks = new ArrayList<>();

    // mối quan hệ một nhiều với bảng user_playlist
    @OneToMany(mappedBy = "userFK", cascade = CascadeType.ALL)
    private List<UserPlaylist> userPlaylists = new ArrayList<>();


    //

}
