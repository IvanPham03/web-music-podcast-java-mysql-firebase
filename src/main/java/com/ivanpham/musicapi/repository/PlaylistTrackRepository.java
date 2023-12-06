package com.ivanpham.musicapi.repository;

import com.ivanpham.musicapi.model.PlaylistTrack;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface PlaylistTrackRepository extends JpaRepository<PlaylistTrack, String> {

    @Query("SELECT pt FROM PlaylistTrack pt WHERE pt.track.id = :trackId AND pt.playlist.id = :playlistId")
    PlaylistTrack findByPlaylistAndTrack(String trackId, String playlistId);
}
