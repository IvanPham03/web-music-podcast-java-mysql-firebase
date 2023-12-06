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
    @Table(name = "playlist")
//    @JsonIdentityInfo(
//            generator = ObjectIdGenerators.PropertyGenerator.class,
//            property = "id")
    public class Playlist {
        @Id
        @JsonView({View.BasicPlaylist.class, View.BasicUser.class, View.BasicTrack.class})
        private String id = UUID.randomUUID().toString();
        @Column(name = "playlistName")
        @NotBlank(message = "Playlist Name không được trống hay null")
        @JsonView({View.BasicPlaylist.class, View.BasicUser.class, View.BasicTrack.class})
        private String playlistName;
        @Column(name = "playlistDescription")
        @JsonView({View.BasicTrack.class, View.BasicPlaylist.class, View.BasicUser.class})
        private String playlistDescription;
        @Column(name = "imgPlaylist")
        @JsonView({View.BasicTrack.class, View.BasicPlaylist.class, View.BasicUser.class})
        private String imgPlaylist;
        @Column(name = "createAt")
        @JsonView({View.BasicTrack.class, View.BasicPlaylist.class, View.BasicUser.class})
        private String createAt; // Sử dụng kiểu Timestamp
        @Column(name = "updateOn")
        @JsonView({View.BasicTrack.class, View.BasicPlaylist.class, View.BasicUser.class})
        private String updateOn;
        @Column(name = "policy")
        @JsonView({View.BasicTrack.class, View.BasicPlaylist.class, View.BasicUser.class})

        private String playlistPolicy;


        @OneToMany(mappedBy = "playlist",cascade=CascadeType.ALL)
        private List<UserPlaylist> userPlaylists = new ArrayList<>();

        @OneToMany(mappedBy = "playlist",cascade=CascadeType.ALL)
        @JsonView({View.BasicPlaylist.class, View.BasicUser.class})
        private List<PlaylistTrack> playlistTracks = new ArrayList<>();
        @ManyToOne
        @JsonView({View.BasicPlaylist.class, View.BasicTrack.class})
        @JoinColumn(name = "user_id")
        private User user;
        public Playlist(){
            String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());
            this.createAt = timeStamp;
            this.updateOn = timeStamp;
            this.playlistPolicy = "public";
        }

        public Playlist(String playlistName, User user, String playlistPolicy){
            this.playlistName = playlistName;
            this.user = user;
            String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());
            this.createAt = timeStamp;
            this.updateOn = timeStamp;
            this.playlistPolicy = playlistPolicy;
        }
    }
