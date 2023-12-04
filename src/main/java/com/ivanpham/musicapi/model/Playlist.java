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
    @Table(name = "playlist")
    @JsonIdentityInfo(
            generator = ObjectIdGenerators.PropertyGenerator.class,
            property = "id")
    public class Playlist {
        @Id
        private String id = UUID.randomUUID().toString();
        @Column(name = "playlistName")
        private String playlistName;
        @Column(name = "playlistDescription")
        private String playlistDescription;
        @Column(name = "imgPlaylist")
        private String imgPlaylist;
        @Column(name = "createAt")
        private String createAt; // Sử dụng kiểu Timestamp
        @Column(name = "updateOn")
        private String updateOn;
        @Column(name = "policy")
        private String playlistPolicy;

        @OneToMany(mappedBy = "playlist")
        private List<UserPlaylist> userPlaylists = new ArrayList<>();

        @OneToMany(mappedBy = "playlist")
        private List<PlaylistTrack> playlistTracks = new ArrayList<>();
        @ManyToOne
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
