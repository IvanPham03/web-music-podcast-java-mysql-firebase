package com.ivanpham.musicapi.repository;

import com.ivanpham.musicapi.model.User;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserRepository extends JpaRepository<User, String> {
    User findByEmail(String mail);
    User findByUsername(String userName);
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
//    Page<User> findByRoles(Role role, Pageable pageable);
}
