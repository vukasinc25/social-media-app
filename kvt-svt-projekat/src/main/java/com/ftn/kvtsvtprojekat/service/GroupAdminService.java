package com.ftn.kvtsvtprojekat.service;

import com.ftn.kvtsvtprojekat.model.Banned;
import com.ftn.kvtsvtprojekat.model.GroupAdmin;

import java.util.List;

public interface GroupAdminService {
    List<GroupAdmin> findAll();

    GroupAdmin findOneById(Long id);

    GroupAdmin addGroupAdmin(GroupAdmin groupAdmin);

    GroupAdmin updateGroupAdmin(GroupAdmin groupAdmin);

    GroupAdmin deleteGroupAdmin(Long id);
}
