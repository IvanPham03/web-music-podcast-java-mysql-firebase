package com.ivanpham.musicapi.repository;

import com.ivanpham.musicapi.model.PlaylistTrack;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlaylistTrackRepository extends JpaRepository<PlaylistTrack, String> {
}
