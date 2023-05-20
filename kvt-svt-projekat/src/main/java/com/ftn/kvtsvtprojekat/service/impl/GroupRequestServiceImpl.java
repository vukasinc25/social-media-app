package com.ftn.kvtsvtprojekat.service.impl;

import com.ftn.kvtsvtprojekat.model.GroupRequest;
import com.ftn.kvtsvtprojekat.repository.GroupRequestRepository;
import com.ftn.kvtsvtprojekat.service.GroupRequestService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GroupRequestServiceImpl implements GroupRequestService {
    
    public final GroupRequestRepository groupRequestRepository;
    private final ModelMapper modelMapper;

    public GroupRequestServiceImpl(GroupRequestRepository groupRequestRepository, ModelMapper modelMapper) {
        this.groupRequestRepository = groupRequestRepository;
        this.modelMapper = modelMapper;
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
    public GroupRequest save(GroupRequest groupRequest){
        return groupRequestRepository.save(groupRequest);
    }

    @Override
    public void delete(Long id){
        groupRequestRepository.deleteById(id);
    }
}
