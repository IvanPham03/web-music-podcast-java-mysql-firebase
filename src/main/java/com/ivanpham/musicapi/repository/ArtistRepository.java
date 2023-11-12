package com.ivanpham.musicapi.repository;

import com.ivanpham.musicapi.model.Artist;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArtistRepository extends JpaRepository<Artist, Long> {

}
