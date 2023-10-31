package com.ivanpham.musicapi.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "user_playlist")
public class UserPlaylist {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // id được đánh dấu là khóa chính và giá trị của nó sẽ được tạo ra tự động bằng cách sử dụng trường tự tăng của cơ sở dữ liệu.
    private long id;

    // trỏ tới bảng user
    @ManyToOne
    @JoinColumn(name = "userFk")
    private User userFK;

    // trỏ tới bảng playlist
    @ManyToOne
    @JoinColumn(name = "playlistFk")
    private Playlist playlistFk;

}
