package com.ivanpham.musicapi.repository;

import com.ivanpham.musicapi.model.Playlist;
import com.ivanpham.musicapi.model.UserPlaylist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserPlaylistRepository extends JpaRepository<UserPlaylist, String> {
    @Query("SELECT up.playlist FROM UserPlaylist up WHERE up.user.id = :userId")
    List<Playlist> findPlaylistsByUserId(@Param("userId") String userId);
}
