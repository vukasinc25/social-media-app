package com.ftn.kvtsvtprojekat.service.impl;

import com.ftn.kvtsvtprojekat.model.User;
import com.ftn.kvtsvtprojekat.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void login(String username, String password){
        User user = userRepository.findUserByUsername(username);
        if (user != null){
            if (user.getPassword() == password){
                //TODO Login
            }
            else {

            }
        }
    }

    public List<User> findAllUsers(){
        return userRepository.findAll();
    }

    public User findOneById(Long id){
        return userRepository.findUserById(id);
    }

    public User addUser(User user){
        return userRepository.save(user);
    }

    public User updateUser(User user){
        return userRepository.save(user);
    }

    public User deleteUser(Long id){
        return userRepository.deleteUserById(id);
    }

}
