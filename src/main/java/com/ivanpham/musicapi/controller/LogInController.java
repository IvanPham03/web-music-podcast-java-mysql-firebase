//package com.ivanpham.musicapi.controller;
//
//import com.ivanpham.musicapi.exception.UserException;
//import com.ivanpham.musicapi.model.Role;
//import com.ivanpham.musicapi.model.User;
//import com.ivanpham.musicapi.repository.RoleRepository;
//import com.ivanpham.musicapi.repository.UserRepository2;
//
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.validation.BindingResult;
//import org.springframework.web.bind.annotation.*;
//
//import javax.validation.Valid;
//import java.util.Arrays;
//import java.util.UUID;
//
//
//@RestController
//@RequestMapping("/api/Authenticate/")
//public class LogInController {
//    private RoleRepository roleRepository;
//    private PasswordEncoder passwordEncoder;
//    private UserRepository2 userRepository;
//    @PostMapping("register")
//    public ResponseEntity<?> createUser(@RequestBody User newUser) {
////        System.out.println(newUser.toString());
//
//        try {
//            // Kiểm tra xem tên người dùng đã tồn tại hay chưa
//            if (userRepository.existsByUsername(newUser.getUsername())) {
//                return ResponseEntity.badRequest().body("Username is already taken!");
//            }
//            // Lưu người dùng mới vào cơ sở dữ liệu
////            Role role = roleRepository.findByName("ROLE_USER");
////            if(role == null){
////                role = checkRoleExist();
////            }
////            newUser.setRoles(Arrays.asList(role));
////            UUID randomUUID = UUID.randomUUID();
////            newUser.setId(randomUUID.toString());
////            newUser.setPassword(passwordEncoder.encode(newUser.getPassword()));
//            userRepository.save(newUser);
//            return ResponseEntity.ok("User created successfully!");
//        } catch (Exception e) {
//            System.out.println("Method createUser has error " + newUser.getUsername() +" " + (newUser.getPassword()z +" " + newUser.getEmail() + " "+ newUser.getCreateAt());
//
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
//        }
//    }
//    private Role checkRoleExist() {
//        Role role = new Role();
//        role.setName("ROLE_USER");
//        return roleRepository.save(role);
//    }}
