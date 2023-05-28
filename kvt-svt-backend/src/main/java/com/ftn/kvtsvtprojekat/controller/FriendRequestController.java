package com.ftn.kvtsvtprojekat.controller;

import com.ftn.kvtsvtprojekat.model.FriendRequest;
import com.ftn.kvtsvtprojekat.model.FriendRequest;
import com.ftn.kvtsvtprojekat.model.FriendRequest;
import com.ftn.kvtsvtprojekat.model.dto.FriendRequestDTO;
import com.ftn.kvtsvtprojekat.model.dto.FriendRequestDTO;
import com.ftn.kvtsvtprojekat.service.FriendRequestService;
import com.ftn.kvtsvtprojekat.service.FriendRequestService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.http.ResponseEntity.status;

@RestController
@RequestMapping("/api/friendRequest")
public class FriendRequestController {
    
    private final FriendRequestService friendRequestService;
    private final ModelMapper modelMapper;

    public FriendRequestController(FriendRequestService friendRequestService, ModelMapper modelMapper) {
        this.friendRequestService = friendRequestService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/all")
    public ResponseEntity<List<FriendRequestDTO>> getFriendRequests() {

        List<FriendRequest> userFriends = friendRequestService.findAll();
        List<FriendRequestDTO> userFriendsDTO = new ArrayList<>();
        for (FriendRequest userFriend : userFriends) {
            if (!userFriend.getIsDeleted() ) {
                FriendRequestDTO userFriendDTO = modelMapper.map(userFriend, FriendRequestDTO.class);
                userFriendsDTO.add(userFriendDTO);
            }
        }
        return new ResponseEntity<>(userFriendsDTO, HttpStatus.OK);
    }

    @GetMapping("/deleted")
    public ResponseEntity<List<FriendRequestDTO>> getFriendRequestsDeleted() {

        List<FriendRequest> userFriends = friendRequestService.findAll();
        List<FriendRequestDTO> userFriendsDTO = new ArrayList<>();
        for (FriendRequest userFriend : userFriends) {
            if (userFriend.getIsDeleted()) {
                FriendRequestDTO userFriendDTO = modelMapper.map(userFriend, FriendRequestDTO.class);
                userFriendsDTO.add(userFriendDTO);
            }
        }
        return new ResponseEntity<>(userFriendsDTO, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<FriendRequestDTO> getFriendRequest(@PathVariable("id") Long id) {
        FriendRequest userFriend = friendRequestService.findOneById(id);

        if (userFriend == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        FriendRequestDTO userFriendDTO = modelMapper.map(userFriend, FriendRequestDTO.class);

        return status(HttpStatus.OK).body(userFriendDTO);
    }

    @PostMapping(value = "/create", consumes = "application/json")
    public ResponseEntity<FriendRequest> createFriendRequest(@Valid @RequestBody FriendRequestDTO userFriendDTO) {
        FriendRequest userFriend = modelMapper.map(userFriendDTO, FriendRequest.class);
        userFriend.setRequestDate(LocalDateTime.now());
        userFriend.setIsDeleted(false);
        userFriend.setApproved(false);
        friendRequestService.save(userFriend);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @DeleteMapping(value = "/accept/{id}")
    public ResponseEntity<FriendRequestDTO> updateFriendRequest(@PathVariable("id") Long id) {
        FriendRequest group = friendRequestService.findOneById(id);
        if(group == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        group.setResponseDate(LocalDateTime.now());
        group.setApproved(true);

        friendRequestService.save(group);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    
    @DeleteMapping(value = "/delete/{id}")
    public ResponseEntity<Void> deleteFriendRequest(@PathVariable Long id) {
        if(id == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        FriendRequest userFriend = friendRequestService.findOneById(id);

        if (userFriend != null) {
            friendRequestService.delete(userFriend.getId());
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.I_AM_A_TEAPOT);
        }
    }
}
