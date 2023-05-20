package com.ftn.kvtsvtprojekat.service;

import com.ftn.kvtsvtprojekat.model.Banned;
import com.ftn.kvtsvtprojekat.model.FriendRequest;

import java.util.List;

public interface FriendRequestService {
    List<FriendRequest> findAll();

    FriendRequest findOneById(Long id);

    FriendRequest save(FriendRequest friendRequest);

    void delete(Long id);
}
