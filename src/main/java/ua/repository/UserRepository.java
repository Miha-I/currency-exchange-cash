package ua.repository;

import ua.model.User;

public interface UserRepository {

    User findByUsername(String username);
}
