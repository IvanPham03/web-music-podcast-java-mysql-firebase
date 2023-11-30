package com.ivanpham.musicapi.service;

import com.ivanpham.musicapi.config.JWTTokenProvider;
import com.ivanpham.musicapi.exception.BadRequestException;
import com.ivanpham.musicapi.model.Role;
import com.ivanpham.musicapi.model.User;
import com.ivanpham.musicapi.repository.RoleRepository;
import com.ivanpham.musicapi.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
@AllArgsConstructor
public class AuthService {
    private UserRepository userRepository;

    private PasswordEncoder passwordEncoder;
    private RoleRepository roleRepository;
    private AuthenticationManager authenticationManager;
    private JWTTokenProvider jwtTokenProvider;
    public void createUser(User newUser){
        try {
//             Kiểm tra xem tên người dùng đã tồn tại hay chưa
            if (userRepository.existsByUsername(newUser.getUsername()) || userRepository.existsByEmail(newUser.getEmail())) {
                if(userRepository.existsByUsername(newUser.getUsername())){
                    throw new BadRequestException("Username is already taken!");
                }
                else{
                    throw new BadRequestException("Email is already taken!");
                }

            }

            // Lưu người dùng mới vào cơ sở dữ liệu
            Role role = roleRepository.findByName("ROLE_USER");
            if(role == null){
                role = checkRoleExist();
            }
            newUser.setRoles(Arrays.asList(role));
            newUser.setPassword(passwordEncoder.encode(newUser.getPassword()));

            userRepository.save(newUser);

        } catch (Exception e) {
            System.out.println("Method createUser has error " + newUser.getUsername() +" " + (newUser.getPassword()) +" " + newUser.getEmail());
        }
    }
    private Role checkRoleExist() {
        Role role = new Role();
        role.setName("ROLE_USER");
        return roleRepository.save(role);
    }
    public String login(User user) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            user.getEmail(), user.getPassword()
                    )
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);
            return jwtTokenProvider.createToken(authentication);
        } catch (Exception e) {
            System.out.println("Method createUser has error " + user.getUsername() + " " + (user.getPassword()) + " " + user.getEmail());
            return "";
        }
    }

}
