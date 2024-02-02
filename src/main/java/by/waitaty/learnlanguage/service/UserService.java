package by.waitaty.learnlanguage.service;

import by.waitaty.learnlanguage.entity.User;

import java.util.Optional;

public interface UserService {
    Optional<User> findByEmail(String email);
}
