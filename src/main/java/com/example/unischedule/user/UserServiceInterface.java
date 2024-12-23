package com.example.unischedule.user;

import java.util.Iterator;
import java.util.List;
import java.util.UUID;

public interface UserServiceInterface {
    User getUserById(UUID id);
    User getUserByEmail(String email);
    User createUser(User user);
    User updateUser(User user);
    void deleteUser(User user);
    List<User> getAllUsers();
}