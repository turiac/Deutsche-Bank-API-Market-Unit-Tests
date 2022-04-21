package com.example.unitTests.Tests;

import com.example.unitTests.model.User;
import com.example.unitTests.service.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class UserServiceTest {
    @Autowired
    private UserService userService;

    @Test
    public void whenCreateUser_ThenCreateUserInDataBase() {
        User user = new User();
        user.setEmail("user@test.com");

        userService.createUser(user);

        Assertions.assertEquals(1, userService.getById(1L));
    }
}
