package com.ftn.kvtsvtprojekat.service.impl;

import com.ftn.kvtsvtprojekat.model.FriendRequest;
import com.ftn.kvtsvtprojekat.repository.FriendRequestRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FriendRequestServiceImpl {
    
    public final FriendRequestRepository friendRequestRepository;

    public FriendRequestServiceImpl(FriendRequestRepository friendRequestRepository) {
        this.friendRequestRepository = friendRequestRepository;
    }

    public List<FriendRequest> findAll(){
        return friendRequestRepository.findAll();
    }

    public FriendRequest findOneById(Long id){
        return friendRequestRepository.findFriendRequestById(id);
    }

    public FriendRequest addFriendRequest(FriendRequest friendRequest){
        return friendRequestRepository.save(friendRequest);
    }

    public FriendRequest updateFriendRequest(FriendRequest friendRequest){
        return friendRequestRepository.save(friendRequest);
    }

    public FriendRequest deleteFriendRequest(Long id){
        return friendRequestRepository.deleteFriendRequestById(id);
    }
}
