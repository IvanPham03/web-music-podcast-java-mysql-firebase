package com.ivanpham.musicapi.repository;

import com.ivanpham.musicapi.model.State;
import com.ivanpham.musicapi.model.User;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

//gonna create a Spring Data JPA repository to talk with the MySQL database
@Repository
public class UserRepository{
    private ConcurrentHashMap<String, User> users = new ConcurrentHashMap<>();

    public User addUser(String username, String email, String hashed_password){
        return addUser(username, email, hashed_password, State.PENDING);
    }
    public User addUser(String username, String email, String hashed_password, State state){
        String id = UUID.randomUUID().toString();
        User user = User.builder()
                .id(id)
                .username(username)
                .email(email)
                .state(state)
                .password(hashed_password)
                .build();
        users.put(id,user);
        return user;
    }

    public boolean isEmailExist(String email){
        return users.values().stream().anyMatch(user -> user.getEmail().equalsIgnoreCase(email));
    }

    public Optional<User> findByEmail(String email){
        return users.values().stream()
                .filter(user -> user.getEmail().equalsIgnoreCase(email)).findFirst();
    }
}
