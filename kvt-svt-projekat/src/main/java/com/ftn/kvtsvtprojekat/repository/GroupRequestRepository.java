package com.ftn.kvtsvtprojekat.repository;

import com.ftn.kvtsvtprojekat.model.GroupRequest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GroupRequestRepository extends JpaRepository<GroupRequest,Long> {
    GroupRequest findGroupRequestById(Long id);

    GroupRequest deleteGroupRequestById(Long id);
}
