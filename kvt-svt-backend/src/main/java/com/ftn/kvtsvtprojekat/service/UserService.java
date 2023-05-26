package com.ftn.kvtsvtprojekat.service;

import com.ftn.kvtsvtprojekat.model.User;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserService {
    List<User> findAll();

    List<User> searchByName(String firstname, String lastname);

    List<User> findAllUsersWithGroupRequests(Long userId);

    List<User> findUserFriends(Long userId);

    User findOneById(Long id);

    User save(User user);

    void delete(Long id);

    User findByUsername(String username);
}
