package com.ftn.kvtsvtprojekat.repository;

import com.ftn.kvtsvtprojekat.model.FriendRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FriendRequestRepository extends JpaRepository<FriendRequest,Long> {
    FriendRequest findFriendRequestById(Long id);
}
