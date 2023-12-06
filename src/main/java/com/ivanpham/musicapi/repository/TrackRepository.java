package com.ivanpham.musicapi.repository;

import com.ivanpham.musicapi.model.Track;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TrackRepository extends JpaRepository<Track,String> {
    Track getByUrl(String s);

    @Query("SELECT COUNT(t) > 0 FROM Track t WHERE t.id = :trackId AND t.user.id = :userId")
    boolean isOwner(@Param("trackId") String trackId, @Param("userId") String userId);

    //Tim kiem
    @Query("SELECT c FROM Track c WHERE c.trackName LIKE %?1%")
    List<Track> searchTrack(String keyword);
//
//    //kiem tra ton tai ten
//    @Query("SELECT CASE WHEN COUNT(t) > 0 THEN true ELSE false END FROM Track t WHERE t.trackName = :trackName")
//    boolean existsByTrackName(String trackName);
}
