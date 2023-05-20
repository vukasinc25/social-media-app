package com.ftn.kvtsvtprojekat.service;

import com.ftn.kvtsvtprojekat.model.User;
import com.ftn.kvtsvtprojekat.model.dto.UserDTO;

import java.util.List;

public interface UserService {
    List<User> findAll();

    User findOneById(Long id);

    User save(UserDTO userDTO);

    void deleteUser(Long id);

    User findByUsername(String username);
}
