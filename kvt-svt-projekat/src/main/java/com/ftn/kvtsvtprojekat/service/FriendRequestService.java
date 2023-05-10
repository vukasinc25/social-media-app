package com.ftn.kvtsvtprojekat.service;

import com.ftn.kvtsvtprojekat.model.Banned;
import com.ftn.kvtsvtprojekat.model.FriendRequest;

import java.util.List;

public interface FriendRequestService {
    List<FriendRequest> findAll();

    FriendRequest findOneById(Long id);

    FriendRequest addFriendRequest(FriendRequest friendRequest);

    FriendRequest updateFriendRequest(FriendRequest friendRequest);

    FriendRequest deleteFriendRequest(Long id);
}
