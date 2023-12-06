package com.ivanpham.musicapi.model;

import com.fasterxml.jackson.annotation.JsonView;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="roles")
public class Role
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonView({View.BasicUser.class, View.BasicTrack.class})
    private Long id;

    @Column(nullable=false, unique=true)
    @JsonView({View.BasicUser.class, View.BasicTrack.class})
    private String name;

    @ManyToMany(mappedBy="roles",cascade=CascadeType.ALL)
    private List<User> users;

    public Role(Long id, String name){
        this.id = id;
        this.name = name;
    }
}
