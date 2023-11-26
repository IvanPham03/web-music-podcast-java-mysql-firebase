package com.ivanpham.musicapi.model.seedData;

import com.ivanpham.musicapi.model.User;
import com.ivanpham.musicapi.repository.UserRepository2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.List;
@Component
public class UserSeeder {
    private final UserRepository2 userRepository;

    @Autowired
    public UserSeeder(UserRepository2 userRepository) {
        this.userRepository = userRepository;
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

        // Tạo các đối tượng user
        User admin1 = new User("admin1@gmail.com", "admin1", "admin1", "admin");
        User admin2 = new User("admin2@gmail.com", "admin2", "admin2", "admin");
        User artist1 = new User("artist1@gmail.com", "artist1", "artist1", "artist");
        User artist2 = new User("artist2@gmail.com", "artist2", "artist2", "artist");
        User user1 = new User("user1@gmail.com", "user1", "user1", "user");
        User user2 = new User("user2@gmail.com", "user2", "user2", "user");

        users.add(user1);
        users.add(user2);
        users.add(admin1);
        users.add(admin2);
        users.add(artist1);
        users.add(artist2);

        return users;
    }
}
