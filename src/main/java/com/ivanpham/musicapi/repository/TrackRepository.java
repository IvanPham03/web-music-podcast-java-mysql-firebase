package com.ivanpham.musicapi.repository;

import com.ivanpham.musicapi.model.Track;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TrackRepository extends JpaRepository<Track,Long> {
}
