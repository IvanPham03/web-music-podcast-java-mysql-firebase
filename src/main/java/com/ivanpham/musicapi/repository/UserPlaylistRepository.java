package com.ivanpham.musicapi.repository;

import com.ivanpham.musicapi.model.UserPlaylist;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserPlaylistRepository extends JpaRepository<UserPlaylist, String> {

}
