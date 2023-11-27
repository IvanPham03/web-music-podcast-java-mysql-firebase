package com.ivanpham.musicapi.repository;

import com.ivanpham.musicapi.model.ArtistTrack;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArtistTrackRepository extends JpaRepository<ArtistTrack, String> {
}
