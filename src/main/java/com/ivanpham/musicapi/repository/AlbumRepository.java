package com.ivanpham.musicapi.repository;

import com.ivanpham.musicapi.model.Album;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AlbumRepository extends JpaRepository<Album, String> {
//    Album findById(String id);
}
