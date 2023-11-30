package com.ivanpham.musicapi.model.seedData;

import com.ivanpham.musicapi.model.Role;
import com.ivanpham.musicapi.model.User;
import com.ivanpham.musicapi.repository.RoleRepository;
import com.ivanpham.musicapi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
@Component
public class UserSeeder {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    @Autowired
    public UserSeeder(
            UserRepository userRepository,
            RoleRepository roleRepository,
            PasswordEncoder passwordEncoder
    ) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void seedUsers() {
        if (userRepository.count() == 0) {
            List<User> users = createUsers();

            // Lưu danh sách tracks vào cơ sở dữ liệu
            userRepository.saveAll(users);
        }
    }

    private List<User> createUsers() {
        List<User> users = new ArrayList<>();
        Role role_admin = roleRepository.findByName("ROLE_ADMIN");
        if(role_admin == null){
            role_admin = checkRoleAdminExist();
        }
        Role role_user = roleRepository.findByName("ROLE_USER");
        if(role_user == null){
            role_user = checkRoleUserExist();
        }
        Role role_artist = roleRepository.findByName("ROLE_ARTIST");
        if(role_artist == null){
            role_artist = checkRoleArtistExist();
        }
        // Tạo các đối tượng user
        User admin1 = new User("admin1@gmail.com", "admin1", passwordEncoder.encode("admin1"), Arrays.asList(role_admin));
        User admin2 = new User("admin2@gmail.com", "admin2", passwordEncoder.encode("admin2"), Arrays.asList(role_admin));
        User artist1 = new User("artist1@gmail.com", "artist1", passwordEncoder.encode("artist1"), Arrays.asList(role_artist));
        User artist2 = new User("artist2@gmail.com", "artist2", passwordEncoder.encode("artist2"), Arrays.asList(role_artist));
        User user1 = new User("user1@gmail.com", "user1", passwordEncoder.encode("user1"), Arrays.asList(role_user));
        User user2 = new User("user2@gmail.com", "user2", passwordEncoder.encode("user2"),Arrays.asList(role_user));

        users.add(user1);
        users.add(user2);
        users.add(admin1);
        users.add(admin2);
        users.add(artist1);
        users.add(artist2);

        return users;
    }
    private Role checkRoleAdminExist() {
        Role role = new Role();
        role.setName("ROLE_ADMIN");
        return roleRepository.save(role);
    }
    private Role checkRoleUserExist() {
        Role role = new Role();
        role.setName("ROLE_USER");
        return roleRepository.save(role);
    }
    private Role checkRoleArtistExist() {
        Role role = new Role();
        role.setName("ROLE_ARTIST");
        return roleRepository.save(role);
    }
}
