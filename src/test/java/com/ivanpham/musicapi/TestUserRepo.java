package com.ivanpham.musicapi;

import com.ivanpham.musicapi.model.State;
import com.ivanpham.musicapi.model.User;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;
//@SpringBootTest
public class TestUserRepo {
    @Test
    public void addUser(){
        UserRepository userRepo = new UserRepository();
        User user = userRepo.addUser("Minh Chi", "phanminhchi@gmail.com","12341234", State.PENDING);
        assertThat(user).isNotNull();
        System.out.println(user.getId());
        assertThat(user.getId()).isNotBlank();
    }
    @Test
    public void addUserWithPendingState(){
        UserRepository userRepo = new UserRepository();
        User user = userRepo.addUser("Minh Chi", "phanminhchi@gmail.com","12341234");
        assertThat(user).isNotNull();
        assertThat(user.getId()).isNotBlank();
        assertThat(user.getState()).isEqualTo(State.PENDING);
    }

    @Test
    public void isEmailExist(){
        UserRepository userRepo = new UserRepository();
        userRepo.addUser("Minh Chi", "phanminhchi@gmail.com","12341234");
        userRepo.addUser("Minh Chi 2", "phanminhchi2@gmail.com","43214321");
        userRepo.addUser("Minh Chi 3", "phanminhchi3@gmail.com","123321");

        assertThat(userRepo.isEmailExist("phanminhchi@gmail.com")).isTrue();
        assertThat(userRepo.isEmailExist("phanminhchi2@gmail.com")).isTrue();
        assertThat(userRepo.isEmailExist("phanminhchi3@gmail.com")).isTrue();
        assertThat(userRepo.isEmailExist("Phanminhchi3@gmail.com")).isTrue();
        assertThat(userRepo.isEmailExist("phanminhchi34@gmail.com")).isFalse();
    }
    @Test
    public void findByEmail(){
        UserRepository userRepo = new UserRepository();
        userRepo.addUser("Minh Chi", "phanminhchi@gmail.com","12341234");
        userRepo.addUser("Minh Chi 2", "phanminhchi2@gmail.com","43214321");
        userRepo.addUser("Minh Chi 3", "phanminhchi3@gmail.com","123321");

        assertThat(userRepo.findByEmail("phanminhchi@gmaIl.com")).isPresent();
        assertThat(userRepo.findByEmail("phanminhchi21@gmaIl.com")).isNotPresent();
    }
}
