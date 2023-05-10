package com.ftn.kvtsvtprojekat.service;

import com.ftn.kvtsvtprojekat.model.User;

import java.util.List;

public interface UserService {
    List<User> findAll();

    User findOneById(Long id);

    User addUser(User user);

    User updateUser(User user);

    User deleteUser(Long id);
}
