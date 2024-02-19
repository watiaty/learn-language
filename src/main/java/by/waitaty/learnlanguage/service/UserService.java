package by.waitaty.learnlanguage.service;

import by.waitaty.learnlanguage.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    Optional<User> findByEmail(String email);

    Optional<User> findByUsername(String username);

    List<User> findAll();

    User updateUser(User user);
}
