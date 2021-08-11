package com.example.demo.validation;

import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Service
@Validated
public class UserService {
    private final List<User> users = new ArrayList<>();


    public UUID createUser(@Valid User user) {
        UUID id = UUID.randomUUID();
        user.setId(id);
        users.add(user);
        return id;
    }

    public List<User> getUsers() {
        return Collections.unmodifiableList(users);
    }
}
