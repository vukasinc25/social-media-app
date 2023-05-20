package com.ftn.kvtsvtprojekat.service.impl;

import com.ftn.kvtsvtprojekat.model.Group;
import com.ftn.kvtsvtprojekat.model.Group;
import com.ftn.kvtsvtprojekat.repository.GroupRepository;
import com.ftn.kvtsvtprojekat.service.GroupService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GroupServiceImpl implements GroupService {

    public final GroupRepository groupRepository;

    public GroupServiceImpl(GroupRepository groupRepository, ModelMapper modelMapper) {
        this.groupRepository = groupRepository;
    }

    @Override
    public List<Group> findAll(){
        return groupRepository.findAll();
    }

    @Override
    public Group findOneById(Long id){
        return groupRepository.findGroupById(id);
    }

    @Override
    public Group save(Group group){
        return groupRepository.save(group);
    }

    @Override
    public void delete(Long id){
        Group group = groupRepository.findGroupById(id);
        group.setIsSuspended(true);
        groupRepository.save(group);
    }
}
