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
@Table(name = "song")
public class Song {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "artistID")
    private String artistId;
    @Column(name = "songName")
    private String songName;
    @Column(name = "description")
    private String description;
    @Column(name = "timestamp")
    private String timestamp;
    @ManyToOne
    @JoinColumn(name = "user")
    //    Please note that the @OneToMany annotation is used
    //    to define the property in Item class that will be used
    //    to map the mappedBy variable. That is why we have a property
    //    named “cart” in the Item class:
    private User user;
}
