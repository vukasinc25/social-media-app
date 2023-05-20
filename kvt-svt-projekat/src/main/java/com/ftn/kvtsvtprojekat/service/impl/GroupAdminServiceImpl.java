package com.ftn.kvtsvtprojekat.service.impl;

import com.ftn.kvtsvtprojekat.model.GroupAdmin;
import com.ftn.kvtsvtprojekat.repository.GroupAdminRepository;
import com.ftn.kvtsvtprojekat.service.GroupAdminService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GroupAdminServiceImpl implements GroupAdminService {

    public final GroupAdminRepository groupAdminRepository;
    private final ModelMapper modelMapper;

    public GroupAdminServiceImpl(GroupAdminRepository groupAdminRepository, ModelMapper modelMapper) {
        this.groupAdminRepository = groupAdminRepository;
        this.modelMapper = modelMapper;
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
    public GroupAdmin save(GroupAdmin groupAdmin) {
        return groupAdminRepository.save(groupAdmin);
    }

    @Override
    public GroupAdmin delete(Long id) {
        return groupAdminRepository.deleteGroupAdminById(id);
    }

}
