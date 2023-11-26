package com.ivanpham.musicapi.repository;

import com.ivanpham.musicapi.model.Playlist;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlaylistRepository extends JpaRepository<Playlist, String> {
//    Playlist findById(String ID);
}
