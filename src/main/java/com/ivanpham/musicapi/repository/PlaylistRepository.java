package com.ivanpham.musicapi.repository;

import com.ivanpham.musicapi.model.Album;
import com.ivanpham.musicapi.model.Playlist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PlaylistRepository extends JpaRepository<Playlist, String> {
    Playlist findByplaylistName(String name);

    @Query("SELECT p FROM Playlist p WHERE p.playlistPolicy = 'public'")
    List<Playlist> findPublicPlaylists();

    @Query("SELECT COUNT(p) > 0 FROM Playlist p WHERE p.id = :playlistId AND p.user.id = :userId")
    boolean isOwner(@Param("playlistId") String playlistId, @Param("userId") String userId);

    @Query("SELECT p FROM Playlist p WHERE p.user.id = :userId")
    List<Playlist> returnOwnerPlaylist(@Param("userId") String userId);

    @Override
    @Query("SELECT p FROM Playlist p WHERE p.id = :playlistId AND p.playlistPolicy = 'public'")
    Optional<Playlist> findById(@Param("playlistId") String playlistId);

    @Query("SELECT p FROM Playlist p WHERE p.id = :playlistId")
    Optional<Playlist> findByIdAdmin(@Param("playlistId") String playlistId);

    @Query("SELECT p FROM Playlist p WHERE LOWER(p.playlistName) LIKE LOWER(CONCAT('%', :keyword, '%')) AND p.playlistPolicy = 'public'")
    List<Playlist> searchByPlaylistName(@Param("keyword") String keyword);

    @Query("SELECT p FROM Playlist p WHERE LOWER(p.playlistName) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<Playlist> searchByPlaylistNameAdmin(@Param("keyword") String keyword);

//    @Query("SELECT DISTINCT p FROM Playlist p " +
//            "JOIN p.user u " +
//            "JOIN UserPlaylist up ON up.playlist.id = p.id " +
//            "WHERE u.id = :userId OR up.user.id = :userId")
//    List<Playlist> findPlaylistsByUserId(@Param("userId") String userId);
}
