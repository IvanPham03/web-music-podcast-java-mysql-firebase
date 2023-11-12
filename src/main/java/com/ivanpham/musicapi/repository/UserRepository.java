package com.ivanpham.musicapi.repository;

import com.ivanpham.musicapi.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

//gonna create a Spring Data JPA repository to talk with the MySQL database
public interface UserRepository extends JpaRepository<User, Long>{

}
