package com.ftn.kvtsvtprojekat.controller;

import com.ftn.kvtsvtprojekat.model.GroupAdmin;
import com.ftn.kvtsvtprojekat.model.dto.GroupAdminDTO;
import com.ftn.kvtsvtprojekat.service.GroupAdminService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.http.ResponseEntity.status;

@RestController
@RequestMapping("/api/groupAdmin")
public class GroupAdminController {

    private final GroupAdminService groupAdminService;
    private final ModelMapper modelMapper;

    public GroupAdminController(GroupAdminService groupAdminService, ModelMapper modelMapper) {
        this.groupAdminService = groupAdminService;
        this.modelMapper = modelMapper;
    }

    @GetMapping
    public ResponseEntity<List<GroupAdminDTO>> getGroupAdmins() {

        List<GroupAdmin> groupAdmins = groupAdminService.findAll();
        List<GroupAdminDTO> groupAdminsDTO = new ArrayList<>();
        for (GroupAdmin groupAdmin : groupAdmins) {
            GroupAdminDTO groupAdminDTO = modelMapper.map(groupAdmin, GroupAdminDTO.class);
            groupAdminsDTO.add(groupAdminDTO);
        }

        return new ResponseEntity<>(groupAdminsDTO, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<GroupAdminDTO> getGroupAdmin(@PathVariable("id") Long id) {
        GroupAdmin groupAdmin = groupAdminService.findOneById(id);

        if (groupAdmin == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        GroupAdminDTO groupAdminDTO = modelMapper.map(groupAdmin, GroupAdminDTO.class);

        return status(HttpStatus.OK).body(groupAdminDTO);
    }

    @PostMapping(value = "/create", consumes = "application/json")
    public ResponseEntity<GroupAdminDTO> createGroupAdmin(@Valid @RequestBody GroupAdminDTO groupAdminDTO) {
        // Check if a group admin already exists for this user and group
        if (groupAdminService.existsByUserAndGroup(groupAdminDTO.getUserId(), groupAdminDTO.getGroupId())) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        
        GroupAdmin groupAdmin = modelMapper.map(groupAdminDTO, GroupAdmin.class);
        groupAdmin.setIsDeleted(false);
        groupAdminService.save(groupAdmin);
        GroupAdminDTO groupAdminDTO1 = modelMapper.map(groupAdmin, GroupAdminDTO.class);
        return new ResponseEntity<>(groupAdminDTO1, HttpStatus.CREATED);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deleteGroupAdmin(@PathVariable Long id) {
        if(id == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        GroupAdmin groupAdmin = groupAdminService.findOneById(id);

        if (groupAdmin != null) {
//            groupAdmin.setIsDeleted(true);
            groupAdminService.delete(groupAdmin.getId());
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.I_AM_A_TEAPOT);
        }
    }
}
