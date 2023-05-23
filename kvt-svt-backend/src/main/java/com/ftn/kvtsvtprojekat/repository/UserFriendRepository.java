package com.ftn.kvtsvtprojekat.repository;

import com.ftn.kvtsvtprojekat.model.UserFriend;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserFriendRepository extends JpaRepository<UserFriend, Long> {
    UserFriend findOneById(Long id);
}
