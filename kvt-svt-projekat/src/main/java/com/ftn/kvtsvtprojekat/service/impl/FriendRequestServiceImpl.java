package com.ftn.kvtsvtprojekat.service.impl;

import com.ftn.kvtsvtprojekat.model.FriendRequest;
import com.ftn.kvtsvtprojekat.repository.FriendRequestRepository;
import com.ftn.kvtsvtprojekat.service.FriendRequestService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FriendRequestServiceImpl implements FriendRequestService {
    
    public final FriendRequestRepository friendRequestRepository;

    public FriendRequestServiceImpl(FriendRequestRepository friendRequestRepository) {
        this.friendRequestRepository = friendRequestRepository;
    }

    @Override
    public List<FriendRequest> findAll(){
        return friendRequestRepository.findAll();
    }

    @Override
    public FriendRequest findOneById(Long id){
        return friendRequestRepository.findFriendRequestById(id);
    }

    @Override
    public FriendRequest addFriendRequest(FriendRequest friendRequest){
        return friendRequestRepository.save(friendRequest);
    }

    @Override
    public FriendRequest updateFriendRequest(FriendRequest friendRequest){
        return friendRequestRepository.save(friendRequest);
    }

    @Override
    public FriendRequest deleteFriendRequest(Long id){
        return friendRequestRepository.deleteFriendRequestById(id);
    }
}
