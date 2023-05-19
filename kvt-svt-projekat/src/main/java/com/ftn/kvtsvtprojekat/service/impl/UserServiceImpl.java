package com.ftn.kvtsvtprojekat.service.impl;

import com.ftn.kvtsvtprojekat.model.User;
import com.ftn.kvtsvtprojekat.model.dto.UserDTO;
import com.ftn.kvtsvtprojekat.model.enums.Roles;
import com.ftn.kvtsvtprojekat.repository.UserRepository;
import com.ftn.kvtsvtprojekat.service.UserService;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
    public User createUser(UserDTO userDTO) {

        Optional<User> user = Optional.ofNullable(userRepository.findUserByUsername(userDTO.getUsername()));

        if(user.isPresent()){
            return null;
        }
        User newUser = new User();
        newUser.setUsername(userDTO.getUsername());
        newUser.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        newUser.setRole(Roles.USER);
        newUser = userRepository.save(newUser);

        return newUser;
    }

    @Override
    public User updateUser(User user){
        return userRepository.save(user);
    }

    @Override
    public User deleteUser(Long id){
        return userRepository.deleteUserById(id);
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findUserByUsername(username);
    }
}
