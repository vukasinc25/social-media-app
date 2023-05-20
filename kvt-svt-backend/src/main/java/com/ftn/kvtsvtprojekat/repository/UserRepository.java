package com.ftn.kvtsvtprojekat.repository;

import com.ftn.kvtsvtprojekat.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findUserById(Long id);

    User findUserByUsername(String username);
}