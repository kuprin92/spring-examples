package com.example.demo.validation;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.validation.ConstraintViolationException;

import static org.junit.jupiter.api.Assertions.assertThrows;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {Config.class})
class UserServiceTest {
    @Autowired
    private UserService userService;

    @Test
    void createUser() {
        ConstraintViolationException violationException = assertThrows(ConstraintViolationException.class, () -> userService.createUser(User.builder().email("sq").build()));
        System.out.println(violationException);
    }

    @Test
    void getUsers() {
    }

}