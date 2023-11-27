package com.ivanpham.musicapi.controller;
import com.ivanpham.musicapi.model.User;
import com.ivanpham.musicapi.repository.UserRepository2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.GetMapping;
import java.util.List;
import java.util.Optional;


@CrossOrigin("*")
@RestController
@RequestMapping("/auth")
public class UserController {
// injection của một interface UserRepository sử dụng @Autowired.
// UserRepository là một interface dùng để truy cập và tương tác
// với cơ sở dữ liệu để thực hiện các thao tác CRUD (Create, Read, Update, Delete) cho đối tượng User.
    @Autowired
    private UserRepository2 userRepository;
    // lấy tất cả
    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userRepository.findAll();
        return ResponseEntity.ok(users);
    }
    // lấy bằng id
    @GetMapping("/users/{id}")
    public ResponseEntity<User> getUserById(@PathVariable("id") String id) {
        Optional<User> userOptional = userRepository.findById(id);
        return userOptional.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
    // tạo người dùng mới
    @PostMapping("/add")
    public ResponseEntity<?> createUser(@RequestBody User newUser) {
//        System.out.println(newUser.toString());

        try {
            // Kiểm tra xem tên người dùng đã tồn tại hay chưa
            if (userRepository.findByUsername(newUser.getUsername()) != null) {
                return ResponseEntity.badRequest().body("Username is already taken!");
            }

            // Lưu người dùng mới vào cơ sở dữ liệu
            userRepository.save(newUser);
            return ResponseEntity.ok("User created successfully!");
        } catch (Exception e) {
            System.out.println("Method createUser has error");

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
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


}
