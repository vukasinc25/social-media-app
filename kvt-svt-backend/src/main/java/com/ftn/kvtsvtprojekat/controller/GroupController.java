package com.ftn.kvtsvtprojekat.controller;

import com.ftn.kvtsvtprojekat.model.Group;
import com.ftn.kvtsvtprojekat.model.GroupAdmin;
import com.ftn.kvtsvtprojekat.model.dto.GroupDTO;
import com.ftn.kvtsvtprojekat.service.GroupService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.http.ResponseEntity.status;

@RestController
@RequestMapping("/api/group")
public class GroupController {

    private final GroupService groupService;
    private final ModelMapper modelMapper;

    public GroupController(GroupService groupService, ModelMapper modelMapper) {
        this.groupService = groupService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/all")
    public ResponseEntity<List<GroupDTO>> getGroups() {

        List<Group> groups = groupService.findAll();
        List<GroupDTO> groupsDTO = new ArrayList<>();
        for (Group group : groups) {
            if (!group.getIsSuspended()) {
                GroupDTO groupDTO = modelMapper.map(group, GroupDTO.class);
                groupsDTO.add(groupDTO);
            }
        }

        return new ResponseEntity<>(groupsDTO, HttpStatus.OK);
    }

    @GetMapping("/deleted")
    public ResponseEntity<List<GroupDTO>> getGroupsDeleted() {

        List<Group> groups = groupService.findAll();
        List<GroupDTO> groupsDTO = new ArrayList<>();
        for (Group group : groups) {
            if (group.getIsSuspended()) {
                GroupDTO groupDTO = modelMapper.map(group, GroupDTO.class);
                groupsDTO.add(groupDTO);
            }
        }
        return new ResponseEntity<>(groupsDTO, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<GroupDTO> getGroup(@PathVariable("id") Long id) {
        Group group = groupService.findOneById(id);

        if (group == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        GroupDTO groupDTO = modelMapper.map(group, GroupDTO.class);

        return status(HttpStatus.OK).body(groupDTO);
    }

    @PostMapping(value = "/create", consumes = "application/json")
    public ResponseEntity<GroupDTO> createGroup(@Valid @RequestBody GroupDTO groupDTO) {
        Group group = modelMapper.map(groupDTO, Group.class);
        group.setCreationDate(LocalDateTime.now());
        group.setIsSuspended(false);
        groupService.save(group);
        GroupDTO groupDTO1 = modelMapper.map(group, GroupDTO.class);
        return new ResponseEntity<>(groupDTO1, HttpStatus.CREATED);
    }

    @PutMapping(value = "/{id}", consumes = "application/json")
    public ResponseEntity<GroupDTO> updateGroup(@PathVariable("id") Long id, @RequestBody GroupDTO groupDTO) {
        Group group = groupService.findOneById(id);
        if(group == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        group.setName(groupDTO.getName());
        group.setDescription(groupDTO.getDescription());

        groupService.save(group);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deleteGroup(@PathVariable Long id) {
        if(id == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Group group = groupService.findOneById(id);

        if (group != null) {
            group.setIsSuspended(true);
            groupService.save(group);
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.I_AM_A_TEAPOT);
        }
    }
}
