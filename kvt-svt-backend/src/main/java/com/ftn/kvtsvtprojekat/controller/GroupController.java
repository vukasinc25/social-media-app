package com.ftn.kvtsvtprojekat.controller;

import com.ftn.kvtsvtprojekat.model.Group;
import com.ftn.kvtsvtprojekat.model.GroupAdmin;
import com.ftn.kvtsvtprojekat.model.dto.GroupDTO;
import com.ftn.kvtsvtprojekat.service.GroupService;
import com.ftn.kvtsvtprojekat.indexservice.GroupIndexingService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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
    private final GroupIndexingService groupIndexingService;

    public GroupController(GroupService groupService, ModelMapper modelMapper, GroupIndexingService groupIndexingService) {
        this.groupService = groupService;
        this.modelMapper = modelMapper;
        this.groupIndexingService = groupIndexingService;
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

    @PostMapping(value = "/create", consumes = {"multipart/form-data"})
    public ResponseEntity<GroupDTO> createGroup(
            @RequestPart("group") GroupDTO groupDTO,
            @RequestPart(value = "pdfFile", required = false) MultipartFile pdfFile
    ) {
        Group group = modelMapper.map(groupDTO, Group.class);
        group.setCreationDate(LocalDateTime.now());
        group.setIsSuspended(false);
        groupService.save(group);
        
        // Index in Elasticsearch (pass pdfFile if present, else null)
        try {
            groupIndexingService.indexGroup(pdfFile, group);
        } catch (Exception e) {
            // Log error but don't fail the request
            System.err.println("Failed to index group in Elasticsearch: " + e.getMessage());
        }
        
        Group group232 = groupService.findByName(group.getName());
        GroupDTO groupDTO1 = modelMapper.map(group232, GroupDTO.class);
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
        
        // Update Elasticsearch index
        try {
            groupIndexingService.updateGroupIndex(group);
        } catch (Exception e) {
            // Log error but don't fail the request
            System.err.println("Failed to update group index in Elasticsearch: " + e.getMessage());
        }
        
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping(value = "/delete")
    public ResponseEntity<Void> deleteGroup(@RequestBody GroupDTO groupDTO) {
        if(groupDTO.getId() == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Group group = groupService.findOneById(groupDTO.getId());

        if (group != null) {
            group.setIsSuspended(true);
            group.setSuspensionReason(groupDTO.getSuspensionReason());
            groupService.save(group);
            
            // Remove from Elasticsearch index
            try {
                groupIndexingService.suspendGroupIndex(group);
            } catch (Exception e) {
                // Log error but don't fail the request
                System.err.println("Failed to remove group from Elasticsearch index: " + e.getMessage());
            }
            
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.I_AM_A_TEAPOT);
        }
    }
}
