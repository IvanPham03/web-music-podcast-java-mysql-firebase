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
        Role role_admin = roleRepository.findByName("ADMIN");
        if(role_admin == null){
            role_admin = checkRoleAdminExist();
        }
        Role role_user = roleRepository.findByName("USER");
        if(role_user == null){
            role_user = checkRoleUserExist();
        }
        Role role_artist = roleRepository.findByName("ARTIST");
        if(role_artist == null){
            role_artist = checkRoleArtistExist();
        }
        // Tạo các đối tượng user
        User admin1 = new User("admin1@gmail.com", "admin1", passwordEncoder.encode("admin1"), Arrays.asList(role_admin));
        User admin2 = new User("admin2@gmail.com", "admin2", passwordEncoder.encode("admin2"), Arrays.asList(role_admin));

        User artist1 = new User("artist1@gmail.com", "artist1", passwordEncoder.encode("artist1"), Arrays.asList(role_artist));
        User artist2 = new User("artist2@gmail.com", "artist2", passwordEncoder.encode("artist2"), Arrays.asList(role_artist));
       User artist3 = new User("artist3@gmail.com", "artist3", passwordEncoder.encode("artist3"), Arrays.asList(role_artist));
       User artist4 = new User("artist4@gmail.com", "artist4", passwordEncoder.encode("artist4"), Arrays.asList(role_artist));
       User artist5 = new User("artist5@gmail.com", "artist5", passwordEncoder.encode("artist5"), Arrays.asList(role_artist));
       User artist6 = new User("artist6@gmail.com", "artist6", passwordEncoder.encode("artist6"), Arrays.asList(role_artist));


        User user1 = new User("user1@gmail.com", "user1", passwordEncoder.encode("user1"), Arrays.asList(role_user));
        User user2 = new User("user2@gmail.com", "user2", passwordEncoder.encode("user2"), Arrays.asList(role_user));
       User user3 = new User("user3@gmail.com", "user3", passwordEncoder.encode("user3"), Arrays.asList(role_user));
       User user4 = new User("user4@gmail.com", "user4", passwordEncoder.encode("user4"), Arrays.asList(role_user));
       User user5 = new User("user5@gmail.com", "user5", passwordEncoder.encode("user5"), Arrays.asList(role_user));
       User user6 = new User("user6@gmail.com", "user6", passwordEncoder.encode("user6"), Arrays.asList(role_user));

       User PhamQuynhAnh = new User("PhamQuynhAnh@gmail.com", "PhamQuynhAnh", passwordEncoder.encode("123123"), Arrays.asList(role_artist));
       User NQP = new User("NQP@gmail.com", "NQP", passwordEncoder.encode("123123"), Arrays.asList(role_artist));
       User ChauKhaiPhong = new User("ChauKhaiPhong@gmail.com", "ChauKhaiPhong", passwordEncoder.encode("123123"), Arrays.asList(role_artist));
       User Keyo = new User("Keyo@gmail.com", "Keyo", passwordEncoder.encode("123123"), Arrays.asList(role_artist));
       User TrangHan = new User("TrangHan@gmail.com", "TrangHan", passwordEncoder.encode("123123"), Arrays.asList(role_artist));
       User Seachain = new User("Seachain@gmail.com", "Seachain", passwordEncoder.encode("123123"), Arrays.asList(role_artist));
       User EmD = new User("EmD@gmail.com", "EmD", passwordEncoder.encode("123123"), Arrays.asList(role_artist));
       User QuocAnh = new User("QuocAnh@gmail.com", "QuocAnh", passwordEncoder.encode("123123"), Arrays.asList(role_artist));
       User LinhQueen = new User("LinhQueen@gmail.com", "LinhQueen", passwordEncoder.encode("123123"), Arrays.asList(role_artist));
       User Misabae = new User("Misabae@gmail.com", "Misabae", passwordEncoder.encode("123123"), Arrays.asList(role_artist));
       User LangLD = new User("LangLD@gmail.com", "LangLD", passwordEncoder.encode("123123"), Arrays.asList(role_artist));
       User DinhUyen = new User("DinhUyen@gmail.com", "DinhUyen", passwordEncoder.encode("123123"), Arrays.asList(role_artist));
       User TheSheep = new User("TheSheep@gmail.com", "TheSheep", passwordEncoder.encode("123123"), Arrays.asList(role_artist));
       User Orange = new User("Orange@gmail.com", "Orange", passwordEncoder.encode("123123"), Arrays.asList(role_artist));
       User MyMy = new User("MyMy@gmail.com", "MyMy", passwordEncoder.encode("123123"), Arrays.asList(role_artist));
       User HoangPhuong = new User("HoangPhuong@gmail.com", "HoangPhuong", passwordEncoder.encode("123123"), Arrays.asList(role_artist));
       User RickyStar = new User("RickyStar@gmail.com", "RickyStar", passwordEncoder.encode("123123"), Arrays.asList(role_artist));
       User Rhy = new User("Rhy@gmail.com", "Rhy", passwordEncoder.encode("123123"), Arrays.asList(role_artist));
       User JBHaCuong = new User("JBHaCuong@gmail.com", "JBHaCuong", passwordEncoder.encode("123123"), Arrays.asList(role_artist));
       User Umie = new User("Umie@gmail.com", "Umie", passwordEncoder.encode("123123"), Arrays.asList(role_artist));

        users.add(user1);
        users.add(admin1);
        users.add(user2);
        users.add(admin1);
        users.add(admin2);
        users.add(artist1);
        users.add(artist2);

       users.add(artist3);
       users.add(artist4);
       users.add(artist5);
       users.add(artist6);

       users.add(user3);
       users.add(user4);
       users.add(user5);
       users.add(user6);

       users.add(PhamQuynhAnh);
       users.add(NQP);
       users.add(ChauKhaiPhong);
       users.add(Keyo);
       users.add(TrangHan);
       users.add(Seachain);
       users.add(EmD);
       users.add(QuocAnh);
       users.add(LinhQueen);
       users.add(Misabae);
       users.add(LangLD);
       users.add(DinhUyen);
       users.add(TheSheep);
       users.add(Orange);
       users.add(MyMy);
       users.add(HoangPhuong);
       users.add(RickyStar);
       users.add(Rhy);
       users.add(JBHaCuong);
       users.add(Umie);

        return users;
    }
    private Role checkRoleAdminExist() {
        Role role = new Role();
        role.setName("ADMIN");
        return roleRepository.save(role);
    }
    private Role checkRoleUserExist() {
        Role role = new Role();
        role.setName("USER");
        return roleRepository.save(role);
    }
    private Role checkRoleArtistExist() {
        Role role = new Role();
        role.setName("ARTIST");
        return roleRepository.save(role);
    }
}
