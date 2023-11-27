package com.ivanpham.musicapi.repository;

import com.ivanpham.musicapi.model.User;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserRepository2  extends JpaRepository<User, String> {
    User findByEmail(String mail);
    User findByUsername(String userName);
}
