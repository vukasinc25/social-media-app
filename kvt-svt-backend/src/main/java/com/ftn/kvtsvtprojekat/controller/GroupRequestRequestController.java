package com.ftn.kvtsvtprojekat.controller;

import com.ftn.kvtsvtprojekat.model.GroupRequest;
import com.ftn.kvtsvtprojekat.model.dto.GroupRequestDTO;
import com.ftn.kvtsvtprojekat.service.GroupRequestService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.http.ResponseEntity.status;

@RestController
@RequestMapping("/api/groupRequest")
public class GroupRequestRequestController {

    private final GroupRequestService groupRequestService;
    private final ModelMapper modelMapper;

    public GroupRequestRequestController(GroupRequestService groupRequestService, ModelMapper modelMapper) {
        this.groupRequestService = groupRequestService;
        this.modelMapper = modelMapper;
    }

    @GetMapping
    public ResponseEntity<List<GroupRequestDTO>> getGroupRequests() {

        List<GroupRequest> groups = groupRequestService.findAll();
        List<GroupRequestDTO> groupsDTO = new ArrayList<>();
        for (GroupRequest group : groups) {
            GroupRequestDTO groupDTO = modelMapper.map(group, GroupRequestDTO.class);
            groupsDTO.add(groupDTO);
        }

        return new ResponseEntity<>(groupsDTO, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<GroupRequestDTO> getGroupRequest(@PathVariable("id") Long id) {
        GroupRequest group = groupRequestService.findOneById(id);

        if (group == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        GroupRequestDTO groupDTO = modelMapper.map(group, GroupRequestDTO.class);

        return status(HttpStatus.OK).body(groupDTO);
    }

    @PostMapping(value = "/create")
    public ResponseEntity<GroupRequest> createGroupRequest(@Valid @RequestBody GroupRequestDTO groupDTO) {
        GroupRequest group = modelMapper.map(groupDTO, GroupRequest.class);
        groupRequestService.save(group);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<GroupRequestDTO> updateGroupRequest(@PathVariable("id") Long id) {
        GroupRequest group = groupRequestService.findOneById(id);
        if(group == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        group.setApproved(true);
        groupRequestService.save(group);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deleteGroupRequest(@PathVariable Long id) {
        if(id == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        GroupRequest group = groupRequestService.findOneById(id);

        if (group != null) {
            group.setIsDeleted(true);
            groupRequestService.save(group);
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.I_AM_A_TEAPOT);
        }
    }
}
