package com.ivanpham.musicapi.repository;

import com.ivanpham.musicapi.model.AlbumTrack;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AlbumTrackRepository extends JpaRepository<AlbumTrack, String> {
}
