package com.ivanpham.musicapi;

import com.ivanpham.musicapi.exception.UserException;
import com.ivanpham.musicapi.model.User;
import com.ivanpham.musicapi.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.assertj.core.api.Assertions.*;

@SpringBootTest
public class TestUserService {
    @Autowired private UserService userService;

    @Test
    public void addUser(){
        User user = userService.addUser("Phan Minh Chi", "phanminhchi@gmail.com", "chi12345");
        assertThat(user).isNotNull();
    }

    @Test
    public void login_when_account_is_pending(){
        userService.addUser("Phan Minh Chi", "phanminhchi@gmail.com", "chi12345");

        assertThatThrownBy(()->{
            userService.login("phanminhchi@gmail.com","chi12345");
        }).isInstanceOf(UserException.class)
                .hasMessageContaining("User is not activated");

    }

    @Test
    public void login_when_account_is_not_found(){

        assertThatThrownBy(()->{
            userService.login("phanminhchi@gmail.com","chi12345");
        }).isInstanceOf(UserException.class)
                .hasMessageContaining("User is not found");
    }

    @Test
    public void login_when_password_is_incorrect(){
        userService.addUserThenAutoActivate("Phan Minh Chi", "phanminhchi@gmail.com", "chi12345");
        assertThatThrownBy(()->{
            userService.login("phanminhchi@gmail.com","chi123456");
        }).isInstanceOf(UserException.class)
                .hasMessageContaining("Password is incorrect");
    }
    @Test
    public void login_successful(){
        userService.addUserThenAutoActivate("Phan Minh Chi", "phanminhchi@gmail.com", "chi12345");
        User user = userService.login("phanminhchi@gmail.com","chi12345");
        assertThat(user).isNotNull();

    }
}
