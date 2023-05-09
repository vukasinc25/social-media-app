package com.ftn.kvtsvtprojekat.service.impl;

import com.ftn.kvtsvtprojekat.model.Group;
import com.ftn.kvtsvtprojekat.repository.GroupRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GroupService {

    public final GroupRepository groupRepository;

    public GroupService(GroupRepository groupRepository) {
        this.groupRepository = groupRepository;
    }
    
    public List<Group> findAll(){
        return groupRepository.findAll();
    }

    public Group findOneById(Long id){
        return groupRepository.findGroupById(id);
    }

    public Group addGroup(Group group){
        return groupRepository.save(group);
    }

    public Group updateGroup(Group group){
        return groupRepository.save(group);
    }

    public Group deleteGroup(Long id){
        return groupRepository.deleteGroupById(id);
    }
}
