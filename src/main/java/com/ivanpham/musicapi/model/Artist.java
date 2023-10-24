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
@Table(name = "artist")
public class Artist {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "artistName")
    private String artistName;
    @Column(name = "db")
    private String db;
    @Column(name = "description")
    private String description;
    @Column(name = "email")
    private String email;
    @Column(name = "timestamp")
    private String timestamp;
}
