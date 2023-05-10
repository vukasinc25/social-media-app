package com.ftn.kvtsvtprojekat.service.impl;

import com.ftn.kvtsvtprojekat.model.GroupAdmin;
import com.ftn.kvtsvtprojekat.repository.GroupAdminRepository;
import com.ftn.kvtsvtprojekat.service.GroupAdminService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GroupAdminServiceImpl implements GroupAdminService {

    public final GroupAdminRepository groupAdminRepository;

    public GroupAdminServiceImpl(GroupAdminRepository groupAdminRepository) {
        this.groupAdminRepository = groupAdminRepository;
    }

    @Override
    public List<GroupAdmin> findAll() {
        return groupAdminRepository.findAll();
    }
    @Override
    public GroupAdmin findOneById(Long id) {
        return groupAdminRepository.findGroupAdminById(id);
    }

    @Override
    public GroupAdmin addGroupAdmin(GroupAdmin groupAdmin) {
        return groupAdminRepository.save(groupAdmin);
    }

    @Override
    public GroupAdmin updateGroupAdmin(GroupAdmin groupAdmin) {
        return groupAdminRepository.save(groupAdmin);
    }

    @Override
    public GroupAdmin deleteGroupAdmin(Long id) {
        return groupAdminRepository.delete(id);
    }
}
