package com.ftn.kvtsvtprojekat.service;

import com.ftn.kvtsvtprojekat.model.UserFriend;

import java.util.List;

public interface UserFriendService {
    List<UserFriend> findAll();

    UserFriend findOneById(Long id);

    UserFriend save(UserFriend userFriend);

    void delete(Long id);
}
