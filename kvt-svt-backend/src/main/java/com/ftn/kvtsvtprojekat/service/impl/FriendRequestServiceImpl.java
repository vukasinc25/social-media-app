package com.ftn.kvtsvtprojekat.service.impl;

import com.ftn.kvtsvtprojekat.model.FriendRequest;
import com.ftn.kvtsvtprojekat.model.FriendRequest;
import com.ftn.kvtsvtprojekat.repository.FriendRequestRepository;
import com.ftn.kvtsvtprojekat.service.FriendRequestService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FriendRequestServiceImpl implements FriendRequestService {
    
    public final FriendRequestRepository friendRequestRepository;
    private final ModelMapper modelMapper;

    public FriendRequestServiceImpl(FriendRequestRepository friendRequestRepository, ModelMapper modelMapper) {
        this.friendRequestRepository = friendRequestRepository;
        this.modelMapper = modelMapper;
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
    public FriendRequest save(FriendRequest friendRequest){
        return friendRequestRepository.save(friendRequest);
    }

    @Override
    public void delete(Long id){
        FriendRequest friendRequest = friendRequestRepository.findFriendRequestById(id);
        friendRequest.setIsDeleted(true);
        friendRequestRepository.save(friendRequest);
    }
}
