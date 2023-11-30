package com.ivanpham.musicapi.model.seedData;

import com.ivanpham.musicapi.model.Role;
import com.ivanpham.musicapi.model.User;
import com.ivanpham.musicapi.repository.RoleRepository;
import com.ivanpham.musicapi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
@Component
public class RoleSeeder {
        private final RoleRepository roleRepository;
        private final UserRepository userRepository2;

        @Autowired
        public RoleSeeder(RoleRepository roleRepository, UserRepository userRepository2) {
            this.roleRepository = roleRepository;
            this.userRepository2 = userRepository2;
        }

        public void seedRole() {
            if (roleRepository.count() == 0) {
                List<Role> role = createRoles();

                // Lưu danh sách tracks vào cơ sở dữ liệu
                roleRepository.saveAll(role);
            }
        }

        private List<Role> createRoles() {
            List<Role> roles = new ArrayList<>();

            User user = userRepository2.findByEmail("admin1@gmail.com");
            if(user != null){
                // Tạo các đối tượng album
                Role role_admin = new Role(1L, "ROLE_ADMIN");
                Role role_user = new Role(2L, "ROLE_USER");
                Role role_artist = new Role(3L, "ROLE_ARTIST");
                roles.add(role_admin);
                roles.add(role_user);
                roles.add(role_artist);

            }

            return roles;
        }

}
