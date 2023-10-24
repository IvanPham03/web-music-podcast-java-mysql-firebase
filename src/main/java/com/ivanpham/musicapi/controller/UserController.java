package com.ivanpham.musicapi.controller;
import com.ivanpham.musicapi.repository.UserRepository;
import com.ivanpham.musicapi.exception.ResourceNotFoundException;
import com.ivanpham.musicapi.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;


@CrossOrigin("*")
@RestController
@RequestMapping("/auth")
public class UserController {
// injection của một interface UserRepository sử dụng @Autowired.
// UserRepository là một interface dùng để truy cập và tương tác
// với cơ sở dữ liệu để thực hiện các thao tác CRUD (Create, Read, Update, Delete) cho đối tượng User.
    @Autowired
    private UserRepository userRepository;

    // build create user REST API
    // get all users
    @GetMapping
    public List<User> getAllUsers(){
        return userRepository.findAll();
    }

}
