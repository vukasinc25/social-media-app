package com.ftn.kvtsvtprojekat.repository;

import com.ftn.kvtsvtprojekat.model.Group;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GroupRepository extends JpaRepository<Group,Long> {
    Group findGroupById(Long id);

    Group deleteGroupById(Long id);
}
