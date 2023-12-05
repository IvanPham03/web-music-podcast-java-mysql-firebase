package com.ivanpham.musicapi.controller;
import com.fasterxml.jackson.annotation.JsonView;
import com.ivanpham.musicapi.model.User;
import com.ivanpham.musicapi.model.View;
import com.ivanpham.musicapi.repository.RoleRepository;
import com.ivanpham.musicapi.repository.UserRepository;
import com.ivanpham.musicapi.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;


@CrossOrigin("*")
@RestController
@RequestMapping("/api/Authenticate")
public class UserController  {
// injection của một interface UserRepository sử dụng @Autowired.
// UserRepository là một interface dùng để truy cập và tương tác
// với cơ sở dữ liệu để thực hiện các thao tác CRUD (Create, Read, Update, Delete) cho đối tượng User.
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private AuthService authService;

    // lấy tất cả
    @GetMapping("/users")
    @JsonView(View.BasicUser.class)
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userRepository.findAll();
        return ResponseEntity.ok(users);
    }
    // lấy bằng id
    @GetMapping("/users/{id}")
    @JsonView(View.BasicUser.class)
    public ResponseEntity<User> getUserById(@PathVariable("id") String id) {
        Optional<User> userOptional = userRepository.findById(id);
        return userOptional.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
    // lấy bằng token
    @GetMapping("/users/info")
    @JsonView(View.BasicUser.class)
    public ResponseEntity<?> getUserToken(@RequestHeader("Authorization") String token) {
        System.out.println(token);
        // Kiểm tra nếu chuỗi bắt đầu bằng "Bearer " thì cắt bỏ phần này
        if(token.startsWith("Bearer ")) {
            token = token.substring(7); // Cắt bỏ "Bearer " từ đầu chuỗi
            Object s = authService.getUserInfor(token);
            if(s != null){
                return new ResponseEntity<>(s, HttpStatus.OK);
            }
            return new ResponseEntity<>("not found", HttpStatus.NOT_FOUND);

        }
        return new ResponseEntity<>("not found token", HttpStatus.BAD_REQUEST);
    }
    // tạo người dùng mới
    @PostMapping("register")
    public ResponseEntity<?> createUser(@RequestBody User newUser) {
//        System.out.println(newUser.toString());
        authService.createUser(newUser);
        return new ResponseEntity<>("User registered success!", HttpStatus.OK);
    }

    //update
    @PutMapping("/users/{id}")
    public ResponseEntity<?> updateUser(@PathVariable("id") String id, @RequestBody User updatedUser) {
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isPresent()) {
            User existingUser = optionalUser.get();
            // Update user details
            existingUser.setUsername(updatedUser.getUsername());
            existingUser.setEmail(updatedUser.getEmail());
            // Update other user fields similarly

            userRepository.save(existingUser);
            return ResponseEntity.ok("User updated successfully!");
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // xoá
    @DeleteMapping("/users/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable("id") String id) {
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isPresent()) {
            userRepository.delete(optionalUser.get());
            return ResponseEntity.ok("User deleted successfully!");
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    @PostMapping("login")
    public ResponseEntity<?> login(@RequestBody User user) {

        Object res = authService.login(user);
        if(res == null){
            return new ResponseEntity<>("Tài khoản không tồn tại!", HttpStatus.NOT_FOUND);
        }
        else if(res.equals("")){
            return new ResponseEntity<>("Server error!", HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>(res, HttpStatus.OK);
    }
}
