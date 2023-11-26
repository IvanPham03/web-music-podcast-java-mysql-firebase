package com.ivanpham.musicapi.repository;

import com.ivanpham.musicapi.model.UserTrack;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserTrackRepository extends JpaRepository<UserTrack, String> {
}
