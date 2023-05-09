package com.ftn.kvtsvtprojekat.service.impl;

import com.ftn.kvtsvtprojekat.model.GroupRequest;
import com.ftn.kvtsvtprojekat.repository.GroupRequestRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GroupRequestServiceImpl {
    
    public final GroupRequestRepository groupRequestRepository;

    public GroupRequestServiceImpl(GroupRequestRepository groupRequestRepository) {
        this.groupRequestRepository = groupRequestRepository;
    }

    public List<GroupRequest> findAll(){
        return groupRequestRepository.findAll();
    }

    public GroupRequest findOneById(Long id){
        return groupRequestRepository.findGroupRequestById(id);
    }

    public GroupRequest addGroupRequest(GroupRequest groupRequest){
        return groupRequestRepository.save(groupRequest);
    }

    public GroupRequest updateGroupRequest(GroupRequest groupRequest){
        return groupRequestRepository.save(groupRequest);
    }

    public GroupRequest deleteGroupRequest(Long id){
        return groupRequestRepository.deleteGroupRequestById(id);
    }
}
