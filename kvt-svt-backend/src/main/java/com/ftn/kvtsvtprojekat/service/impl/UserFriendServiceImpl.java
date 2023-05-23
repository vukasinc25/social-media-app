package com.ftn.kvtsvtprojekat.service.impl;

import com.ftn.kvtsvtprojekat.model.UserFriend;
import com.ftn.kvtsvtprojekat.repository.UserFriendRepository;
import com.ftn.kvtsvtprojekat.service.UserFriendService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserFriendServiceImpl implements UserFriendService {

    private final UserFriendRepository userFriendRepository;

    public UserFriendServiceImpl(UserFriendRepository userFriendRepository) {
        this.userFriendRepository = userFriendRepository;
    }

    @Override
    public List<UserFriend> findAll() {
        return userFriendRepository.findAll();
    }

    @Override
    public UserFriend findOneById(Long id) {
        return userFriendRepository.findOneById(id);
    }

    @Override
    public UserFriend save(UserFriend userFriend) {
        return userFriendRepository.save(userFriend);
    }

    @Override
    public void delete(Long id) {
        UserFriend userFriend = userFriendRepository.findOneById(id);
        userFriend.setIsDeleted(true);
        userFriendRepository.save(userFriend);
    }
}
