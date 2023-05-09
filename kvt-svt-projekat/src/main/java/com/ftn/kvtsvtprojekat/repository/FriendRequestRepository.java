package com.ftn.kvtsvtprojekat.repository;

import com.ftn.kvtsvtprojekat.model.FriendRequest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FriendRequestRepository extends JpaRepository<FriendRequest,Long> {
    FriendRequest findFriendRequestById(Long id);

    FriendRequest deleteFriendRequestById(Long id);
}
