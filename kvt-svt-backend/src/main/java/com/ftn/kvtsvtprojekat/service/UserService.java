package com.ftn.kvtsvtprojekat.service;

import com.ftn.kvtsvtprojekat.model.User;

import java.util.List;

public interface UserService {
    List<User> findAll();

    User findOneById(Long id);

    User save(User user);

    void delete(Long id);

    User findByUsername(String username);
}
