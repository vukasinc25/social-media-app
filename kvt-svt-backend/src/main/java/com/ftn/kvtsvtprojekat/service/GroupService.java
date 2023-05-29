package com.ftn.kvtsvtprojekat.service;

import com.ftn.kvtsvtprojekat.model.Banned;
import com.ftn.kvtsvtprojekat.model.Group;

import java.util.List;

public interface GroupService {
    List<Group> findAll();

    Group findByName(String name);

    Group findOneById(Long id);

    Group save(Group group);

    void delete(Long id);
}
