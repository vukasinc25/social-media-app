package com.ftn.kvtsvtprojekat.service.impl;

import com.ftn.kvtsvtprojekat.model.User;
import com.ftn.kvtsvtprojekat.model.User;
import com.ftn.kvtsvtprojekat.model.dto.UserDTO;
import com.ftn.kvtsvtprojekat.model.enums.Roles;
import com.ftn.kvtsvtprojekat.repository.UserRepository;
import com.ftn.kvtsvtprojekat.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public List<User> findAll(){
        return userRepository.findAll();
    }

    @Override
    public User findOneById(Long id){
        return userRepository.findUserById(id);
    }

    @Override
    public User save(User user) {

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        if(user.getRole() != Roles.ADMIN){
            user.setRole(Roles.USER);
        }

        return userRepository.save(user);
    }

    @Override
    public void delete(Long id){
        User user = userRepository.findUserById(id);
        user.setIsDeleted(true);
        userRepository.save(user);
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findUserByUsername(username);
    }
}
