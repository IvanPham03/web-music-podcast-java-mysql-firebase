package com.ivanpham.musicapi;

import com.ivanpham.musicapi.hash.Hashing;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
@SpringBootTest
public class TestHash {
    @Autowired private Hashing hash;

    @Test
    public void hashPassword() throws NoSuchAlgorithmException, InvalidKeySpecException {
        var passwords = List.of("abc", "qwert", "ox-123", "_!@#$%^1234");
        for(String password: passwords){
           String hashed_pass = hash.hashPassword(password);
           assertThat(hashed_pass).isNotNull();
        }
    }

    @Test void validatePassword(){
        var passwords = List.of("abc-+", "qwert", "ox-123", "_!@#$%^1234");
        for(String password: passwords){
            String hashed_pass = hash.hashPassword(password);
            hash.validatePassword(password, hashed_pass);
            assertThat(hash.validatePassword(password, hashed_pass)).isTrue();
        }
//        assertThat(hash.validatePassword("abc", "1000:qwert")).isFalse();

    }
}
