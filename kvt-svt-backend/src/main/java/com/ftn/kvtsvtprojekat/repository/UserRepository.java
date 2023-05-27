package com.ftn.kvtsvtprojekat.repository;

import com.ftn.kvtsvtprojekat.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findUserById(Long id);

    User findUserByUsername(String username);

    List<User> findByFirstnameContainingOrLastnameContaining(String firstname, String lastname);

    @Query("SELECT DISTINCT u FROM User u JOIN FETCH GroupRequest gr WHERE gr.user.id = u.id AND gr.group.id = :userId AND gr.approved = true")
    List<User> findAllUsersWithGroupRequests(@Param("userId") Long userId);

    @Query("SELECT u FROM User u JOIN UserFriend uf WHERE uf.user.id = u.id AND u.id = :userId")
    List<User> findUserFriends(@Param("userId") Long userId);
}
