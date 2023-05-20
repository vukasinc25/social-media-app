package com.ftn.kvtsvtprojekat.service;

import com.ftn.kvtsvtprojekat.model.Banned;
import com.ftn.kvtsvtprojekat.model.GroupRequest;

import java.util.List;

public interface GroupRequestService {
    List<GroupRequest> findAll();

    GroupRequest findOneById(Long id);

    GroupRequest save(GroupRequest groupRequest);

    void delete(Long id);
}
