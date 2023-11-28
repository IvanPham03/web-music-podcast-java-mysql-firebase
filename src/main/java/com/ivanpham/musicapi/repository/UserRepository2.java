package com.ivanpham.musicapi.repository;

import com.ivanpham.musicapi.model.Album;
import com.ivanpham.musicapi.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepository2  extends JpaRepository<User, String> {
    User findByEmail(String mail);

    @Query("SELECT CASE WHEN COUNT(u) > 0 THEN true ELSE false END FROM User u WHERE u.id = :userId AND u.role = 'admin'")
    boolean existsByIdAndRoleAdmin(@Param("userId") String userId);
}
