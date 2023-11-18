package com.ivanpham.musicapi.repository;

import com.ivanpham.musicapi.model.Album;
import com.ivanpham.musicapi.model.Artist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface AlbumRepository extends JpaRepository<Album, Long> {
    public Album findById(long id);
}
