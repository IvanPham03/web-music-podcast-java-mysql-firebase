package com.ivanpham.musicapi.repository;

import com.ivanpham.musicapi.model.Album;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface AlbumRepository extends JpaRepository<Album, String> {
    Album findByAlbumName(String name);

    //    Album findById(String id);
    @Query("SELECT COUNT(a) > 0 FROM Album a WHERE a.id = :albumId AND a.user.id = :userId")
    boolean isOwner(@Param("albumId") String albumId, @Param("userId") String userId);
    @Query("SELECT a FROM Album a WHERE a.albumPolicy = 'public'")
    List<Album> findAllPublicAlbums();

    @Override
    @Query("SELECT a FROM Album a WHERE a.id = :albumId AND a.albumPolicy = 'public'")
    Optional<Album> findById( @Param("albumId") String albumId);
    @Query("SELECT a FROM Album a WHERE LOWER(a.albumName) LIKE LOWER(CONCAT('%', :keyword, '%')) AND a.albumPolicy = 'public'")
    List<Album> searchByAlbumName(@Param("keyword") String keyword);

//    @Modifying
//    @Transactional
//    @Query("UPDATE Album a SET a.albumName = :albumName, a.albumGenre = :albumGenre, a.albumPolicy = :albumPolicy WHERE a.id = :albumId AND a.user.id = :userId")
//    void editAlbum(@Param("albumId") String albumId, @Param("userId") String userId, @Param("albumName") String albumName, @Param("albumGenre") String albumGenre, @Param("albumPolicy") String albumPolicy);
//
//    @Modifying
//    @Transactional
//    @Query("UPDATE Album a SET a.albumName = :albumName, a.albumGenre = :albumGenre, a.albumPolicy = :albumPolicy WHERE a.id = :albumId")
//    void editAlbumByAdmin(@Param("albumId") String albumId, @Param("albumName") String albumName, @Param("albumGenre") String albumGenre, @Param("albumPolicy") String albumPolicy);
}
