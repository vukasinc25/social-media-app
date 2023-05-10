package com.ftn.kvtsvtprojekat.service.impl;

import com.ftn.kvtsvtprojekat.model.GroupRequest;
import com.ftn.kvtsvtprojekat.repository.GroupRequestRepository;
import com.ftn.kvtsvtprojekat.service.GroupRequestService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GroupRequestServiceImpl implements GroupRequestService {
    
    public final GroupRequestRepository groupRequestRepository;

    public GroupRequestServiceImpl(GroupRequestRepository groupRequestRepository) {
        this.groupRequestRepository = groupRequestRepository;
    }

    @Override
    public List<GroupRequest> findAll(){
        return groupRequestRepository.findAll();
    }

    @Override
    public GroupRequest findOneById(Long id){
        return groupRequestRepository.findGroupRequestById(id);
    }

    @Override
    public GroupRequest addGroupRequest(GroupRequest groupRequest){
        return groupRequestRepository.save(groupRequest);
    }

    @Override
    public GroupRequest updateGroupRequest(GroupRequest groupRequest){
        return groupRequestRepository.save(groupRequest);
    }

    @Override
    public GroupRequest deleteGroupRequest(Long id){
        return groupRequestRepository.deleteGroupRequestById(id);
    }
}
