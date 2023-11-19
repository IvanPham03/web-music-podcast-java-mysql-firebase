package com.ivanpham.musicapi.service;

import com.ivanpham.musicapi.model.User;
import org.springframework.stereotype.Service;

import java.util.Optional;

public interface UserService {
    public User login(String email, String password);
    public boolean logout(String email);
    public User addUser(String username, String email, String password);
    public User addUserThenAutoActivate(String username, String email, String password);
    public Boolean activateUser(String activation_code);
    public Boolean updatePassword(String email, String password);
    public Boolean updateEmail(String email, String newEmail);

    public Optional<User> findByEmail(String email);
    public User findById(String id);


}
