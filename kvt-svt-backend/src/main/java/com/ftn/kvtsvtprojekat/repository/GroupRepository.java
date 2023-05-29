package com.ftn.kvtsvtprojekat.repository;

import com.ftn.kvtsvtprojekat.model.Group;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GroupRepository extends JpaRepository<Group,Long> {
    Group findGroupById(Long id);

    Group findByName(String name);
}
