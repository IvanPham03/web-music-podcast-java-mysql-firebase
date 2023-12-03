package com.ivanpham.musicapi.repository;

import com.ivanpham.musicapi.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface UserRepository extends JpaRepository<User, String> {
    User findByEmail(String mail);
    User findByUsername(String userName);
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
//    Page<User> findByRoles(Role role, Pageable pageable);
    @Query("SELECT CASE WHEN COUNT(u) > 0 THEN true ELSE false END FROM User u JOIN u.roles r WHERE r.id = 1 AND u.id = :userId")
    boolean existsByIdAndRoleAdmin(@Param("userId") String userId);
}
