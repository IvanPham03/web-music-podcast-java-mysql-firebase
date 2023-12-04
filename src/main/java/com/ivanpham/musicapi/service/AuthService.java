package com.ivanpham.musicapi.service;

import com.ivanpham.musicapi.config.JWTTokenProvider;
import com.ivanpham.musicapi.exception.BadRequestException;
import com.ivanpham.musicapi.model.Role;
import com.ivanpham.musicapi.model.User;
import com.ivanpham.musicapi.repository.RoleRepository;
import com.ivanpham.musicapi.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Service
//@NoArgsConstructor
public class AuthService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JWTTokenProvider jwtTokenProvider;

    private UserDetails userDetails;


    // tạo thêm admin
    public void createAdmin(User newUser){
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
            Role role = roleRepository.findByName("ADMIN");
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

    // lấy thông tin người dùng từ token
    public Object getUserInfor(String token) {
        if (jwtTokenProvider.validateToken(token)) { // Kiểm tra tính hợp lệ của token trước khi giải mã
            String userEmailFromToken = jwtTokenProvider.getUserEmailFromToken(token);
            // Lấy thông tin người dùng từ token và trả về UserDetails
            if(userRepository.existsByEmail(userEmailFromToken)){
                return userRepository.findByEmail(userEmailFromToken);
            }
        }
        return null; // Trả về null nếu token không hợp lệ
    }
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
            Role role = roleRepository.findByName("USER");
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
        role.setName("USER");
        return roleRepository.save(role);
    }
    public Object login(User user) {
        try {

            // kiêm tra người dùng tồn tài rồi mới check mật khẩu
            User existingUser = userRepository.findByEmail(user.getEmail());
            if(existingUser != null){
                Authentication authentication = authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(
                                user.getEmail(), user.getPassword()
                        )
                );
                SecurityContextHolder.getContext().setAuthentication(authentication);
                UserDetails userDetails = (UserDetails) authentication.getPrincipal();

                System.out.println("Login successfully: ");
                System.out.println("Username: " + userDetails.getUsername());
                System.out.println("Authorities: " + userDetails.getAuthorities());
                String token = jwtTokenProvider.createToken(authentication);
                Map<String, Object> response = new HashMap<>();
                response.put("token", token);
                response.put("role", existingUser.getRoles());
                // tóm lại chức năng là xác thực, lấy thông tin và tạo token
                return response;
            }
            else{
                return null;
            }

        } catch (Exception e) {
            System.out.println("User is logging: " + user.getUsername() + " " + (user.getRoles()) + " " + user.getEmail());
            return "";
        }
    }

}
