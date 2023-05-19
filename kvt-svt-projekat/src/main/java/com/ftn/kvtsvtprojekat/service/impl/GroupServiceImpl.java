package com.ftn.kvtsvtprojekat.service.impl;

import com.ftn.kvtsvtprojekat.model.Group;
import com.ftn.kvtsvtprojekat.repository.GroupRepository;
import com.ftn.kvtsvtprojekat.service.GroupService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GroupServiceImpl implements GroupService {

    public final GroupRepository groupRepository;

    public GroupServiceImpl(GroupRepository groupRepository) {
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
    public Group addGroup(Group group){
        return groupRepository.save(group);
    }

    @Override
    public Group updateGroup(Group group){
        return groupRepository.save(group);
    }

    @Override
    public Group deleteGroup(Long id){
        return groupRepository.deleteGroupById(id);
    }
}