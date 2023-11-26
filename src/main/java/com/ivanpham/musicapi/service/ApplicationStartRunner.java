package com.ivanpham.musicapi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class ApplicationStartRunner implements ApplicationRunner {
//    @Autowired UserService userService;
    @Override
    public void run(ApplicationArguments args) throws Exception {
//        userService.addUserThenAutoActivate("Admin", "admin@gmail.com", "12345");
//        userService.addUser("Phan Minh Chi", "pmc@gmail.com", "12345");
    }
}
